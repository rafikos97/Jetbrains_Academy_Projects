package contacts;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person extends Contact {
    Scanner scanner = new Scanner(System.in);
    private String name;
    private String surname;
    private String birthDate;
    private String gender;

    public Person(String phoneNumber, String name, String surname, String birthDate, String gender) {
        super(phoneNumber);
        this.name = name;
        this.surname = surname;
        setBirthDate(birthDate);
        setGender(gender);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        if (checkBirthDate(birthDate)) {
            this.birthDate = birthDate;
        } else {
            System.out.println("Bad birth date!");
            this.birthDate = "";
        }
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("M")) {
            this.gender = "M";
        } else if (gender.equalsIgnoreCase("F")) {
            this.gender = "F";
        } else {
            System.out.println("Bad gender!");
            this.gender = "";
        }
    }

    public boolean checkBirthDate(String birthDate) {
        String mainPattern = "(\\d{4}([.-]|\\s)\\d{2}([.-]|\\s)\\d{2})|(\\d{2}([.-]|\\s)\\d{2}([.-]|\\s)\\d{4})";

        Pattern pattern = Pattern.compile(mainPattern, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(birthDate);
        return matcher.matches();
    }

    @Override
    public void edit() {
        System.out.print("Select a field (name, surname, birth, gender, number): ");
        String field = scanner.nextLine().trim().toLowerCase();

        switch (field) {
            case("name"):
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                setName(name);
                System.out.println("The record updated!");
                break;
            case("surname"):
                System.out.print("Enter surname: ");
                String surname = scanner.nextLine();
                setSurname(surname);
                System.out.println("The record updated!");
                break;
            case("birth"):
                System.out.print("Enter birth date: ");
                String birthDate = scanner.nextLine().trim();
                setBirthDate(birthDate);
                System.out.println("The record updated!");
                break;
            case("gender"):
                System.out.print("Enter gender (M/F): ");
                String gender = scanner.nextLine().trim();
                setGender(gender);
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
        System.out.println("Name: " + this.name);
        System.out.println("Surname: " + this.surname);
        System.out.println("Birth date: " + (this.birthDate.isEmpty() ? "[no data]" : this.birthDate));
        System.out.println("Gender: " + (this.gender.isEmpty() ? "[no data]" : this.gender));
        System.out.println("Number: " + (getPhoneNumber().isEmpty() ? "[no number]" : getPhoneNumber()));
        System.out.println("Time created: " + getCreateDate());
        System.out.println("Time last edit: " + getEditDate());
    }

    @Override
    public String searchString() {
        return getPhoneNumber() + this.name + this.surname;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }
}