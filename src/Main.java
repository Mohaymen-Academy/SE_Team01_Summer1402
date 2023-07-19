import DS.FileManager;
import filter.WordValidator;
import filter.normalizer.UpperCaseNormalizer;
import searchMode.NormalSearch;
import searchMode.Search;
import filter.tokenizer.SplitTokenizer;

import java.io.*;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager
                (new SplitTokenizer(), new UpperCaseNormalizer(), path);
        fileManager.setValidator(new WordValidator(true));
        fileManager.setDoStem(true);
        fileManager.createDatabase();
        String query = getQuery();
        Search search = new NormalSearch(fileManager.getInvertedIndex(), query);
        Set<String> result = search.geAllDocuments();
        for (String title : result)
            System.out.println(title);
    }

    private static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter query: ");
        return scanner.nextLine();
    }

}