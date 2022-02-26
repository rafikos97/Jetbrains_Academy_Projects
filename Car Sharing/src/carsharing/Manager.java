package carsharing;

import java.util.ArrayList;
import java.util.Scanner;

public class Manager {
    private static final Scanner scanner = new Scanner(System.in);

    public static void openManager() {
        while (true) {
            printMenu();
            int userChoice = Integer.parseInt(scanner.nextLine());

            switch (userChoice) {
                case 1:
                    ArrayList<Company> companies = DataBase.listOfCompanies();
                    printCompanies(companies);
                    if (!companies.isEmpty()) {
                        int selectedCompanyNumber = Integer.parseInt(scanner.nextLine());
                        if (selectedCompanyNumber == 0) {
                            break;
                        } else if (selectedCompanyNumber > 0 && selectedCompanyNumber <= companies.size()) {
                            Company selectedCompany = companies.get(selectedCompanyNumber - 1);
                            selectedCompany.openCompanyMenu();
                        } else {
                            System.out.println("Wrong input!");
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter the company name: ");
                    String companyName = scanner.nextLine();
                    DataBase.createCompany(companyName);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Wrong input;");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back\n");
    }

    public static void printCompanies(ArrayList<Company> companies) {
        int index = 1;

        if (companies.size() > 0) {
            System.out.println("Company list:");
            for (Company company : companies) {
                System.out.println(index + ". " + company.getCompanyName());
                index++;
            }
            System.out.println("0. Back");
        } else {
            System.out.println("The company list is empty!");
        }
        System.out.println();
    }
}
