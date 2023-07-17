import java.io.*;
import java.util.HashSet;

public class Reader {
    private static final String path = "D:\\mohaymen\\HW\\project1\\textFiles";

    public void fillDatabase() {
        File directoryPath = new File(path);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        for (File file : filesList) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String st;
                while ((st = br.readLine()) != null) {
                    String words[] = st.split("\\P{Alpha}+");
                    for (String a : words) {
                        InvertedIndex.addToDb(file.getName(), a);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
