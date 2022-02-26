package tictactoe;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[][] board = new String[3][3];

        for (String[] strings : board) {
            Arrays.fill(strings, " ");
        }

        System.out.println("---------");
        System.out.println("| " + board[0][0] + " " + board[0][1] + " " + board[0][2] + " |");
        System.out.println("| " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |");
        System.out.println("| " + board[2][0] + " " + board[2][1] + " " + board[2][2] + " |");
        System.out.println("---------");

        int turn = 0;
        // if 0 -> X turn, if 1 -> O turn

        while (!winnerCheck(board)) {
            try {
                System.out.println("Enter the coordinates: ");
                String input = scanner.nextLine();
                String[] pieces = input.split(" ");
                int x = Integer.parseInt(pieces[0]);
                int y = Integer.parseInt(pieces[1]);

                if (x < 1 || x > 3 || y < 1 || y > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                } else if (!board[x - 1][y - 1].contains(" ")) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else if (turn == 0) {
                        board[x - 1][y - 1] = "X";
                        turn = 1;
                } else {
                        board[x - 1][y - 1] = "O";
                        turn = 0;
                }

                System.out.println("---------");
                System.out.println("| " + board[0][0] + " " + board[0][1] + " " + board[0][2] + " |");
                System.out.println("| " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |");
                System.out.println("| " + board[2][0] + " " + board[2][1] + " " + board[2][2] + " |");
                System.out.println("---------");

            } catch (InputMismatchException ime) {
                System.out.println("You should enter numbers!");
                scanner.nextLine();
            }
        }
    }

    public static boolean winnerCheck(String[][] board) {
        List<String> lines = new ArrayList<>();
        String line;
        boolean winX = false;
        boolean winO = false;

        line = board[0][0] + board[0][1] + board[0][2];
        lines.add(line);
        line = board[1][0] + board[1][1] + board[1][2];
        lines.add(line);
        line = board[2][0] + board[2][1] + board[2][2];
        lines.add(line);
        line = board[0][0] + board[1][0] + board[2][0];
        lines.add(line);
        line = board[0][1] + board[1][1] + board[2][1];
        lines.add(line);
        line = board[0][2] + board[1][2] + board[2][2];
        lines.add(line);
        line = board[0][0] + board[1][1] + board[2][2];
        lines.add(line);
        line = board[0][2] + board[1][1] + board[2][0];
        lines.add(line);


        int empty = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].contains(" ")) {
                    empty++;
                }
            }
        }

        for (String singleLine : lines) {
            if (singleLine.contains("XXX")) {
                winX = true;
            } else if (singleLine.contains("OOO")) {
                winO = true;
            }
        }

        if ((!winO && !winX) && empty == 0) {
            System.out.println("Draw");
            return true;
        } else if (winO) {
            System.out.println("O wins");
            return true;
        } else if (winX) {
            System.out.println("X wins");
            return true;
        }
        return false;
    }
}
