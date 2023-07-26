package search.searchMode;

import dataStructures.InvertedIndex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NormalSearch extends Search {

    private Set<String> queryWords;

    public NormalSearch(InvertedIndex invertedIndex, String query) {
        super(query, invertedIndex);
    }

    @Override
    public Set<String> getAllDocuments() {
        String splitter = "[^\\da-zA-Z]+";
        queryWords = new HashSet<>(Arrays.asList(query.split(splitter)));
        return getNames();
    }

    private Set<String> getNames() {
        Set<String> finalFiles = new HashSet<>();
        queryWords.stream()
                .map(this::filterWord)
                .filter(Objects::nonNull)
                .filter(invertedIndex.getIndexMap()::containsKey)
                .forEach(word -> {
                    finalQueryWords.add(word);
                    finalFiles.addAll(invertedIndex.getIndexMap().get(word).keySet());
                });
        return finalFiles;
    }
}
