package reader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class TXTReader extends Reader {

    private String filesPath;

    private File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    private boolean isFileValid(String fileName) {
        String[] splitName = fileName.split("\\.");
        return splitName.length > 0 &&
                splitName[splitName.length - 1].equals("txt");
    }

    @Override
    public Map<String, String> getMapDocuments(String path) {
        this.filesPath = path;
        Map<String, String> documents = new HashMap<>();
        try {
            String title, context;
            for (File sourceFile : getFiles()) {
                title = sourceFile.getName();
                if (isFileValid(title)) {
                    title = sourceFile.getName();
                    context = Files.readString(Path.of(filesPath, title));
                    documents.put(title, context);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return documents;
    }
}
