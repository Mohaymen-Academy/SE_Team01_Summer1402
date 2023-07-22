package reader;

import java.util.HashMap;
import java.util.Map;

public abstract class IReader {

    protected final Map<String, StringBuilder> files_texts;

    public IReader() {
        files_texts = new HashMap<>();
    }

    public Map<String, StringBuilder> getFilesText() {
        return files_texts;
    }

    public abstract Map<String, StringBuilder> GetMapDocuments();

}
