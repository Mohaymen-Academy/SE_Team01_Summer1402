package search.searchMode;

import dataStructures.InvertedIndex;
import lombok.RequiredArgsConstructor;
import search.Sorter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public abstract class Search {
    protected final String query;
    protected final InvertedIndex invertedIndex;
    protected Set<String> finalQueryWords = new HashSet<>();

    public List<String> sortResult(Set<String> finalFiles) {
        return new Sorter(finalQueryWords, invertedIndex, finalFiles).sort();
    }

    public String filterWord(String word) {
        word = invertedIndex.getNormalizer().normalize(word);
        if (!invertedIndex.getWordValidator().isAcceptable(word))
            return null;
        return invertedIndex.checkForStem(word);
    }

    public abstract Set<String> getAllDocuments();


}