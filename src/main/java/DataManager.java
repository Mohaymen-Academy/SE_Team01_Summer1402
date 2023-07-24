import dataStructures.InvertedIndex;
import lombok.RequiredArgsConstructor;
import reader.Reader;

import java.util.Map;

@RequiredArgsConstructor
public class DataManager {
    private final InvertedIndex invertedIndex;

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
