import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import reader.DocReader;
import filter.tokenizer.Tokenizer;

import java.util.Map;

public class FileManager {

    private final String filesPath;
    private final InvertedIndex invertedIndex;
    private final Normalizer normalizer;

    //if there was no specific tokenizer then we use the default splitTokenizer
    //if there was no specific normalizer we use the lowercase normalizer as a default normalizer
    public FileManager(String filesPath) {
        this.normalizer = new UpperCaseNormalizer();
        this.filesPath = filesPath;
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), normalizer);
        setDefaultFilters();
    }

    public FileManager(Tokenizer tokenizer, String filesPath) {
        this.normalizer = new UpperCaseNormalizer();
        this.invertedIndex = new InvertedIndex(tokenizer, normalizer);
        this.filesPath = filesPath;
        setDefaultFilters();
    }

    public FileManager(Normalizer normalizer, String filesPath) {
        this.normalizer = normalizer;
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), normalizer);
        this.filesPath = filesPath;
        setDefaultFilters();
    }

    public FileManager(Tokenizer tokenizer, Normalizer normalizer, String filesPath) {
        this.normalizer = normalizer;
        this.invertedIndex = new InvertedIndex(tokenizer, normalizer);
        this.filesPath = filesPath;
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

    public void createDatabase() {
        DocReader reader = new DocReader(filesPath);
        Map<String, StringBuilder> file_texts = reader.GetMapDocuments();
        fillInvertedIndex(file_texts);
    }

    private void fillInvertedIndex(Map<String, StringBuilder> fileText) {
        for (String title : fileText.keySet()) {
            invertedIndex.addFile(title, fileText.get(title));
        }
    }
}
