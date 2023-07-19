package searchMode;

import dataStructures.InvertedIndex;
import filter.stemmer.Stemmer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NormalSearch extends Search {
    private Set<String> queryWords;

    public NormalSearch(InvertedIndex database, String query) {
        super(database, query);
    }

    @Override
    public Set<String> geAllDocuments() {
        String splitter = "\\P{Alpha}+";
        queryWords =  new HashSet<>(Arrays.asList(query.split(splitter)));
        return getNames();
    }

    private Set<String> getNames() {
        Set<String> files = new HashSet<>();
        queryWords = database.getNormalizer().normalize(queryWords);
        for (String word : queryWords) {
            if (database.getWordValidator().isAcceptable(word)) {
                word = database.doStem() ? new Stemmer().getWordRoot(word) : word;
                if (database.getEngine().containsKey(word)) {
                    files.addAll(database.getEngine().get(word));
                }
            }
        }
        return files;
    }
}
