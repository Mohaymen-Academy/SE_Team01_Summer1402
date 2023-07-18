package searchMode;

import DS.ListCategory;
import filter.stemmer.Stemmer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdvancedSearch extends Search {
    private final String regex = "(\\-|\\+)?[a-zA-Z]+";
    List<String> queryWords;
    private ListCategory lists;


    public AdvancedSearch(Map<String, Set<String>> database, String query) {
        super(database, query);
        queryWords = new ArrayList<>();
        lists = new ListCategory();
    }


    public void categorizeFiles(String word, ListType type) {
        filter.WordValidator validator = new filter.WordValidator();
        if (!validator.isAcceptable(word)) return;
        String stemmedWord = new Stemmer().getWordRoot(word);
        Set<String> files = new HashSet<>();
        if (database.containsKey(stemmedWord))
            files = database.get(word);
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
                    categorizeFiles(word.substring(1), ListType.OPTIONAL);
                    break;
                case '-':
                    categorizeFiles(word.substring(1), ListType.FORBIDDEN);
                    break;
                default:
                    categorizeFiles(word, ListType.ESSENTIAL);
                    break;
            }
        }
        lists.intersectFiles();
    }


    @Override
    Set<String> tokenizer() {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(query);
        while (m.find()) {
            queryWords.add(m.group());
        }
        getDocs();
        return lists.intersectFiles();
    }
}
