package searchMode;

import dataStructures.InvertedIndex;
import java.util.Set;

public abstract class Search {
    protected final String query;
    protected InvertedIndex invertedIndex;

    public Search(InvertedIndex database, String query) {
        this.query = query;
        this.invertedIndex = database;
    }

    public void printDocuments(Set<String> documentTitles) {
        if (documentTitles.isEmpty()) System.out.println("There is no document.");
        for (String title : documentTitles) System.out.println(title);
    }

    public abstract Set<String> getAllDocuments();


}
