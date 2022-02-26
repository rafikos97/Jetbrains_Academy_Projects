package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Company {
    private final int id;
    private final String companyName;
    private final Scanner scanner = new Scanner(System.in);

    public Company(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void openCompanyMenu() {
        while (true) {
            ArrayList<Car> cars = DataBase.listOfCars(this.getId());
            printMenu();
            int userChoice = Integer.parseInt(scanner.nextLine());

            switch (userChoice) {
                case 1:
                    printCars(cars);
                    break;
                case 2:
                    System.out.println("Enter the car name:");
                    String carName = scanner.nextLine();
                    DataBase.createCar(carName, this.getId());
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Incorrect input");
            }
        }
    }

    private void printMenu() {
        System.out.println(this.companyName + " company:");
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back\n");
    }

    public static void printCars(ArrayList<Car> cars) {
        int index = 1;

        if (cars.size() > 0) {
            System.out.println("Cars list:");
            for (Car car : cars) {
                System.out.println(index + ". " + car.getName());
                index++;
            }
        } else {
            System.out.println("The car list is empty!");
        }
        System.out.println();
    }
}
