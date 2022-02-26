package search;

import java.util.*;

public abstract class SearchEngine {

    public abstract void searchForName(String[] records);

    protected Map<String, List<Integer>> invertedIndex(String[] records) {
        Map <String, List<Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < records.length; i++) {
            String[] words = records[i].trim().toLowerCase().split(" ");
            for (String word : words) {
                if (!invertedIndex.containsKey(word)) {
                    invertedIndex.put(word, new ArrayList<>());
                }
                invertedIndex.get(word).add(i);
            }
        }
        return invertedIndex;
    }
}
