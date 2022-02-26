package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void startInteraction(String fileName) {
        boolean shouldContinue = true;

        String[] records = readFile(fileName);

        while(shouldContinue) {
            System.out.println("=== Menu ===\n" +
                    "1. Find a person\n" +
                    "2. Print all people\n" +
                    "0. Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case (1):
                    selectStrategy(records);
                    break;
                case (2):
                    displayAllPeople(records);
                    break;
                case(0):
                    System.out.println("Bye!");
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    private static void selectStrategy(String[] records) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String input = scanner.nextLine().toUpperCase().trim();

        switch (input) {
            case "ALL":
                SearchEngine all = new SearchAll();
                all.searchForName(records);
                break;
            case "ANY":
                SearchEngine any = new SearchAny();
                any.searchForName(records);
                break;
            case "NONE":
                SearchEngine none = new SearchNone();
                none.searchForName(records);
                break;
            default:
                System.out.println("Incorrect input");
        }
    }

    private static String[] readFile(String fileName) {
        List<String> records = new ArrayList<>();
        try {
            File myFile = new File(fileName);
            Scanner reader = new Scanner(myFile);
            while (reader.hasNext()) {
                String data = reader.nextLine();
                records.add(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        return records.toArray(new String[0]);
    }

    private static void displayAllPeople(String[] records) {
        System.out.println("=== List of people ===");
        for (String str : records) {
            System.out.println(str);
        }
        System.out.println();
    }
}
