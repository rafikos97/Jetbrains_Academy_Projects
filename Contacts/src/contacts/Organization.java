package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Organization extends Contact {
    Scanner scanner = new Scanner(System.in);
    private String organizationName;
    private String address;

    public Organization(String phoneNumber, String organizationName, String address) {
        super(phoneNumber);
        this.organizationName = organizationName;
        this.address = address;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void edit() {
        System.out.print("Select a field (name, address, number): ");
        String field = scanner.nextLine().trim().toLowerCase();

        switch (field) {
            case("name"):
                System.out.print("Enter organization name: ");
                String name = scanner.nextLine();
                setOrganizationName(name);
                System.out.println("The record updated!");
                break;
            case("address"):
                System.out.print("Enter address: ");
                String address = scanner.nextLine();
                setAddress(address);
                System.out.println("The record updated!");
                break;
            case("number"):
                System.out.print("Enter number: ");
                String number = scanner.nextLine();
                setPhoneNumber(number);
                System.out.println("The record updated!");
                break;
            default:
                System.out.println("Wrong input!");
        }

        setEditDate(LocalDateTime.now());
    }

    @Override
    public void showInformation() {
        System.out.println("Organization name: " + this.organizationName);
        System.out.println("Address: " + this.address);
        System.out.println("Number: " + (getPhoneNumber().isEmpty() ? "[no number]" : getPhoneNumber()));
        System.out.println("Time created: " + getCreateDate());
        System.out.println("Time last edit: " + getEditDate());
    }

    @Override
    public String searchString() {
        return getPhoneNumber() + this.organizationName;
    }

    @Override
    public String toString() {
        return this.organizationName;
    }
}
