package searchMode;

import DS.InvertedIndex;
import java.util.Set;

public abstract class Search {
    protected final String query;
    protected InvertedIndex database;

    public Search(InvertedIndex database, String query) {
        this.query = query;
        this.database = database;
    }

    public abstract Set<String> geAllDocuments();
}
