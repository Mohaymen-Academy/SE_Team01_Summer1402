package searchMode.advancedSearch;

import DS.InvertedIndex;
import DS.ListCategory;
import filter.stemmer.Stemmer;
import searchMode.Search;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedSearch extends Search {
    private final String regex = "(\\-|\\+)?[a-zA-Z]+";
    List<String> queryWords;
    private final ListCategory lists;


    public AdvancedSearch(InvertedIndex database, String query) {
        super(database, query);
        queryWords = new ArrayList<>();
        lists = new ListCategory();
    }


    public void categorizeWord(String word, ListType type) {
        if (!new filter.WordValidator().isAcceptable(word))
            return;
        String stemmedWord = new Stemmer().getWordRoot(word);
        Set<String> files = new HashSet<>();
        if (database.getEngine().containsKey(stemmedWord))
            files = database.getEngine().get(stemmedWord);
        switch (type) {
            case ESSENTIAL:
                lists.addToEssentialFile(files);
                break;
            case FORBIDDEN:
                lists.addToForbiddenFile(files);
                break;
            case OPTIONAL:
                lists.addToOptionalFile(files);
                break;
        }

    }

    private void getDocs() {
        for (String word : queryWords) {
            switch (word.charAt(0)) {
                case '+':
                    categorizeWord(word.substring(1), ListType.OPTIONAL);
                    break;
                case '-':
                    categorizeWord(word.substring(1), ListType.FORBIDDEN);
                    break;
                default:
                    categorizeWord(word, ListType.ESSENTIAL);
                    break;
            }
        }
    }


    @Override
    public Set<String> geAllDocuments() {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(query);
        while (m.find()) {
            queryWords.add(m.group());
        }
        getDocs();
        return lists.intersectFiles();
    }
}
