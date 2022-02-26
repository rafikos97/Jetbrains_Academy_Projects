package contacts;

import java.util.*;

public class SearchEngine {
    Scanner scanner = new Scanner(System.in);

    public List<Contact> searchForRecord(List<Contact> contacts) {
        List<Contact> contactsToDisplay = new ArrayList<>();
        List<String> records = new ArrayList<>();
        for (Contact contact : contacts) {
            records.add(contact.searchString().toLowerCase());
        }

        System.out.print("Enter search query: ");
        String query = scanner.nextLine().trim().toLowerCase();

        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).contains(query)) {
                contactsToDisplay.add(contacts.get(i));
            }
        }
        return contactsToDisplay;
    }
}
