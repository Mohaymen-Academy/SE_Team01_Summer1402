package searchMode;

import dataStructures.InvertedIndex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NormalSearch extends Search {
    
    private Set<String> queryWords;

    public NormalSearch(InvertedIndex invertedIndex, String query) {
        super(invertedIndex, query);
    }

    @Override
    public Set<String> getAllDocuments() {
        String splitter = "\\P{Alpha}+";
        queryWords =  new HashSet<>(Arrays.asList(query.split(splitter)));
        return getNames();
    }

    private Set<String> getNames() {
        Set<String> files = new HashSet<>();
        for (String word : queryWords) {
            word = invertedIndex.getNormalizer().normalize(word);
            if (invertedIndex.getWordValidator().isAcceptable(word)) {
                word = invertedIndex.getWordRoot(word);
                if (invertedIndex.getEngine().containsKey(word)) {
                    files.addAll(invertedIndex.getEngine().get(word));
                }
            }
        }
        return files;
    }
}
