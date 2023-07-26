import dataStructures.InvertedIndex;
import dataStructures.Score;
import filter.WordValidator;
import filter.normalizer.LowerCaseNormalizer;
import filter.tokenizer.NGramTokenizer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class tmo {
    public static void main(String[] args) throws IOException {
        InvertedIndex invertedIndex = InvertedIndex.builder().
                tokenizer(new SplitTokenizer("[\\s]+")).
                normalizer(new LowerCaseNormalizer()).
                doStem(false).
                wordValidator(new WordValidator(true)).
                build();
        invertedIndex.addNewContext("context1", "abc pattern uid salam pattern abc pattern");
        invertedIndex.addNewContext("context2","abc uid uid bye abc uid uid abc uid");
        Map<String,Map<String, Score>>xx=invertedIndex.getIndexMap();
        for(Map.Entry<String,Map<String,Score>>e:xx.entrySet()){
            System.out.println(e.getKey());
            System.out.println(e.getValue());
        }
    }
}
