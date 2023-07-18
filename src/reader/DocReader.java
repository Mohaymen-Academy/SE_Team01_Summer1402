package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DocReader {

    private final String filesPath;
    private final Map<String, StringBuilder> files_texts;

    public DocReader(String filesPath) {
        this.filesPath = filesPath;
        files_texts = new HashMap<>();
    }

    public Map<String, StringBuilder> gettext() {
        return (HashMap<String, StringBuilder>) files_texts;
    }

    private File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    private StringBuilder getFileLines(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            text.append("\n").append(line);
        return text;
    }

    public Map<String, StringBuilder> createMapOfDoc() throws IOException {
        for (File file : getFiles()) {
            StringBuilder fileWords = getFileLines(file);
            files_texts.put(file.getName(), fileWords);
        }
        return gettext();
    }

}
