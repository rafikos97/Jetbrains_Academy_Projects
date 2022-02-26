package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, String> arguments = new HashMap<>();
        String dataToProcess;
        String encrypted;
        String decrypted;

        for (int i = 0; i < args.length; i+=2) {
            arguments.put(args[i], args[i + 1]);
        }

        String mode = arguments.getOrDefault("-mode", "enc");
        int key = Integer.parseInt(arguments.getOrDefault("-key", "0"));
        String data = arguments.getOrDefault("-data", "");
        String in = arguments.getOrDefault("-in", "");
        String out = arguments.getOrDefault("-out", "");
        String alg = arguments.getOrDefault("-alg", "shift");


        Path pathIn = Paths.get(System.getProperty("user.dir") + "\\" + in);
        Path pathOut = Paths.get(System.getProperty("user.dir") + "\\" + out);

        if ("".equals(data)) {
            String dataFromFile = readFile(pathIn);
            if ("".equals(dataFromFile)) {
                System.out.println("Error - file is empty");
                return;
            } else {
                dataToProcess = dataFromFile;
            }
        } else {
             dataToProcess = data;
        }


        if ("shift".equals(alg)) {
            encrypted = shiftEncrypt(dataToProcess, key);
            decrypted = shiftDecrypt(dataToProcess, key);
        } else {
            encrypted = unicodeEncrypt(dataToProcess, key);
            decrypted = unicodeDecrypt(dataToProcess, key);
        }

        switch (mode) {
            case "enc":
                if (out == null) {
                    System.out.println(encrypted);
                } else {
                    writeFile(pathOut, encrypted);
                }
                break;
            case "dec":
                if (out == null) {
                    System.out.println(decrypted);
                } else {
                    writeFile(pathOut, decrypted);
                }
                break;
            default:
                System.out.println("Error - wrong input!");
        }
    }

    private static String unicodeEncrypt(String str, int key) {
        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            sb.append((char) (c + key));
        }
        return sb.toString();
    }

    private static String unicodeDecrypt(String str, int key) {
        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            sb.append((char) (c - key));
        }
        return sb.toString();
    }

    private static String shiftEncrypt(String str, int key) {
        char lowerBound;
        char upperBound;

        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                lowerBound = 'A';
                upperBound = 'Z';
            } else {
                lowerBound = 'a';
                upperBound = 'z';
            }
            if (c >= lowerBound && c <= upperBound) {
                char test = (char) (c + key);

                if (test > upperBound) {
                    sb.append((char) (c - (26 - key)));
                } else {
                    sb.append((char) (c + key));
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static String shiftDecrypt(String str, int key) {
        char lowerBound;
        char upperBound;

        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            if (Character.isUpperCase(c)) {
                lowerBound = 'A';
                upperBound = 'Z';
            } else {
                lowerBound = 'a';
                upperBound = 'z';
            }
            if (c >= lowerBound && c <= upperBound) {
                char test = (char) (c - key);

                if (test < lowerBound) {
                    sb.append((char) (c + (26 - key)));
                } else {
                    sb.append((char) (c - key));
                }
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static String readFile(Path pathIn) {
        File file = new File(String.valueOf(pathIn));
        String dataFromFile = "";

        try (Scanner scanner = new Scanner(file)) {
            dataFromFile = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error - no file found: " + pathIn);
        }

        return dataFromFile;
    }

    private static void writeFile(Path pathOut, String data) {
        File file = new File(String.valueOf(pathOut));

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error - enable to write file");
        }

    }
}