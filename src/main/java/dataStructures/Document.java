package dataStructures;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class Document {

    private final String documentName;
    private final Map<String, Integer> wordNumber;
    private Integer allWordsNumber;

    public Document(String documentName) {
        this.documentName = documentName;
        wordNumber = new HashMap<>();
    }

    public void addWord(String word) {
        if (wordNumber.containsKey(word))
            wordNumber.replace(word, wordNumber.get(word) + 1);
        else wordNumber.put(word, 1);
    }

    public int getWordPriority(String word) {
        return wordNumber.getOrDefault(word, 0) / allWordsNumber;
    }
}
