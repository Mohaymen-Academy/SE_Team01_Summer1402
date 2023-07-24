package reader;

import java.util.HashMap;
import java.util.Map;


public abstract class Reader {

    protected final Map<String, StringBuilder> files_texts;

    public Reader() {
        files_texts = new HashMap<>();
    }

    public Map<String, StringBuilder> getFilesText() {
        return files_texts;
    }

    public abstract Map<String, StringBuilder> getMapDocuments();

}
