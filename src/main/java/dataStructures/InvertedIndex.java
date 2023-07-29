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

    private void addWordsToMap(Map<String, Long> words, String title, long size) {
        for (Map.Entry<String, Long> entry : words.entrySet()) {
            addWordToMap(title, size, entry);
        }
    }

    private void addWordToMap(String title, long size, Map.Entry<String, Long> entry) {
        if (indexMap.containsKey(entry.getKey()))
            indexMap.get(entry.getKey()).put(title, new Score(size, entry.getValue()));
        else {
            Map<String, Score> newMap = new HashMap<>();
            newMap.put(title, new Score(size, entry.getValue()));
            indexMap.put(entry.getKey(), newMap);
        }
    }

    public String stem(String word) {
        PorterStemmer ptStm = new PorterStemmer();
        ptStm.setCurrent(word.toLowerCase());
        ptStm.stem();
        return ptStm.getCurrent();
    }

    public String checkForStem(String word) {
        return doStem ? stem(word) : word;
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

        addWordsToMap(words, title, size);
    }
}