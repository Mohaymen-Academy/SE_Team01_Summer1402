package reader;

import java.io.*;
import java.util.*;

public class DocReader {

    private final String filesPath;
    private final Map<String, StringBuilder> files_texts;

    public DocReader(String filesPath) {
        this.filesPath = filesPath;
        files_texts = new HashMap<>();
    }

    public Map<String, StringBuilder> gettext() {
        return  files_texts;
    }

    private File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    private StringBuilder getFileLines(File file) {
        BufferedReader bufferedReader ;
        StringBuilder text = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            text = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                text.append("\n").append(line);
        } catch (FileNotFoundException e) {
            System.out.println("file not found.");
        } catch (IOException e) {
            System.out.println("invalid file");
        }
        return text;
    }

    public Map<String, StringBuilder> GetMapDocuments() {
        for (File file : getFiles()) {
            StringBuilder fileWords = getFileLines(file);
            files_texts.put(file.getName(), fileWords);
        }
        return gettext();
    }

}
