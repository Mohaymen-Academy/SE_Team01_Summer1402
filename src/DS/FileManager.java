package DS;

import DS.InvertedIndex1;
import filter.normalizer.NormalizerI;
import reader.DocReader;
import filter.tokenizer.TokenizerI;

import java.io.IOException;
import java.util.Map;

public class FileManager {
    private final InvertedIndex1 invertedIndex;

    public FileManager(TokenizerI tokenizer, NormalizerI normalizer) {
        this.invertedIndex = new InvertedIndex1(tokenizer, normalizer);

    }

    public InvertedIndex1 getInvertedIndex() {
        return invertedIndex;
    }

    private final static String filesPath = "./textFiles";

    public void createDatabase() throws IOException {
        DocReader reader = new DocReader(filesPath);
        Map<String, StringBuilder> file_texts = reader.createMapOfDoc();
        fillInvertedIndex(file_texts);
    }

    private void fillInvertedIndex(Map<String, StringBuilder> fileText) {
        for (String title : fileText.keySet()) {
            invertedIndex.addFile(title, fileText.get(title));
        }
    }
}
