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
//        for (String word : queryWords) {
//            word = invertedIndex.getNormalizer().normalize(word);
//            if (invertedIndex.getWordValidator().isAcceptable(word)) {
//                word = invertedIndex.checkForStem(word);
//                if (invertedIndex.getEngine().containsKey(word)) {
//                    files.addAll(invertedIndex.getEngine().get(word).keySet());
//                }
//            }
//        }
        Set<String> finalFiles = new HashSet<>();
        queryWords.stream()
                .map(this::filterWord)
                .filter(Objects::nonNull)
                .filter(invertedIndex.getEngine()::containsKey)
                .forEach(word -> {
                    finalQueryWords.add(word);
                    finalFiles.addAll(invertedIndex.getEngine().get(word).keySet());
                });
//        queryWords.stream()
//                .map(invertedIndex.getNormalizer()::normalize)
//                .filter(invertedIndex.getWordValidator()::isAcceptable)
//                .map(invertedIndex::checkForStem)
//                .filter(invertedIndex.getEngine()::containsKey)
//                .forEach(word -> finalFiles.addAll(invertedIndex.getEngine().get(word).keySet()));
        return finalFiles;
    }
}
