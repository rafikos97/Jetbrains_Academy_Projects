package contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PhoneBook {
    private Scanner scanner = new Scanner(System.in);
    private List<Contact> phoneBook = new ArrayList<>();
    SearchEngine searchEngine = new SearchEngine();

    public void addContact() {
        System.out.print("Enter the type (person, organization): ");
        String type = scanner.nextLine().trim().toLowerCase();

        if (type.equals("person")) {
            System.out.print("Enter the name: ");
            String name = scanner.nextLine();
            System.out.print("Enter the surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter the birth date: ");
            String birthDate = scanner.nextLine().trim();
            System.out.print("Enter the gender (M, F): ");
            String gender = scanner.nextLine().trim();
            System.out.print("Enter the number: ");
            String number = scanner.nextLine().trim();
            System.out.println();

            Contact person = new Person(number, name, surname, birthDate, gender);
            person.setCreateDate(LocalDateTime.now());
            person.setEditDate(LocalDateTime.now());
            phoneBook.add(person);
            System.out.println("The record added!");
        } else if (type.equals("organization")) {
            System.out.print("Enter the organization name: ");
            String organizationName = scanner.nextLine();
            System.out.print("Enter the address: ");
            String address = scanner.nextLine();
            System.out.print("Enter the number: ");
            String number = scanner.nextLine().trim();
            System.out.println();

            Contact organization = new Organization(number, organizationName, address);
            organization.setCreateDate(LocalDateTime.now());
            organization.setEditDate(LocalDateTime.now());
            phoneBook.add(organization);
            System.out.println("The record added!");
        } else {
            System.out.println("Wrong input!");
        }
    }

    public void listContacts() {
        int i = 1;
        for (Contact contact : phoneBook) {
            System.out.println(i + ". " +  contact.toString());
            i++;
        }

        System.out.print("[list] Enter action ([number], back): ");
        if (scanner.hasNextInt()) {
            int userNumber = scanner.nextInt();
            scanner.nextLine();

            if (userNumber > 0 && userNumber <= phoneBook.size()) {
                Contact contact = phoneBook.get(userNumber - 1);
                contact.showInformation();
                System.out.println();
                System.out.print("[record] Enter action (edit, delete, menu): ");
                String userChoice = scanner.nextLine().trim().toLowerCase();

                switch (userChoice) {
                    case "edit":
                        contact.edit();
                        break;
                    case "delete":
                        remove(contact);
                        break;
                    case "menu":
                        return;
                    default:
                        System.out.println("Wrong input!");
                }
            }
        }
    }

    private void remove(Contact contact) {
        phoneBook.remove(contact);
    }

    public void count() {
        System.out.println("The Phone Book has " + phoneBook.size() + " records.");
    }

    public void search() {
        List<Contact> contactsToDisplay = searchEngine.searchForRecord(phoneBook);
        int count = 1;
        for (Contact contact : contactsToDisplay) {
            System.out.println(count + ". " + contact.toString());
            count++;
        }

        System.out.print("[search] Enter action ([number], back, again): ");

        if (scanner.hasNextInt()) {
            int userNumber = scanner.nextInt();

            if (userNumber > 0 && userNumber <= contactsToDisplay.size()) {
                contactsToDisplay.get(userNumber - 1).showInformation();
            } else {
                System.out.println("Wrong input number!");
            }
        } else if (scanner.hasNextLine()) {
            String userChoice = scanner.nextLine().trim().toLowerCase();

            if (userChoice.equals("again")) {
                search();
            } else if (userChoice.equals("back")) {
                System.out.println("Going back...");
                return;
            } else {
                System.out.println("Wrong input!");
            }
        }
    }
}
