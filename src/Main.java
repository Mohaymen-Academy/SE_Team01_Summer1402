import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import java.util.regex.
public class Main {

    public static void main(String[] args) {
        /*
        create a reader file and fill the database
         */
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
                List<String>ws=new ArrayList<>();
                String pattern="(\\-|\\+)?[a-zA-Z]+";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(input);
                while (m.find()) {
                    ws.add(m.group());;
                }
                InvertedIndex.print_spec_docs(ws);
                break;
        }
    }
}