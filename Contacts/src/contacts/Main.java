package contacts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            String userChoice = scanner.nextLine().trim().toLowerCase();

            switch (userChoice) {
                case("add"):
                    phoneBook.addContact();
                    break;
                case("list"):
                    phoneBook.listContacts();
                    break;
                case("search"):
                    phoneBook.search();
                    break;
                case("count"):
                    phoneBook.count();
                    break;
                case("exit"):
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
            System.out.println();
        }
    }
}
