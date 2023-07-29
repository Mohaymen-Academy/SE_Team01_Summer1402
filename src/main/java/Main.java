import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.normalizer.UpperCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import reader.Reader;
import reader.TXTReader;
import search.Search;
import search.advancedSearch.AdvancedSearch;
import sort.IDFSorter;
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
                tokenizer(new SplitTokenizer("[^\\da-zA-Z]+")).
                normalizer(new UpperCaseNormalizer()).
                doStem(false).
                wordValidator(new WordValidator(true)).
                build();
        Reader reader = new TXTReader();
        Map<String, String> contexts = reader.getMapDocuments(path);
        for (String title : contexts.keySet())
            invertedIndex.addNewContext(title, contexts.get(title));
        String query = getQuery();
        Search search = new AdvancedSearch(invertedIndex, query);
        search.getAllDocuments();
        search.setSorter(new IDFSorter(invertedIndex));
        printDocuments(search.sortResult());
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
            System.out.println((index++) + ") " + entry.getKey() + "\n the score is: " + entry.getValue());
    }

}