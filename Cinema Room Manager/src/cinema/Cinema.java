package cinema;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {
    public static Scanner scanner = new Scanner(System.in);
    public static int purchasedTickets = 0;
    public static int currentIncome = 0;

    public static void main(String[] args) {

        System.out.println("Enter the number of rows:");
        int r = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int s = scanner.nextInt();
        String[][] cinema = new String[r][s];

        for (String[] strings : cinema) {
            Arrays.fill(strings, "S ");
        }

        boolean shouldContinue = true;

        while (shouldContinue) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");

            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1:
                    displaySeats(cinema, s);
                    break;
                case 2:
                    buyTicket(r, s, cinema);
                    break;
                case 3:
                    showStatistics(r, s);
                    break;
                case 0:
                    shouldContinue = false;
                    break;
                default:
                    System.out.println("This is not a valid input!");
                    System.out.println();
            }
        }
    }

    public static void showStatistics(int rows, int seats) {
        int totalSeats = rows * seats;
        int income;

        if (totalSeats <= 60) {
            income = totalSeats * 10;
        } else {
            if (rows % 2 == 0) {
                income = (rows / 2) * seats * 10 + (rows / 2) * seats * 8;
            } else {
                income = ((rows / 2) * seats * 10) + (((rows / 2) + 1) * seats * 8);
            }
        }

        double percentage = (double) purchasedTickets / totalSeats * 100;

        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%%n", percentage);
        System.out.printf("Current income $%d%n", currentIncome);
        System.out.printf("Total income: $%d%n", income);
        System.out.println();
    }

    public static void displaySeats(String[][] cinema, int seats) {
        System.out.println("Cinema:");
        for (int k = 0; k <= seats; k++) {
            if (k == 0) {
                System.out.print("  ");
            } else {
                System.out.print(k + " ");
            }
        }
        System.out.println();

        for (int i = 0; i < cinema.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void buyTicket(int rows, int seats, String[][] cinema) {
        while (true) {
            System.out.println("Enter a row number:");
            int row = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat = scanner.nextInt();

            if (row < 1 || row > cinema.length || seat < 1 || seat > cinema[row - 1].length) {
                System.out.println("Wrong input!\n");
            } else if (Objects.equals(cinema[row - 1][seat - 1], "B ")) {
                System.out.println("That ticket has already been purchased!\n");
            } else {
                int totalSeats = rows * seats;
                int ticketPrice;

                if (totalSeats <= 60) {
                    ticketPrice = 10;
                } else {
                    if (row <= (rows / 2)) {
                        ticketPrice = 10;
                    } else {
                        ticketPrice = 8;
                    }
                }

                cinema[row - 1][seat - 1] = "B ";
                System.out.printf("Ticket price: $%d%n", ticketPrice);
                purchasedTickets++;
                currentIncome += ticketPrice;
                System.out.println();
                break;
            }
        }
    }
}