package sort;

import dataStructures.InvertedIndex;
import dataStructures.Score;
import java.util.*;

public class IDFSorter extends Sorter {
    private final Map<String, Double> filesScores = new HashMap<>();

    public IDFSorter(InvertedIndex invertedIndex) {
        super(invertedIndex);
    }

    private void fillInitialMap() {
        finalFiles.forEach(key -> filesScores.put(key, 0d));
    }

    private void calculateFileScores() {
        for (String word : queryWords) {
            for (String fileName : finalFiles) {
                Score score = null;
                if (invertedIndex.getIndexMap().containsKey(word)) {
                    score = invertedIndex.getIndexMap().get(word).get(fileName);
                }
                if (score != null)
                    filesScores.replace(fileName, filesScores.get(fileName) + score.getScore());
            }
        }
    }

    @Override
    public List<Map.Entry<String, Double>> sort() {
        fillInitialMap();
        calculateFileScores();
        List<Map.Entry<String, Double>> sorted_values = new ArrayList<>(filesScores.entrySet());
        sorted_values.sort(Map.Entry.comparingByValue());
        return sorted_values;
    }

}
