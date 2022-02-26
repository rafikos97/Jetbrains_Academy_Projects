package banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Bank {
    Scanner scanner = new Scanner(System.in);
    DataBase currentDataBase = DataBase.returnCurrentDataBase();

    public void addCustomer() {
        String cardNumber = generateCardNumber();
        String pinNumber = generatePIN();

        currentDataBase.insert(cardNumber, pinNumber, 0);
        System.out.println("Your card has been created\n" +
                "Your card number:\n" + cardNumber +
                "\nYour card PIN:\n" + pinNumber + "\n");
    }

    public String login() {
        System.out.println("Enter your card number:");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pinNumber = scanner.nextLine();


        if (findCustomer(cardNumber, pinNumber)) {
            System.out.println("You have successfully logged in!");
            return cardNumber;
        } else {
            System.out.println("Wrong card number or PIN!");
        }
        return null;
    }

    public void closeAccount(String cardNumber) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(String userCardNumber) {
        System.out.println("Enter card number:");
        String cardNumber = scanner.nextLine();

        if (userCardNumber.equals(cardNumber)) {
            System.out.println("You can't transfer money to the same account!");
        } else if (!checkLuhnAlgorithm(cardNumber)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (checkCardNumberUniqueness(cardNumber)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            int amount = scanner.nextInt();
            scanner.nextLine();
            if (getBalance(userCardNumber) < amount) {
                System.out.println("Not enough money!");
            } else {
                addMoneyToCard(amount, cardNumber);
                takeMoneyFromCard(amount, userCardNumber);
            }
        }
    }

    private void addMoneyToCard(int amount, String cardNumber) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void takeMoneyFromCard(int amount, String cardNumber) {
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addIncome(String cardNumber) {
        System.out.println("Enter income:");
        int income = scanner.nextInt();
        scanner.nextLine();
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, income);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();

            System.out.println("Income was added!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean findCustomer(String cardNumber, String pinNumber) {
        String sql = "SELECT 1 FROM card WHERE number = ? AND pin = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pinNumber);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getBalance(String cardNumber) {
        String sql = "SELECT balance FROM card WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.getInt("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String generateCardNumber() {
        Random random = new Random();

        while (true) {
            final String BIN = "400000";
            String accountIdentifier = String.format("%09d", random.nextInt(999999999) + 1);
            String cardNumber = BIN + accountIdentifier + generateChecksum(BIN + accountIdentifier);

            if (checkCardNumberUniqueness(cardNumber)) {
                return cardNumber;
            }
        }
    }

    private int generateChecksum(String numberString) {
        int[] digits = Arrays.stream(numberString.split("")).mapToInt(Integer::parseInt).toArray();
        int sum = 0;

        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                digits[i] = digits[i] * 2;
            }
        }

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] > 9) {
                digits[i] -= 9;
            }
            sum += digits[i];
        }

        return (int) getCeiling(sum) - sum;
    }

    private double getCeiling(double number){
        return Math.ceil(number / 10) * 10;
    }

    private String generatePIN() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(9999) + 1);
    }

    private boolean checkCardNumberUniqueness(String cardNumber) {
        String sql = "SELECT number FROM card WHERE number = ?";

        try (Connection conn = currentDataBase.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            ResultSet rs = preparedStatement.executeQuery();

            return !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkLuhnAlgorithm(String cardNumber) {
        String numberToCheck = cardNumber.trim();
        String[] splitArray = numberToCheck.split("");
        int[] digits = new int[splitArray.length];
        for (int i = 0; i < splitArray.length; i++) {
            digits[i] = Integer.parseInt(splitArray[i]);
        }
//        int[] digits = Arrays.stream(numberToCheck.split("")).mapToInt(Integer::parseInt).toArray();
        int lastDigit = digits[digits.length - 1];
        int sum = 0;

        int[] digitsWithoutChecksum = Arrays.copyOf(digits, digits.length-1);

        for (int i = 0; i < digitsWithoutChecksum.length; i++) {
            if (i % 2 == 0) {
                digitsWithoutChecksum[i] = digitsWithoutChecksum[i] * 2;
            }
        }

        for (int i = 0; i < digitsWithoutChecksum.length; i++) {
            if (digitsWithoutChecksum[i] > 9) {
                digitsWithoutChecksum[i] -= 9;
            }
            sum += digitsWithoutChecksum[i];
        }

        return (int) getCeiling(sum) - sum == lastDigit;
    }

}