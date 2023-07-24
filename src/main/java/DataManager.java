import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.normalizer.Normalizer;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import reader.Reader;

import java.util.Map;

@RequiredArgsConstructor
public class DataManager {
    private final InvertedIndex invertedIndex;
    //if there was no specific tokenizer then we use the default splitTokenizer
    //if there was no specific normalizer we use the lowercase normalizer as a default normalizer

    public void createDatabase(Reader reader) {
        Map<String, StringBuilder> file_texts = reader.getMapDocuments();
        fillInvertedIndex(file_texts);
    }

    private void fillInvertedIndex(Map<String, StringBuilder> fileText) {
        for (String title : fileText.keySet()) {
            invertedIndex.addFile(title, fileText.get(title));
        }
    }
}
