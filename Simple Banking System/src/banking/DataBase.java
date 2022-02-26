package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class DataBase {
    private final String fileName;
    private static final String defaultFileName = "card.s3db";
    private static final DataBase currentDataBase = new DataBase(defaultFileName);

    public DataBase(String fileName) {
        this.fileName = fileName;
    }

    public Connection connect() {
        String url = "jdbc:sqlite:" + this.fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createDatabase() {
        String url = "jdbc:sqlite:" + this.fileName;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0);");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String number, String pin, int balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            preparedStatement.setString(2, pin);
            preparedStatement.setInt(3, balance);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            Connection conn = this.connect();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectAll(){
        String sql = "SELECT id, number, pin, balance FROM card";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("number") + "\t" +
                        rs.getString("pin") + "\t" +
                        rs.getInt("balance") +  "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDatabase() {
        String sql = "DELETE FROM card";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("exception in database deleteDatabase" + e.getMessage());
        }
    }

    public static DataBase returnCurrentDataBase() {
        return currentDataBase;
    }
}
