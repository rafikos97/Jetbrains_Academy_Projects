package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerPanel {
    private static final Scanner scanner = new Scanner(System.in);

    public static void customerSelectionMenu() {
        ArrayList<Customer> customers = DataBase.listOfCustomers();

        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            System.out.println();
        } else {
            printCustomers(customers);
            int selectedCustomerNumber = Integer.parseInt(scanner.nextLine());

            if (selectedCustomerNumber == 0) {
                return;
            }

            Customer selectedCustomer = new Customer(customers.get(selectedCustomerNumber - 1));
            selectedCustomer.openCustomerMenu();
        }
    }

    private static void printCustomers(ArrayList<Customer> customers) {
        System.out.println("Customer list:");
        int index = 1;

        for (Customer customer : customers) {
            System.out.println(index + ". " + customer.getName());
            index++;
        }

        System.out.println("0. Back");
    }
}
