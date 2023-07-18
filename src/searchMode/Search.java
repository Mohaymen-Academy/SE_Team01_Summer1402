package searchMode;

import java.util.Map;
import java.util.Set;

public abstract class Search {
    protected final String query;
    protected Map<String, Set<String>> database;

    public Search(Map<String, Set<String>> database, String query) {
        this.query = query;
        this.database = database;
    }

    abstract Set<String> tokenizer();
}
