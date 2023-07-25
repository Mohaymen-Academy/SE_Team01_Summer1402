package search.searchMode;

import dataStructures.InvertedIndex;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public abstract class Search {
    protected final String query;
    protected final InvertedIndex invertedIndex;

    public void printDocuments(Set<String> documentTitles) {
        if (documentTitles.isEmpty()) System.out.println("There is no document.");
        for (String title : documentTitles) System.out.println(title);
    }

    public abstract Set<String> getAllDocuments();


}
