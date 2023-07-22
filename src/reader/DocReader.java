package reader;

import java.io.*;
import java.util.*;

public class DocReader extends IReader {

    private final String filesPath;

    public DocReader(String filesPath) {
        super();
        this.filesPath = filesPath;
    }

    private File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    private StringBuilder getFileLines(File file) {
        BufferedReader bufferedReader;
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

    @Override
    public Map<String, StringBuilder> GetMapDocuments() {
        for (File file : getFiles()) {
            StringBuilder fileWords = getFileLines(file);
            if (fileWords != null)
                files_texts.put(file.getName(), fileWords);
        }
        return getFilesText();
    }

}
