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
                Score score = invertedIndex.getEngine().get(word).get(fileName);
                if (score != null)
                    filesScores.replace(fileName, filesScores.get(fileName) + score.getScore());
            }
        }
    }

    public List<String> sort() {
        fillInitialMap();
        calculateFileScores();
        List<Map.Entry<String, Double>> list = new ArrayList<>(filesScores.entrySet());
        list.sort(Map.Entry.comparingByValue());
        List<String> result = new LinkedList<>();
        list.forEach(entry -> result.add(entry.getKey()));
        return result;
    }

}
