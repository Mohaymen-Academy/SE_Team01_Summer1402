package searchMode.advancedSearch;

import dataStructures.InvertedIndex;
import dataStructures.ListCategory;
import filter.stemmer.Stemmer;
import searchMode.Search;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedSearch extends Search {
    private final Set<String> queryWords;
    private final ListCategory listCategory;


    public AdvancedSearch(InvertedIndex database, String query) {
        super(database, query);
        queryWords = new HashSet<>();
        listCategory = new ListCategory();
    }


    public void addToListCategory(String word, ListType type) {
        if (!database.getWordValidator().isAcceptable(word))
            return;
        String stemmedWord = database.doStem() ?
                new Stemmer().getWordRoot(word) : word;
        Set<String> files = new HashSet<>();
        if (database.getEngine().containsKey(stemmedWord)) {
            files = database.getEngine().get(stemmedWord);
        }
        switch (type) {
            case ESSENTIAL:
                System.out.println(files.isEmpty());
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
        Set<String> normalizedWords =
                database.getNormalizer().normalize(new HashSet<>(queryWords));
        for (String word : normalizedWords) {
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
    public Set<String> geAllDocuments() {
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
