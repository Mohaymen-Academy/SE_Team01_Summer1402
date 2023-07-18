import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DocReader {

    private final String filesPath;
    private final int model;
    private final HashMap<String, ArrayList<String>> words;
    private String regex;


    //todo: call createMapOfWords in constructor?
    public DocReader(String filesPath, int model) {
        this.filesPath = filesPath;
        this.model = model;
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

    //todo: does it need to have a class to store string terms?
    private void setRegex() {
        switch (model) {
            case 1:
                regex = "\\P{Alpha}+";
                break;
            case 2:
                regex = "(\\-|\\+)?[a-zA-Z]+";
        }
    }

    public HashMap<String, ArrayList<String>> createMapOfWords() throws IOException {
        setRegex();
        for (File file : getFiles())
            words.put(file.getName(), getFileWords(file));
        return getWords();
    }

}
