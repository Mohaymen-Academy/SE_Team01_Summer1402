import DS.FileManager;
import filter.normalizer.UpperCaseNormalizer;
import searchMode.NormalSearch;
import searchMode.Search;
import filter.tokenizer.SplitTokenizer;

import java.io.*;
import java.util.*;
public class Main {

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager(new SplitTokenizer(), new UpperCaseNormalizer());
        fileManager.createDatabase();
        String sample_query="LEGACY";
        Search search=new NormalSearch(fileManager.getInvertedIndex().getEngine(),sample_query);
        Set<String>res=search.geAllDocuments();
        for(String tt:res){
            System.out.println(tt);
        }
    }

    private static void TestModel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose splitter:\n1) Non alphabetic\n2) write any character sequence");
        System.out.println("Choose Normalizer:\n1) Upper\n2) Lower");
        System.out.println("Choose:\n1) Normal search\n2) Advanced search");
        int searchMode = Integer.parseInt(sc.nextLine());
        String query = sc.nextLine();

    }

}