import filter.tokenizer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TokenizerTest {
    @Test
    public void tokenize_splitter1(){
        Tokenizer tokenizer = new SplitTokenizer("[\\s\\n\\-]+");
        Map<String, Long> result = tokenizer.tokenize("sal203\n\n bc -sk-l23-wk sal203 ---  xs=  $");
        Map<String,Long> expected= new HashMap<>();
        expected.put("bc",1L);
        expected.put("sal203",2L);
        expected.put("xs=",1L);
        expected.put("$",1L);
        expected.put("wk",1L);
        expected.put("sk",1L);
        expected.put("l23",1L);
        Assertions.assertEquals(expected,result);
    }
    @Test
    public void tokenize_splitter2(){
        Tokenizer tokenizer = new SplitTokenizer("[\\s\\n\\-]+");
        Map<String, Long> result = tokenizer.tokenize("sal203\n\n bc -sk-l23-wk sal203 ---  xs=  $");
        Map<String,Long> expected= new HashMap<>();
        expected.put("bc",1L);
        expected.put("sal203",2L);
        expected.put("xs=",1L);
        expected.put("$",1L);
        expected.put("wk",1L);
        expected.put("sk",1L);
        expected.put("l23",1L);
        Assertions.assertEquals(expected,result);
    }

    @Test
    public void tokenize_NgramTokenizer(){
        Tokenizer tokenizer = new NGramTokenizer(3, 5,"[\\s\\n\\-]+");
        Map<String,Long>result=tokenizer.tokenize("sal203\n\n bc -skpqk-l23-wk wks sal203 ---  xs=dog/hotdog/hottdoggg");
        Map<String,Long> expected= new HashMap<>();
        expected.put("bc",3L);
        expected.put("sal2",2L);
        expected.put("xs=",1L);
        expected.put("l23",3L);
        expected.put("wk",3L);
        expected.put("skpq",1L);
        expected.put("skpqk",1L);
        expected.put("skp",1L);
        expected.put("xs=d",1L);
        expected.put("xs=do",1L);
        expected.put("wks",3L);
        expected.put("sal20",2L);
        expected.put("sal",2L);
        Assertions.assertEquals(expected, result);
    }
}
