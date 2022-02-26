package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<String> properties = Arrays.asList("even", "odd", "buzz", "duck", "palindromic", "gapful", "spy", "square", "sunny", "jumping", "happy", "sad");
    public static List<String> negativeProperties = Arrays.asList("-even", "-odd", "-buzz", "-duck", "-palindromic", "-gapful", "-spy", "-square", "-sunny", "-jumping", "-happy", "-sad");

    public static void main(String[] args) {
        boolean shouldContinue = true;
        Scanner scanner = new Scanner(System.in);
        displayMenu();

        while (shouldContinue) {
            System.out.println("Enter a request:");
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");

            if (inputArray.length == 1) {
                long number = Long.parseLong(inputArray[0]);
                System.out.println();

                if (number == 0) {
                    System.out.println("Goodbye!");
                    shouldContinue = false;
                } else if (isNatural(number)) {
                    System.out.println("Properties of " + number);
                    System.out.println("\teven: " + isEven(number));
                    System.out.println("\todd: " + !isEven(number));
                    System.out.println("\tbuzz: " + isBuzz(number));
                    System.out.println("\tduck: " + isDuck(number));
                    System.out.println("\tpalindromic: " + isPalindromic(number));
                    System.out.println("\tgapful: " + isGapful(number));
                    System.out.println("\tspy: " + isSpy(number));
                    System.out.println("\tsquare: " + isSquare(number));
                    System.out.println("\tsunny: " + isSunny(number));
                    System.out.println("\tjumping: " + isJumping(number));
                    System.out.println("\thappy: " + isHappy(number));
                    System.out.println("\tsad: " + !isHappy(number));
                    System.out.println();
                } else {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                }
            } else if (inputArray.length == 2) {
                long firstParameter = Long.parseLong(inputArray[0]);
                long secondParameter = Long.parseLong(inputArray[1]);

                if (isNatural(firstParameter) && isNatural(secondParameter)) {
                    printWithoutUserProperties(firstParameter, secondParameter);
                    System.out.println();
                } else if (!isNatural(firstParameter) && isNatural(secondParameter)) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                } else if (isNatural(firstParameter) && !isNatural(secondParameter)) {
                    System.out.println("The second parameter should be a natural number.\n");
                } else {
                    System.out.println("Both parameters should be natural numbers.\n");
                }
            } else if (inputArray.length > 2) {
                long firstParameter = Long.parseLong(inputArray[0]);
                long secondParameter = Long.parseLong(inputArray[1]);
                List<String> userProperties = new ArrayList<>();

                for (int i = 2; i < inputArray.length; i++) {
                    userProperties.add(inputArray[i].toLowerCase());
                }

                printWithUserProperties(firstParameter, secondParameter, userProperties);
            }
        }
    }

    public static void displayMenu() {
        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println("Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "\t* the first parameter represents a starting number;\n" +
                "\t* the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
    }

    public static List<String> checkProperties(long number) {
        List<String> resultsList = new ArrayList<>();
        if (isBuzz(number)) {
            resultsList.add("buzz");
        }
        if (isDuck(number)) {
            resultsList.add("duck");
        }
        if (isPalindromic(number)) {
            resultsList.add("palindromic");
        }
        if (isGapful(number)) {
            resultsList.add("gapful");
        }
        if (isEven(number)) {
            resultsList.add("even");
        }
        if (!isEven(number)) {
            resultsList.add("odd");
        }
        if (isSpy(number)) {
            resultsList.add("spy");
        }
        if (isSquare(number)) {
            resultsList.add("square");
        }
        if (isSunny(number)) {
            resultsList.add("sunny");
        }
        if (isJumping(number)) {
            resultsList.add("jumping");
        }
        if (isHappy(number)) {
            resultsList.add("happy");
        }
        if (!isHappy(number)) {
            resultsList.add("sad");
        }

        return resultsList;
    }

    public static void printProperties(long number) {
        List<String> resultsList = checkProperties(number);
        String result = String.join(", ", resultsList);
        System.out.println("\t" + number + " is " + result);
    }

    public static void printWithoutUserProperties(long firstParameter, long secondParameter) {
        long lastNumber = firstParameter + secondParameter;
        long checkingNumber = firstParameter;

        while (checkingNumber < lastNumber) {
            printProperties(checkingNumber);
            checkingNumber++;
        }
    }

    public static void printWithUserProperties(long firstParameter, long secondParameter, List<String> userProperties) {
        List<String> negativeUserProperties = new ArrayList<>();
        List<String> positiveUserProperties = new ArrayList<>();
        long checkingNumber = firstParameter;
        long counter = 0;

        for (String str : userProperties) {
            if (negativeProperties.contains(str)) {
                negativeUserProperties.add(str.substring(1));
            } else {
                positiveUserProperties.add(str);
            }
        }

        if (userInputPropertiesCheck(userProperties) && checkExclusive(userProperties)) {
            while (counter < secondParameter) {
                if (negativeUserProperties.isEmpty()) {
                    if (checkProperties(checkingNumber).containsAll(positiveUserProperties)) {
                        printProperties(checkingNumber);
                        counter++;
                    }
                } else if (positiveUserProperties.isEmpty()) {
                    if (checkNegativeProperties(checkProperties(checkingNumber), negativeUserProperties)) {
                        printProperties(checkingNumber);
                        counter++;
                    }
                } else {
                    if (checkProperties(checkingNumber).containsAll(positiveUserProperties) && checkNegativeProperties(checkProperties(checkingNumber), negativeUserProperties)) {
                        printProperties(checkingNumber);
                        counter++;
                    }
                }
                checkingNumber++;
            }
        }
    }

    public static boolean checkNegativeProperties(List<String> numberProperties, List<String> negativeProperties) {
        for (String num : numberProperties) {
            for (String neg : negativeProperties) {
                if (num.equals(neg)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean userInputPropertiesCheck(List<String> userProperties) {
        List<String> badProperties = new ArrayList<>();
        for (String str : userProperties) {
            if (!properties.contains(str) && !negativeProperties.contains(str)) {
                badProperties.add(str);
            }
        }

        if (badProperties.size() == 0) {
            return true;
        } else if (badProperties.size() > 1) {
            String joined = String.join(", ", badProperties);
            System.out.println("The properties [" + joined.toUpperCase() + "] are wrong.\n" +
                    "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
        } else {
            System.out.println("The property " + badProperties.toString().toUpperCase() + " is wrong.\n" +
                    "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
        }
        return false;
    }

    public static boolean checkExclusive(List<String> userProperties) {
        for (String str1 : userProperties) {
            for (String str2 : userProperties) {
                if ((str1.equals("even") && str2.equals("odd")) || (str2.equals("even") && str1.equals("odd")) ||
                        (str1.equals("duck") && str2.equals("spy")) || (str2.equals("duck") && str1.equals("spy")) ||
                        (str1.equals("sunny") && str2.equals("square")) || (str2.equals("sunny") && str1.equals("square")) ||
                        (str2.equals("happy") && str1.equals("sad")) || (str2.equals("sad") && str1.equals("happy")) ||
                        (str2.equals("-odd") && str1.equals("-even")) || (str2.equals("-even") && str1.equals("-odd")) ||
                        (str1.contains("-") && str1.substring(1).equals(str2)) || (str1.equals(str2.substring(1)) && str2.contains("-"))) {

                    System.out.print("The request contains mutually exclusive properties: [" + str1.toUpperCase() + ", " + str2.toUpperCase() + "]\n"
                            + "There are no numbers with these properties.\n");
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNatural(long number) {
        return number >= 1;
    }

    public static boolean isEven(long number) {
        return number % 2 == 0;
    }

    public static boolean isBuzz(long number) {
        return number % 7 == 0 || number % 10 == 7;
    }

    public static boolean isDuck(long number) {
        while (number != 0) {
            if (number % 10 == 0) {
                return true;
            }
            number /= 10;
        }
        return false;
    }

    public static boolean isPalindromic(long number) {
        long temp = number;
        long reversed = 0;
        long rest;

        while (temp > 0) {
            rest = temp % 10;
            reversed = reversed * 10 + rest;
            temp /= 10;
        }

        return reversed == number;
    }

    public static boolean isGapful(long number) {
        String numberString = String.valueOf(number);

        if (numberString.length() < 3) {
            return false;
        }

        String[] strArray = numberString.split("");
        long divider = Long.parseLong((strArray[0] + strArray[strArray.length - 1]));

        return number % divider == 0;
    }

    public static boolean isSpy(long number) {
        String numberString = String.valueOf(number);
        String[] strArray = numberString.split("");
        int sum = 0;
        int product = 1;

        for (String s : strArray) {
            sum += Long.parseLong(s);
            product *= Long.parseLong(s);
        }

        return sum == product;
    }

    public static boolean isSquare(long number) {
        double squareRoot = Math.sqrt(number);
        return squareRoot - Math.floor(squareRoot) == 0;
    }

    public static boolean isSunny(long number) {
        return isSquare(number + 1);
    }

    public static boolean isJumping(long number) {
        int[] digits = new int[String.valueOf(number).length()];
        long temp;
        int i = 0;
        while (number > 0) {
            temp = (int) number % 10;
            digits[i] = (int) temp;
            number /= 10;
            i++;
        }

        for (int j = 1; j < digits.length; j++) {
            if (digits[j - 1] != digits[j] - 1 && digits[j - 1] != digits[j] + 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isHappy(long number) {
        while (number > 1 && number != 4) {
            number = happyFunction(number);
        }
        return number == 1;
    }

    public static long happyFunction(long number) {
        long total = 0;

        while (number > 0) {
            total += Math.pow(number % 10, 2);
            number /= 10;
        }
        return total;
    }
}

