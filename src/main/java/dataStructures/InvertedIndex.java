package dataStructures;

import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.tartarus.snowball.ext.PorterStemmer;

import java.util.*;

@Builder
@Getter
@Setter
public class InvertedIndex {

    private final Map<String, Map<String, Score>> engine = new HashMap<>();
    @Builder.Default
    private Normalizer normalizer = new UpperCaseNormalizer();
    @Builder.Default
    private Tokenizer tokenizer = new SplitTokenizer("[^\\da-zA-Z]+");
    @Builder.Default
    private WordValidator wordValidator = new WordValidator(true);
    @Builder.Default
    private boolean doStem = true;
    private static PorterStemmer ptStm = new PorterStemmer();

    public InvertedIndex(Normalizer normalizer, Tokenizer tokenizer, WordValidator wordValidator, boolean doStem) {
        this.normalizer = normalizer;
        this.tokenizer = tokenizer;
        this.wordValidator = wordValidator;
        this.doStem = doStem;
    }

    private void addWordToEngine(Map<String, Long> words, String title, long size) {
        for (Map.Entry<String, Long> en : words.entrySet()) {
            if (engine.containsKey(en.getKey())) {
                engine.get(en.getKey()).put(title, new Score(size, en.getValue()));
            } else {
                Map<String, Score> new_map = new HashMap<>();
                new_map.put(title, new Score(size, en.getValue()));
                engine.put(en.getKey(), new_map);
            }
        }
    }

    public String wordStemmer(String word) {
        ptStm.setCurrent(word);
        ptStm.stem();
        return ptStm.getCurrent();
    }

    public String checkForStem(String word) {
        return doStem ? wordStemmer(word) : word;
    }

    private Map<String, Long> manipulateFile(String fileText) {
        Map<String, Long> tokenizedWords = tokenizer.tokenize(fileText);
        Map<String, Long> result = new HashMap<>();
        String key;
        for (Map.Entry<String, Long> e : tokenizedWords.entrySet()) {
            key = normalizer.normalize(e.getKey());
            if (wordValidator.isAcceptable(key)) {
                key = checkForStem(key);
                if (result.containsKey(key)) {
                    result.replace(key, e.getValue() + result.get(key));
                } else {
                    result.put(key, e.getValue());
                }
            }
        }
        return result;
    }


    public void addFile(String title, String fileText) {
        Map<String, Long> words = manipulateFile(fileText);
        long size = words.values().stream().mapToLong(Long::longValue).sum();
        addWordToEngine(words, title, size);
    }

    public Map<String, Map<String, Score>> getEngine() {
        return engine;
    }
}
