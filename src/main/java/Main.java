import dataStructures.InvertedIndex;
import filter.WordValidator;
import filter.tokenizer.NGramTokenizer;
import filter.normalizer.UpperCaseNormalizer;
import reader.TXTReader;
import searchMode.Search;
import searchMode.advancedSearch.AdvancedSearch;
import java.io.*;
import java.util.*;

public class Main {

    public static final String path = "./textFiles";

    public static void main(String[] args) throws IOException {
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
        return scanner.nextLine();
    }

}