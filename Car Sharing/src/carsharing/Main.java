package carsharing;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0) {
            DataBase.DB_URL += args[1];
        } else {
            System.out.println("No database name input");
        }

        DataBase.createTable();

        while (true) {
            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit\n");
            int userChoice = Integer.parseInt(scanner.nextLine());
            switch (userChoice) {
                case 1:
                    Manager.openManager();
                    break;
                case 2:
                    CustomerPanel.customerSelectionMenu();
                    break;
                case 3:
                    System.out.println("Enter the customer name:");
                    String name = scanner.nextLine();
                    DataBase.createCustomer(name);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong input");
            }
        }
    }
}