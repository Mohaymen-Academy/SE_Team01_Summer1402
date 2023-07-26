package search;

import dataStructures.InvertedIndex;
import dataStructures.Score;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class Sorter {
    private final Set<String> queryWords;
    private final InvertedIndex invertedIndex;
    private final Set<String> finalFiles;
    private final Map<String, Double> filesScores = new HashMap<>();

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

    public List<Map.Entry<String, Double>> sort() {
        fillInitialMap();
        calculateFileScores();
        List<Map.Entry<String, Double>> sorted_values = new ArrayList<>(filesScores.entrySet());
        sorted_values.sort(Map.Entry.comparingByValue());
        return sorted_values;
    }

}
