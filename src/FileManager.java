import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import reader.IReader;

import java.util.Map;

public class FileManager {
    private final InvertedIndex invertedIndex;
    private final Normalizer normalizer;

    //if there was no specific tokenizer then we use the default splitTokenizer
    //if there was no specific normalizer we use the lowercase normalizer as a default normalizer
    public FileManager() {
        this.normalizer = new UpperCaseNormalizer();
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), normalizer);
        setDefaultFilters();
    }

    public FileManager(Tokenizer tokenizer) {
        this.normalizer = new UpperCaseNormalizer();
        this.invertedIndex = new InvertedIndex(tokenizer, normalizer);
        setDefaultFilters();
    }

    public FileManager(Normalizer normalizer) {
        this.normalizer = normalizer;
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), normalizer);
        setDefaultFilters();
    }

    public FileManager(Tokenizer tokenizer, Normalizer normalizer) {
        this.normalizer = normalizer;
        this.invertedIndex = new InvertedIndex(tokenizer, normalizer);
        setDefaultFilters();
    }

    public void setValidator(WordValidator validator) {
        invertedIndex.setWordValidator(validator);
    }

    public InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }

    public void setDoStem(boolean doStem) {
        invertedIndex.setDoStem(doStem);
    }

    public void setDefaultFilters() {
        invertedIndex.setDoStem(true);
        invertedIndex.setWordValidator(new WordValidator());
    }

    public void createDatabase(IReader reader) {
        Map<String, StringBuilder> file_texts = reader.GetMapDocuments();
        fillInvertedIndex(file_texts);
    }

    private void fillInvertedIndex(Map<String, StringBuilder> fileText) {
        for (String title : fileText.keySet()) {
            invertedIndex.addFile(title, fileText.get(title));
        }
    }
}
