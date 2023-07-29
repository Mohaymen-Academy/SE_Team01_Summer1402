package reader;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Reader {

    protected final Map<String, StringBuilder> filesTexts;

    public Reader() {
        filesTexts = new HashMap<>();
    }

    public abstract Map<String, String> getMapDocuments(String path);

}
