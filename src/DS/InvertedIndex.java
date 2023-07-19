package DS;

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
    private final WordValidator wordValidator;

    public InvertedIndex(Tokenizer tokenizer, Normalizer normalizer) {
        this.engine = new HashMap<>();
        this.tokenizer = tokenizer;
        this.normalizer = normalizer;
        wordValidator = new WordValidator();
    }

    private void add_word_to_engine(String root, String fileName) {
        if (!engine.containsKey(root)) {
            engine.put(root, new HashSet<>());
        }
        engine.get(root).add(fileName);
    }

    private String get_word_root(String word) {
        return new Stemmer().getWordRoot(word);
    }

    private Set<String> manipulateFile(String fileText) {
        Set<String> tokenizedWords = new HashSet<>(tokenizer.tokenize(fileText));
        Set<String> normalizedWords = normalizer.normalize(tokenizedWords);
        Set<String> finalSetOfWords = new HashSet<>();
        for (String word : normalizedWords) {
            if (wordValidator.isAcceptable(word)) {
                finalSetOfWords.add(get_word_root(word));
            }
        }
        return finalSetOfWords;
    }


    protected void addFile(String title, StringBuilder fileText) {
        for (String word : manipulateFile(fileText.toString())) {
            add_word_to_engine(word, title);
        }
    }

    public Map<String, Set<String>> getEngine() {
        return engine;
    }
}
