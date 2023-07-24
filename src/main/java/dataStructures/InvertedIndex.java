package dataStructures;

import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.stemmer.Stemmer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder @Getter @AllArgsConstructor
public class InvertedIndex {
    private final Map<String, Set<String>> engine;
    private Normalizer normalizer;
    private Tokenizer tokenizer;
    private WordValidator wordValidator;
    private boolean doStem;

    public InvertedIndex() {
        this.engine = new HashMap<>();
        setDefaultFilters();
    }

    public void setDefaultFilters() {
        tokenizer = new SplitTokenizer();
        normalizer = new UpperCaseNormalizer();
        doStem = true;
        wordValidator = new WordValidator(true);
    }
    private void addWordToEngine(String root, String fileName) {
        if (!engine.containsKey(root)) {
            engine.put(root, new HashSet<>());
        }
        engine.get(root).add(fileName);
    }

    public String getWordRoot(String word) {
        return doStem ? new Stemmer().getWordRoot(word) : word;
    }

    private Set<String> manipulateFile(String fileText) {
        Set<String> tokenizedWords = new HashSet<>(tokenizer.tokenize(fileText));
        return tokenizedWords.stream().
                map(normalizer::normalize).
                filter(wordValidator::isAcceptable).
                map(this::getWordRoot).
                collect(Collectors.toSet());
    }


    public void addFile(String title, StringBuilder fileText) {
        manipulateFile(fileText.toString()).forEach((word) -> addWordToEngine(word, title));
    }

    public Map<String, Set<String>> getEngine() {
        return engine;
    }
}
