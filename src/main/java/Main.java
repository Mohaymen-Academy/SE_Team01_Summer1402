import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.tokenizer.NGramTokenizer;
import filter.normalizer.UpperCaseNormalizer;
import reader.TXTReader;
import search.searchMode.Search;
import search.searchMode.advancedSearch.AdvancedSearch;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";
    static long startTime, endTime;

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
        printDocuments(search.sortResult(search.getAllDocuments()));
    }

    private static String getQuery() {
        Scanner scanner = new Scanner(System.in);
        endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
        System.out.println("Enter query: ");
        return scanner.nextLine();
    }

    public static void printDocuments(List<Map.Entry<String, Double>> documentTitles) {
        if (documentTitles.isEmpty()) System.out.println("There is no document.");
        int index = 1;
        for (Map.Entry<String, Double> entry : documentTitles)
            System.out.println((index++) +") "+ entry.getKey() + "\n the score is: " + entry.getValue());
    }

}