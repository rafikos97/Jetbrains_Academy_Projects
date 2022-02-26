package tracker;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static tracker.PatternChecker.*;

public class Tracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Student> studentsDB = StudentsHandler.getStudents();

    public static void startTracker() {
        boolean shouldContinue = true;

        System.out.println("Learning Progress Tracker");

        while (shouldContinue) {
            String userInput = scanner.nextLine();

            if (userInput.isBlank()) {
                System.out.println("No input.");
            } else if (userInput.equals("exit")) {
                System.out.println("Bye!");
                shouldContinue = false;
            } else if (userInput.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if (userInput.equals("add students")) {
                getStudentCredentials();
            } else if (userInput.equals("list")) {
                StudentsHandler.printStudentsList();
            } else if (userInput.equals("find")) {
                findStudent();
            } else if (userInput.equals("add points")) {
                addPoints();
            } else if (userInput.equals("statistics")) {
                getStatistics();
            } else if (userInput.equals("notify")) {
                doNotify();
            } else {
                System.out.println("Error: unknown command!");
            }
        }
    }

    private static void getStudentCredentials() {
        int count = 0;
        System.out.println("Enter student credentials or 'back' to return:");

        while (true) {
            String credentials = scanner.nextLine();
            String[] credentialsArray = credentials.split("\\s");

            if (credentials.equals("back")) {
                System.out.println("Total " + count + " students have been added.");
                return;
            } else if (credentialsArray.length < 3) {
                System.out.println("Incorrect credentials.");
            } else if (checkCredentials(credentialsArray)) {
                count ++;
            }
        }
    }

    private static boolean checkCredentials(String[] credentials) {
        String email = credentials[credentials.length - 1];
        String firstName = credentials[0];

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < credentials.length - 1; i++) {
            sb.append(credentials[i]).append(" ");
        }

        String secondName = sb.toString().trim();

        if (!checkFirstName(firstName)) {
            System.out.println("Incorrect first name.");
            return false;
        } else if (!checkLastName(secondName)) {
            System.out.println("Incorrect last name.");
            return false;
        } else if (!checkEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        } else if (!checkEmailUniqueness(email)) {
            System.out.println("This email is already taken.");
            return false;
        } else {
            System.out.println("The student has been added.");
            StudentsHandler.addStudent(firstName, secondName, email);
            return true;
        }
    }

    private static boolean checkEmailUniqueness(String email) {
        List<String> emails = StudentsHandler.getEmails();

        for (String str : emails) {
            if (str.equals(email)) {
                return false;
            }
        }
        return true;
    }

    private static void findStudent() {
        System.out.println("Enter an id or 'back' to return");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                return;
            } else {
                int id = Integer.parseInt(input);
                if (studentsDB.containsKey(id)) {
                    studentsDB.get(id).printPoints();
                } else {
                    System.out.println("No students found for id=" + id);
                }
            }
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("back")) {
                return;
            }

            if (checkPointsInput(input)) {
                int delimiter = input.indexOf(" ");
                String id = input.substring(0, delimiter);
                String points = input.substring(delimiter + 1);

                if (checkId(id) && studentsDB.containsKey(Integer.parseInt(id))) {
                    Student student = studentsDB.get(Integer.parseInt(id));
                    student.addStudentPoints(points);
                    System.out.println("Points updated.");
                } else {
                    System.out.println("No student is found for id=" + id);
                }
            } else {
                System.out.println("Incorrect points format.");
            }
        }
    }

    private static void getStatistics() {
        OverallStatistics stat = new OverallStatistics();

        System.out.println("Type the name of a course to see details or 'back' to quit:");
        stat.printStatistics();

        while (true) {
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "back":
                    return;
                case "java":
                    SingleCourseStatistics.showJavaStatistics();
                    break;
                case "databases":
                    SingleCourseStatistics.showDBStatistics();
                    break;
                case "dsa":
                    SingleCourseStatistics.showDSAStatistics();
                    break;
                case "spring":
                    SingleCourseStatistics.showSpringStatistics();
                    break;
                default:
                    System.out.println("Unknown course.");
            }
        }
    }

    private static void doNotify() {
        int count = 0;
        for (Student student : studentsDB.values()) {
            count += student.printNotify();
        }

        System.out.println("Total " + count + " students have been notified.");
    }
}