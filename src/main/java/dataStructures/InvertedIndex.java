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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class InvertedIndex {

    private final Map<String, Map<String, Integer>> engine = new HashMap<>();
    @Builder.Default
    private Normalizer normalizer = new UpperCaseNormalizer();
    @Builder.Default
    private Tokenizer tokenizer = new SplitTokenizer("[^\\da-zA-Z]+");
    @Builder.Default
    private WordValidator wordValidator = new WordValidator(true);
    @Builder.Default
    private boolean doStem = true;

    public InvertedIndex(Normalizer normalizer, Tokenizer tokenizer, WordValidator wordValidator, boolean doStem) {
        this.normalizer = normalizer;
        this.tokenizer = tokenizer;
        this.wordValidator = wordValidator;
        this.doStem = doStem;
    }

    private void addWordToEngine(String root, String fileName) {
        if (!engine.containsKey(root)) {
            engine.put(root, new HashMap<>());
        }
        Map<String, Integer> map = engine.remove(root);
       // if (map == null) map = new HashMap<>();
        if (map.containsKey(fileName)) map.replace(fileName, map.get(fileName) + 1);
        else map.put(fileName, 1);
        engine.put(root, map);
    }

    public String wordStemmer(String word) {
        PorterStemmer ptStm = new PorterStemmer();
        ptStm.setCurrent(word);
        ptStm.stem();
        return ptStm.getCurrent();
    }

    public String checkForStem(String word) {
        return doStem ? wordStemmer(word) : word;
    }

    private Set<String> manipulateFile(String fileText) {
        Set<String> tokenizedWords = new HashSet<>(tokenizer.tokenize(fileText));
        return tokenizedWords.stream().
                map(normalizer::normalize).
                filter(wordValidator::isAcceptable).
                map(this::checkForStem).
                collect(Collectors.toSet());
    }


    public void addFile(String title, String fileText) {
        manipulateFile(fileText).forEach((word) -> addWordToEngine(word, title));
    }

    public Map<String, Map<String, Integer>> getEngine() {
        return engine;
    }
}
