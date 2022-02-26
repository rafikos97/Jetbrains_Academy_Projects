package search;

import java.util.*;

public class SearchNone extends SearchEngine {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void searchForName(String[] records) {
        Map<String, List<Integer>> invertedIndex = invertedIndex(records);
        HashSet<String> recordsSet = new HashSet<>(Arrays.asList(records));


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

        recordsSet.removeAll(output);

        int size = recordsSet.size();
        if (size > 0) {
            System.out.println(size + " persons found:");

            for (String person : recordsSet) {
                System.out.println(person);
            }
        } else {
            System.out.println("No matching people found.");
        }
        System.out.println();
    }
}
