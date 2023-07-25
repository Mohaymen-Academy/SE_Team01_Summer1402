import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.tokenizer.NGramTokenizer;
import filter.normalizer.UpperCaseNormalizer;
import reader.TXTReader;
import searchMode.Search;
import searchMode.advancedSearch.AdvancedSearch;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";
    static long startTime;

    public static void main(String[] args) throws IOException {
        startTime = System.currentTimeMillis();
        InvertedIndex invertedIndex = InvertedIndex.builder().
                tokenizer(new NGramTokenizer(3, 5, "[^\\da-zA-Z]+")).
                normalizer(new UpperCaseNormalizer()).
                doStem(true).
                wordValidator(new WordValidator(true)).
                build();
        DataManager dataManager = new DataManager(invertedIndex);
        dataManager.createDatabase(new TXTReader(path));
        String query = getQuery();
        Search search = new AdvancedSearch(invertedIndex, query);
        search.printDocuments(search.getAllDocuments());
    }

    private static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter query: ");
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
        return scanner.nextLine();
    }

}