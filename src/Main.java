import filter.WordValidator;
import filter.normalizer.LoweCaseNormalizer;
import searchMode.NormalSearch;
import searchMode.Search;
import filter.tokenizer.SplitTokenizer;
import searchMode.advancedSearch.AdvancedSearch;

import java.io.*;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager
                (new SplitTokenizer(), new LoweCaseNormalizer(), path);
        fileManager.setValidator(new WordValidator(true));
        fileManager.setDoStem(true);
        fileManager.createDatabase();
        String query = getQuery();
        Search search = new AdvancedSearch(fileManager.getInvertedIndex(), query);
        search.printDocuments(search.geAllDocuments());
    }

    private static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter query: ");
        return scanner.nextLine();
    }

}