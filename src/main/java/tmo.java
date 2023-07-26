import filter.tokenizer.NGramTokenizer;
import filter.tokenizer.SplitTokenizer;
import filter.tokenizer.Tokenizer;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class tmo {
    public static void main(String[] args) throws IOException {
        Tokenizer tokenizer = new NGramTokenizer(3, 5,"[\\s\\n\\-]+");
        Map<String,Long>result=tokenizer.tokenize
                ("sal203\n\n bc -skpqk-l23-wk wks sal203 ---  xs=dog/hotdog/hottdoggg");
        for (Map.Entry<String, Long> en : result.entrySet()) {
            System.out.println(en.getKey() + " " + en.getValue());
        }
    }
}
