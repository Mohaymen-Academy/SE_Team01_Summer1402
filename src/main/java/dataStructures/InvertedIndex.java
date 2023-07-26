package dataStructures;

import filter.*;
import filter.normalizer.*;
import filter.tokenizer.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tartarus.snowball.ext.PorterStemmer;

import java.util.*;

@Builder
@Getter
@Setter
public class InvertedIndex {

    private final Map<String, Map<String, Score>> indexMap = new HashMap<>();
    @Builder.Default
    private Normalizer normalizer = new UpperCaseNormalizer();
    @Builder.Default
    private Tokenizer tokenizer = new SplitTokenizer("[^\\da-zA-Z]+");
    @Builder.Default
    private WordValidator wordValidator = new WordValidator(true);
    @Builder.Default
    private boolean doStem = true;

    private void addWordToEngine(Map<String, Long> words, String title, long size) {

        for (Map.Entry<String, Long> en : words.entrySet()) {
            if (indexMap.containsKey(en.getKey()))
                indexMap.get(en.getKey()).put(title, new Score(size, en.getValue()));
            else {
                Map<String, Score> newMap = new HashMap<>();
                newMap.put(title, new Score(size, en.getValue()));
                indexMap.put(en.getKey(), newMap);
            }
        }
    }

    public String wordStemmer(String word) {
        PorterStemmer ptStm = new PorterStemmer();
        ptStm.setCurrent(word.toLowerCase());
        ptStm.stem();
        return ptStm.getCurrent();
    }

    public String checkForStem(String word) {
        return isDoStem() ? wordStemmer(word) : word;
    }

    private Map<String, Long> filterContext(String context) {
        Map<String, Long> tokenizedWords = tokenizer.tokenize(context);
        Map<String, Long> result = new HashMap<>();
        String key;
        for (Map.Entry<String, Long> e : tokenizedWords.entrySet()) {
            key = e.getKey();
            if (wordValidator.isAcceptable(key)) {
                key = normalizer.normalize(checkForStem(key));
                if (result.containsKey(key))
                    result.replace(key, e.getValue() + result.get(key));
                else
                    result.put(key, e.getValue());
            }
        }
        return result;
    }

    public void addNewContext(String title, String context) {
        Map<String, Long> words = filterContext(context);
        long size = words.values().stream().mapToLong(Long::longValue).sum();

        addWordToEngine(words, title, size);
    }

    public Map<String, Map<String, Score>> getIndexMap() {
        return indexMap;
    }
}