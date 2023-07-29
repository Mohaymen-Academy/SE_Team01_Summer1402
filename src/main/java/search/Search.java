package search;

import dataStructures.InvertedIndex;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sort.IDFSorter;
import sort.Sorter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public abstract class Search {

    protected final String query;
    protected final InvertedIndex invertedIndex;
    protected Set<String> finalQueryWords = new HashSet<>();
    protected Set<String> finalDocs;
    @Setter
    private Sorter sorter;


    public List<Map.Entry<String, Double>> sortResult() {
        sorter.setFinalFiles(finalDocs);
        sorter.setQueryWords(finalQueryWords);
        return sorter.sort();
    }

    public String filterWord(String word) throws Exception {
        if (!invertedIndex.getWordValidator().isAcceptable(word))
            throw new Exception();
        return invertedIndex.getNormalizer().normalize(invertedIndex.checkForStem(word));
    }

    public abstract Set<String> getAllDocuments();


}
