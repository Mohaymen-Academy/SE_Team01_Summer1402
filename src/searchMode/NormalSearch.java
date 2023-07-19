package searchMode;

import filter.stemmer.Stemmer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NormalSearch extends Search {
    private final String splitter = "\\P{Alpha}+";
    String[] queryWords;

    public NormalSearch(Map<String, Set<String>> database, String query) {
        super(database, query);
    }

    @Override
    public Set<String> geAllDocuments() {
        queryWords = query.split(splitter);
        return getNames();
    }

    private Set<String> getNames() {
        Set<String> files = new HashSet<>();
        filter.WordValidator validator = new filter.WordValidator();
        for (String word : queryWords) {
            if (validator.isAcceptable(word)) {
                if (database.containsKey(word = new Stemmer().getWordRoot(word))) {
                    files.addAll(database.get(word));
                }
            }
        }
        return files;
    }
}
