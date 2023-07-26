import dataStructures.InvertedIndex;
import lombok.RequiredArgsConstructor;
import reader.Reader;
import java.util.Map;

@RequiredArgsConstructor
public class DataManager {

    private final InvertedIndex invertedIndex;

    public void createDatabase(Reader reader) {
        Map<String, String> contexts = reader.getMapDocuments();
        fillInvertedIndex(contexts);
    }

    private void fillInvertedIndex(Map<String, String> contexts) {
        for (String title : contexts.keySet())
            invertedIndex.addNewContext(title, contexts.get(title));
    }
}
