package searchMode.advancedSearch;
import dataStructures.InvertedIndex;
import dataStructures.ListCategory;
import searchMode.Search;
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
    }

    public Set<String> getAllFiles(Map<String, Set<String>> engine) {
        Set<String> allFiles = new HashSet<>();
        for (String word : engine.keySet()) allFiles.addAll(engine.get(word));
        return allFiles;
    }


    public void addToListCategory(String word, ListType type) {
        word = invertedIndex.getNormalizer().normalize(word);
        if (!invertedIndex.getWordValidator().isAcceptable(word))
            return;
        String stemmedWord = invertedIndex.checkForStem(word);
        Set<String> files;
        files = getMapValue(invertedIndex.getEngine() , stemmedWord);
        switch (type) {
            case ESSENTIAL -> listCategory.addToEssentialFile(files);
            case FORBIDDEN -> listCategory.addToForbiddenFile(files);
            case OPTIONAL -> listCategory.addToOptionalFile(files);
        }
    }

    private Set<String> getMapValue(Map<String, Set<String>> map, String key) {
        return map.containsKey(key) ? map.get(key) : new HashSet<>();
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
