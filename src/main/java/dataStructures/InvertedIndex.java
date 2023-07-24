package dataStructures;

import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.stemmer.Stemmer;
import filter.tokenizer.Tokenizer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvertedIndex {
    private final Map<String, Set<String>> engine;
    private final Normalizer normalizer;
    private final Tokenizer tokenizer;
    private WordValidator wordValidator;
    private boolean doStem;

    public InvertedIndex(Tokenizer tokenizer, Normalizer normalizer) {
        this.engine = new HashMap<>();
        this.tokenizer = tokenizer;
        this.normalizer = normalizer;
    }

    public void setDoStem(boolean doStem) {
        this.doStem = doStem;
    }

    public void setWordValidator(WordValidator validator) {
        this.wordValidator = validator;
    }

    public WordValidator getWordValidator() {
        return wordValidator;
    }

    public Normalizer getNormalizer() {
        return normalizer;
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
        Set<String> finalSetOfWords = new HashSet<>();
        for (String word : tokenizedWords) {
            word = normalizer.normalize(word);
            if (wordValidator.isAcceptable(word))
                finalSetOfWords.add(getWordRoot(word));
        }
        return finalSetOfWords;
    }


    public void addFile(String title, StringBuilder fileText) {
        for (String word : manipulateFile(fileText.toString())) {
            addWordToEngine(word, title);
        }
    }

    public Map<String, Set<String>> getEngine() {
        return engine;
    }
}
