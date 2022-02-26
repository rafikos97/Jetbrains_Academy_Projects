package carsharing;

import java.util.Scanner;

public class Customer {
    private final int id;
    private final String name;
    private final int rentedCarId;
    private final Scanner scanner = new Scanner(System.in);

    public Customer(int id, String name, int rentedCarId) {
        this.id = id;
        this.name = name;
        this.rentedCarId = rentedCarId;
    }

    public Customer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.rentedCarId = customer.getRentedCarId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRentedCarId() {
        return rentedCarId;
    }

    public void openCustomerMenu() {
        while (true) {
            printMenu();
            int userChoice = Integer.parseInt(scanner.nextLine());

            switch (userChoice) {
                case 1:
                    DataBase.rentACar(this);
                    break;
                case 2:
                    DataBase.returnCar(this);
                    break;
                case 3:
                    DataBase.getRentedCar(this);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input!");
            }
        }
    }

    private void printMenu() {
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back\n");
    }
}
