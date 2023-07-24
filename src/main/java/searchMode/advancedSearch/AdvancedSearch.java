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
        if (!invertedIndex.getWordValidator().isAcceptable(word))
            return;
        String stemmedWord = invertedIndex.getWordRoot(word);
        Set<String> files = new HashSet<>();
        if (invertedIndex.getEngine().containsKey(stemmedWord)) {
            files = invertedIndex.getEngine().get(stemmedWord);
        }
        switch (type) {
            case ESSENTIAL:
                listCategory.addToEssentialFile(files);
                break;
            case FORBIDDEN:
                listCategory.addToForbiddenFile(files);
                break;
            case OPTIONAL:
                listCategory.addToOptionalFile(files);
                break;
        }

    }

    private void categorizeWords() {
        for (String word : queryWords) {
            word = invertedIndex.getNormalizer().normalize(word);
            switch (word.charAt(0)) {
                case '+':
                    addToListCategory(word.substring(1), ListType.OPTIONAL);
                    break;
                case '-':
                    addToListCategory(word.substring(1), ListType.FORBIDDEN);
                    break;
                default:
                    addToListCategory(word, ListType.ESSENTIAL);
                    break;
            }
        }
    }


    @Override
    public Set<String> getAllDocuments() {
        String regex = "(\\-|\\+)?[a-zA-Z]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(query);
        while (m.find()) {
            queryWords.add(m.group());
        }
        categorizeWords();
        return listCategory.intersectFiles();
    }
}
