package reader;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public Map<String, String> getMapDocuments() {
//        Arrays.stream(getFiles()).filter(file -> fileIsValid(file.getName())).map(
//                        file -> {
//                            StringBuilder fileWords = getFileLines(file);
//                            return (fileWords != null) ? new AbstractMap.SimpleEntry<>(file.getName(), fileWords) : null;
//                        }
//                ).filter(Objects::nonNull)
//                .forEach(x -> filesTexts.put(x.getKey(), x.getValue()));
        Map<String, String> documents = new HashMap<>();
        try {
            File sourceFolder = new File(filesPath);
            for (File sourceFile : sourceFolder.listFiles()) {
                String name = sourceFile.getName();
                //System.out.println(name);
                documents.put(name, Files.readString(Path.of(filesPath, name)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return documents;
    }

}
