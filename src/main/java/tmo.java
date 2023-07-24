import dataStructures.InvertedIndex;
import filter.tokenizer.NGramTokenizer;
import org.tartarus.snowball.ext.PorterStemmer;
import searchMode.Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class tmo {
    public static void main(String[] args) throws IOException {
        File file=new File("./textFiles/Clean Agile.txt");
        BufferedReader bf=new BufferedReader(new FileReader(file));
        String line;
        PorterStemmer stm=new PorterStemmer();
        stm.setCurrent("ert");
        stm.stem();
        System.out.println(stm.getCurrent());
        NGramTokenizer tokenizer=new NGramTokenizer(3,5,"[^\\da-zA-Z]+");
        Set<String> tt=new HashSet<>();
        while ((line=bf.readLine())!=null){
            System.out.println(line);
            tt=tokenizer.tokenize(line);

           System.out.println(tt);
        }
    }
}
