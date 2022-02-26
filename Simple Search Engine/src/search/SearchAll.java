package search;

import java.util.*;

public class SearchAll extends SearchEngine {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void searchForName(String[] records) {
        Map<String, List<Integer>> invertedIndex = invertedIndex(records);

        System.out.println("Enter a name or email to search all suitable people.");
        String input = scanner.nextLine().trim().toLowerCase();
        String[] inputArray = input.split(" ");

        HashSet<Integer> keys;

        if (invertedIndex.containsKey(inputArray[0])) {
            keys = new HashSet<>(invertedIndex.get(inputArray[0]));
        } else {
            System.out.println("No matching people found.");
            return;
        }

        for (int i = 1; i < inputArray.length; i++) {
            if (invertedIndex.containsKey(inputArray[i])) {
                keys.retainAll(invertedIndex.get(inputArray[i]));
            } else {
                System.out.println("No matching people found.");
                return;
            }
        }

        if (!keys.isEmpty()) {
            int size = keys.size();
            System.out.println(size + " persons found:");

            for (Integer key : keys) {
                System.out.println(records[key]);
            }
        }

        System.out.println();
    }
}
