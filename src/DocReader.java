import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DocReader {

    private final String regex = "\\P{Alpha}+"; //todo: does it need to have a class to store string terms?
    private final String filesPath;
    private final HashMap<String, ArrayList<String>> words;

    

    //todo: call createMapOfWords in constructor?
    public DocReader(String filesPath) {
        this.filesPath = filesPath;
        words = new HashMap<>();
    }

    public HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    private File[] getFiles() {
        File directoryPath = new File(filesPath);
        return directoryPath.listFiles();
    }

    private ArrayList<String> getFileWords(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> words = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null)
            words.addAll(Arrays.asList(line.split(regex)));
        return words;
    }

    public HashMap<String, ArrayList<String>> createMapOfWords() throws IOException {
        for (File file : getFiles())
            words.put(file.getName(), getFileWords(file));
        return getWords();
    }

}
