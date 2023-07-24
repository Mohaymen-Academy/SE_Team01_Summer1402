package reader;

import lombok.RequiredArgsConstructor;
import java.io.*;
import java.util.*;


@RequiredArgsConstructor
public class TXTReader extends Reader {

    private final String filesPath;

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

    private boolean fileIsValid(String fileName) {
        String[] splitName = fileName.split("\\.");
        return splitName.length > 0 &&
                splitName[splitName.length - 1].equals("txt");
    }

    @Override
    public Map<String, StringBuilder> getMapDocuments() {
        for (File file : getFiles()) {
            if (!fileIsValid(file.getName())) continue;
            StringBuilder fileWords = getFileLines(file);
            if (fileWords != null)
                filesTexts.put(file.getName(), fileWords);
        }
        return this.getFilesTexts();
    }

}
