package searchMode;

import DS.FileManager;
import filter.stemmer.Stemmer;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NormalSearch extends Search {
    String[] queryWords;

    public NormalSearch(Map<String, Set<String>> database, String query) {
        super(database, query);
    }

    @Override
    Set<String> tokenizer() {
        queryWords = query.split("\\P{Alpha}+");
        return getDocs();
    }

    private Set<String> getDocs() {
        Set<String> files = new HashSet<>();
        for (String word : queryWords) {
            filter.WordValidator validator = new filter.WordValidator();
            if (validator.isAcceptable(word)) {
                files.addAll(database.get(new Stemmer().getWordRoot(word)));
            }
        }
        return files;
    }
}
