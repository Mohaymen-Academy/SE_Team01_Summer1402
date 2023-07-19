package DS;

import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import reader.DocReader;
import filter.tokenizer.Tokenizer;

import java.io.IOException;
import java.util.Map;

public class FileManager {

    private final static String filesPath = "./textFiles";
    private final InvertedIndex invertedIndex;

    //if there was no specific tokenizer then we use the default splitTokenizer
    //if there was no specific normalizer we use the lowercase normalizer as a default normalizer
    public FileManager() {
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), new UpperCaseNormalizer());
    }

    public FileManager(Tokenizer tokenizer) {
        this.invertedIndex = new InvertedIndex(tokenizer, new UpperCaseNormalizer());

    }

    public FileManager(Normalizer normalizer) {
        this.invertedIndex = new InvertedIndex(new SplitTokenizer(), normalizer);

    }

    public FileManager(Tokenizer tokenizer, Normalizer normalizer) {
        this.invertedIndex = new InvertedIndex(tokenizer, normalizer);

    }

    public InvertedIndex getInvertedIndex() {
        return invertedIndex;
    }


    public void createDatabase() throws IOException {
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
