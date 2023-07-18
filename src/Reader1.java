import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reader1 {

    private final String filesPath;

    public Reader1(String filesPath) {
        this.filesPath = filesPath;
    }

    public File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    public ArrayList<String> getFileWords(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        ArrayList<String> words = new ArrayList<>();
        while ((str = br.readLine()) != null)
            words.addAll(Arrays.asList(str.split("\\P{Alpha}+")));
        return words;
    }

}
