package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String JDBC_DRIVER = "org.h2.Driver";
    public static String DB_URL = "jdbc:h2:C:\\Users\\Rafiki\\IdeaProjects\\Car Sharing\\Car Sharing\\task\\src\\carsharing\\db\\";

    public static void createTable() {
        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE);" +
                            "CREATE TABLE IF NOT EXISTS CAR (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE," +
                            "COMPANY_ID INT NOT NULL," +
                            "CONSTRAINT fk_companyId FOREIGN KEY (COMPANY_ID)" +
                            "REFERENCES COMPANY (ID));" +
                            "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                            "ID INT PRIMARY KEY AUTO_INCREMENT," +
                            "NAME VARCHAR NOT NULL UNIQUE," +
                            "RENTED_CAR_ID INT," +
                            "CONSTRAINT fk_rented_car_id FOREIGN KEY (RENTED_CAR_ID)" +
                            "REFERENCES CAR (ID));");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Company> listOfCompanies() {

        ArrayList<Company> companiesList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String countQuery = "SELECT COUNT(*) FROM COMPANY";
                    ResultSet res = statement.executeQuery(countQuery);
                    int count = 0;

                    if (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {
                        String listQuery = "SELECT * FROM COMPANY";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {
                            Company company = new Company(res.getInt(1), res.getString(2));
                            companiesList.add(company);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return companiesList;
    }

    public static void createCompany(String companyName) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String addStatement = "INSERT INTO COMPANY(NAME) VALUES('" + companyName + "');";
                    statement.executeUpdate(addStatement);
                    System.out.println("The company was created!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Car> listOfCars(int companyId) {
        ArrayList<Car> carsList = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {

                    String countQuery = "SELECT COUNT(*) FROM COMPANY";
                    ResultSet res = statement.executeQuery(countQuery);
                    int count = 0;

                    if (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {
                        String listQuery = "SELECT * FROM CAR WHERE COMPANY_ID = " + companyId + ";";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {
                            Car car = new Car(res.getInt(1), res.getString(2), companyId);
                            carsList.add(car);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return carsList;
    }

    public static void createCar(String name, int companyId) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String sql = "INSERT INTO CAR(NAME, COMPANY_ID) VALUES('" + name + "', " + companyId + ");";
                    statement.executeUpdate(sql);
                    System.out.println("The car was added!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createCustomer(String name) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String sql = "INSERT INTO CUSTOMER(NAME) VALUES('" + name + "');";
                    statement.executeUpdate(sql);
                    System.out.println("The customer was added!");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Customer> listOfCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String countQuery = "SELECT COUNT(*) FROM CUSTOMER;";
                    ResultSet res = statement.executeQuery(countQuery);
                    int count = -1;

                    while (res.next()) {
                        count = res.getInt(1);
                    }

                    if (count > 0) {
                        String listQuery = "SELECT * FROM CUSTOMER";
                        res = statement.executeQuery(listQuery);

                        while (res.next()) {
                            Customer customer = new Customer(res.getInt(1), res.getString(2), res.getInt(3));
                            customers.add(customer);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void rentACar(Customer selectedCustomer) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String sql = "SELECT * FROM CUSTOMER WHERE ID = " + selectedCustomer.getId() + ";";
                    ResultSet customer = statement.executeQuery(sql);

                    int carId = -1;
                    String carName = "NULL";

                    while (customer.next()) {
                        if (customer.getObject(3) != null) {
                            System.out.println("You've already rented a car!\n");
                            return;
                        } else {
                            ArrayList<Company> companies = listOfCompanies();


                            if (companies.size() == 0) {
                                System.out.println("The company list is empty!");
                                System.out.println();
                                return;
                            } else {
                                Manager.printCompanies(companies);
                                System.out.println("0. Back");

                                int companySelection = Integer.parseInt(scanner.nextLine());
                                System.out.println();

                                if (companySelection == 0) {
                                    return;
                                }

                                int companyId = companies.get(companySelection - 1).getId();
                                String companyName = companies.get(companySelection - 1).getCompanyName();

                                ArrayList<Car> carList = carListWithoutRented(listOfCars(companyId));

                                if (carList.size() == 0) {
                                    System.out.println("No available cars in the '" + companyName + "' company");
                                    System.out.println();
                                    return;
                                } else {
                                    Company.printCars(carList);

                                    int selectedCar = Integer.parseInt(scanner.nextLine());
                                    System.out.println();

                                    if (selectedCar == 0) {
                                        return;
                                    }
                                    carId = carList.get(selectedCar - 1).getId();
                                    carName = carList.get(selectedCar - 1).getName();
                                }
                            }
                        }
                    }
                    sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = " +
                            carId + " WHERE ID = " + selectedCustomer.getId();
                    statement.executeUpdate(sql);
                    System.out.println("You rented '" + carName + "'");
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getRentedCar(Customer customer) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String query = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID=" + customer.getId() + ";";
                    ResultSet res = statement.executeQuery(query);

                    String carName = "NULL";
                    String companyName = "NULL";
                    int companyId = 0;
                    int rentedCarId = 0;

                    while (res.next()) {
                        if (res.getObject(1) == null) {
                            System.out.println("You didn't rent a car!\n");
                            return;
                        } else {
                            rentedCarId = res.getInt(1);
                        }
                    }

                    query = "SELECT * FROM CAR WHERE ID=" + rentedCarId;
                    ResultSet car = statement.executeQuery(query);

                    while (car.next()) {
                        carName = car.getString("NAME");
                        companyId = car.getInt("COMPANY_ID");
                    }

                    query = "SELECT NAME FROM COMPANY WHERE ID=" + companyId;
                    ResultSet company = statement.executeQuery(query);

                    while (company.next()) {
                        companyName = company.getString("NAME");
                    }

                    System.out.println("Your rented car:");
                    System.out.println(carName);
                    System.out.println("Company:");
                    System.out.println(companyName);
                    System.out.println();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnCar(Customer customer) {

        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    String sql = "SELECT * FROM CUSTOMER WHERE ID=" + customer.getId() + ";";
                    ResultSet selectedCustomer = statement.executeQuery(sql);

                    while (selectedCustomer.next()) {
                        if (selectedCustomer.getObject(3) == null) {
                            System.out.println("You didn't rent a car!\n");
                            return;
                        }
                    }
                    sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = " + customer.getId();
                    statement.executeUpdate(sql);
                    System.out.println("You've returned a rented car!");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Car> carListWithoutRented(ArrayList<Car> listOfCars) {
        ArrayList<Car> newList = new ArrayList<>();
        try {
            Class.forName(JDBC_DRIVER);
            try (Connection connection = DriverManager.getConnection(DB_URL)) {
                connection.setAutoCommit(true);
                try (Statement statement = connection.createStatement()) {
                    ArrayList<Integer> rentedCarsId = new ArrayList<>();
                    String sql = "SELECT RENTED_CAR_ID FROM CUSTOMER;";
                    ResultSet rentedCars = statement.executeQuery(sql);

                    while (rentedCars.next()) {
                        int temp = rentedCars.getInt(1);
                        rentedCarsId.add(temp);
                    }

                    for (Car car : listOfCars) {
                        if (!rentedCarsId.contains(car.getId())) {
                            newList.add(car);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return newList;
    }
}
