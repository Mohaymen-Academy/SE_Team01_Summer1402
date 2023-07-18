package DS;

import filter.WordValidator;
import filter.normalizer.NormalizerI;
import filter.stemmer.Stemmer;
import filter.tokenizer.TokenizerI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InvertedIndex1 {
    private final HashMap<String, HashSet<String>> engine;
    private final NormalizerI normalizer;
    private final TokenizerI tokenizer;
    private final WordValidator wordValidator;

    public InvertedIndex1(TokenizerI tokenizer, NormalizerI normalizer) {
        this.engine = new HashMap<>();
        this.tokenizer = tokenizer;
        this.normalizer = normalizer;
        wordValidator = new WordValidator();
    }

    public void add_word_to_engine(String root, String fileName) {
        if (!engine.containsKey(root)) {
            engine.put(root, new HashSet<>());
        }
        engine.get(root).add(fileName);
    }

    private String getRoot(String word) {
        return new Stemmer().getWordRoot(word);
    }

    private Set<String> manipulateFile(String fileText) {
        Set<String> tokenizedWords = new HashSet<>(tokenizer.tokenize(fileText));
        Set<String> normalizedWords = normalizer.normalize(tokenizedWords);
        Set<String> finalSetOfWords = new HashSet<>();
        for (String word : normalizedWords) {
            if (wordValidator.isAcceptable(word)) {
                finalSetOfWords.add(getRoot(word));
            }
        }
        return finalSetOfWords;
    }

//    public HashSet<String> getFiles(String root) {
//        if (!engine.containsKey(root)) return new HashSet<>();
//        return engine.get(root);
//    }

    public void addFile(String title, StringBuilder fileText) {
        for (String word : manipulateFile(fileText.toString())) {
            add_word_to_engine(word, title);
        }
    }


}
