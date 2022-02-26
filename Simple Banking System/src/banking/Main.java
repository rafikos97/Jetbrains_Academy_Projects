package banking;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean backToMainMenu = false;
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        String fileName = args[1];
        DataBase db = new DataBase(fileName);
        db.createDatabase();
        db.selectAll();

        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("""
                    1. Create an account
                    2. Log into account
                    0. Exit
                    """);

            if (!scanner.hasNextInt()) {
                System.out.println("""
                        Incorrect input!
                        Try again:
                        """);
                scanner.nextLine();
            }
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case(1):
                    bank.addCustomer();
                    break;
                case(2):
                    String cardNumber = bank.login();
                    if (cardNumber != null) {
                        loginMenu(cardNumber);
                    }

                    if (backToMainMenu) {
                        db.closeConnection();
                        shouldContinue = false;
                    }
                    break;
                case(0):
                    shouldContinue = false;
                    db.closeConnection();
                    break;
                default:
                    System.out.println("Incorrect input!");
            }
        }
    }

    public static void loginMenu(String cardNumber) {
        boolean continueLogin = true;

        while (continueLogin) {
            System.out.println("""
                    1. Balance
                    2. Add income
                    3. Do transfer
                    4. Close account
                    5. Log out
                    0. Exit
                    """);

            if (!scanner.hasNextInt()) {
                System.out.println("""
                        Incorrect input!
                        Try again:
                        """);
                scanner.nextLine();
            }
            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case(1):
                    System.out.println("Balance: " + bank.getBalance(cardNumber));
                    break;
                case(2):
                    bank.addIncome(cardNumber);
                    break;
                case(3):
                    bank.transfer(cardNumber);
                    break;
                case(4):
                    bank.closeAccount(cardNumber);
                    continueLogin = false;
                    break;
                case(5):
                    continueLogin = false;
                    break;
                case(0):
                    backToMainMenu = true;
                    continueLogin = false;
                    break;
                default:
                    System.out.println("Incorrect input!");
            }
        }
    }
}