package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SearchAny extends SearchEngine {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void searchForName(String[] records) {
        Map<String, List<Integer>> invertedIndex = invertedIndex(records);

        System.out.println("Enter a name or email to search all suitable people.");
        String input = scanner.nextLine().trim().toLowerCase();
        String[] inputArray = input.split(" ");
        List<String> output = new ArrayList<>();

        for (String name : inputArray) {
            if (invertedIndex.containsKey(name)) {
                List<Integer> linesToRead = invertedIndex.get(name);

                for (Integer line : linesToRead) {
                    output.add(records[line]);
                }
            }
        }

        int size = output.size();
        if (size > 0) {
            System.out.println(size + " persons found:");

            for (String person : output) {
                System.out.println(person);
            }
        } else {
            System.out.println("No matching people found.");
        }
        System.out.println();
    }
}
