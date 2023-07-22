import filter.WordValidator;
import filter.normalizer.LoweCaseNormalizer;
import reader.DocReader;
import reader.IReader;
import searchMode.Search;
import filter.tokenizer.SplitTokenizer;
import searchMode.advancedSearch.AdvancedSearch;

import java.io.*;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager
                (new SplitTokenizer(), new LoweCaseNormalizer());
        fileManager.setValidator(new WordValidator(true));
        fileManager.setDoStem(true);
        fileManager.createDatabase(new DocReader(path));
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