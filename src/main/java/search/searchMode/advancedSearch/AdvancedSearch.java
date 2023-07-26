package search.searchMode.advancedSearch;
import dataStructures.InvertedIndex;
import dataStructures.ListCategory;
import dataStructures.Score;
import search.searchMode.Search;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedSearch extends Search {
    private final Set<String> queryWords;
    private final ListCategory listCategory;

    public AdvancedSearch(InvertedIndex invertedIndex, String query) {
        super(query, invertedIndex);
        queryWords = new HashSet<>();
        listCategory = new ListCategory(getAllFiles(invertedIndex.getEngine()));
        finalQueryWords = new HashSet<>();
    }

    public Set<String> getAllFiles(Map<String, Map<String, Score>> engine) {
        Set<String> allFiles = new HashSet<>();
        for (String word : engine.keySet()) allFiles.addAll(engine.get(word).keySet());
        return allFiles;
    }


    public void addToListCategory(String word, ListType type) {
        String stemmedWord = filterWord(word);
        if (word == null) return;
        Set<String> files = getMapValue(invertedIndex.getEngine() , stemmedWord);
        finalQueryWords.add(stemmedWord);
        switch (type) {
            case ESSENTIAL -> listCategory.addToEssentialFile(files);
            case FORBIDDEN -> listCategory.addToForbiddenFile(files);
            case OPTIONAL -> listCategory.addToOptionalFile(files);
        }
    }

    private Set<String> getMapValue(Map<String, Map<String, Score>> map, String key) {
        return map.containsKey(key) ? map.get(key).keySet() : new HashSet<>();
    }

    private void categorizeWords() {
        for (String word : queryWords) {
            switch (word.charAt(0)) {
                case '+' -> addToListCategory(word.substring(1), ListType.OPTIONAL);
                case '-' -> addToListCategory(word.substring(1), ListType.FORBIDDEN);
                default -> addToListCategory(word, ListType.ESSENTIAL);
            }
        }
    }


    @Override
    public Set<String> getAllDocuments() {
        String regex = "(\\-|\\+)?[\\da-zA-Z]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(query);
        while (m.find()) {
            queryWords.add(m.group());
        }
        categorizeWords();
        return listCategory.intersectFiles();

    }
}
