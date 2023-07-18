import DS.FileManager;
import filter.normalizer.UpperCaseNormalizer;
import searchMode.Search;
import filter.tokenizer.SplitTokenizer;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import java.util.regex.
public class Main {
    static FileManager database;

    public static void main(String[] args) throws IOException {
        /*
        create a reader file and fill the database
         */
//        TokenizerI filter.tokenizer = new SplitTokenizer(" ");
//        NormalizerI filter.normalizer = new UpperCaseNormalizer();
        database = new FileManager(new SplitTokenizer(" "), new UpperCaseNormalizer());
        database.createDatabase();
        getQuery();



        Reader reader = new Reader();
        reader.fillDatabase();
        Scanner sc = new Scanner(System.in);
        System.out.print("choose the model 1,2:");
        String model = sc.next();
        sc.nextLine();
        String input = sc.nextLine();
        String[] all_words;
        switch (model) {
            case "1":
                all_words = input.split("\\P{Alpha}+");
                InvertedIndex.getDocuments(all_words);
                break;
            case "2":
                List<String> ws = new ArrayList<>();
                String pattern = "(\\-|\\+)?[a-zA-Z]+";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(input);
                while (m.find()) {
                    ws.add(m.group());
                    ;
                }
                InvertedIndex.print_spec_docs(ws);
                break;
        }
    }

    private static void getQuery() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose:\n1) Normal search\n2) Advanced search");
        int searchMode = Integer.parseInt(sc.nextLine());
        String query = sc.nextLine();
        Search search = null;
        switch (searchMode) {
            case 1:

                break;
            case 2:

                break;
        }
        if (search != null)
            search.tokenizer();
    }

}