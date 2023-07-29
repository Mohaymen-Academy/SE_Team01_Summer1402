import filter.tokenizer.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class TokenizerTest {
    @Test
    public void tokenize_splitter_Punctuation_Test() {
        Tokenizer tokenizer = new SplitTokenizer("[\\s\\n\\-]+");
        Map<String, Long> result = tokenizer.tokenize("sal203\n\n bc -sk-l23-wk sal203 ---  xs=  $");
        Map<String, Long> expected = new HashMap<>();
        expected.put("bc", 1L);
        expected.put("sal203", 2L);
        expected.put("xs=", 1L);
        expected.put("$", 1L);
        expected.put("wk", 1L);
        expected.put("sk", 1L);
        expected.put("l23", 1L);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void tokenize_splitter_NonAlphabetic_Numeric_Regex_Test() {
        Tokenizer tokenizer = new SplitTokenizer("[^a-zA-Z\\d]+");
        Map<String, Long> result = tokenizer.tokenize("sal203\n\n bc -sk-l23-wk sal203 ---  xs=  $");
        Map<String, Long> expected = new HashMap<>();
        expected.put("bc", 1L);
        expected.put("sal203", 2L);
        expected.put("xs", 1L);
        expected.put("wk", 1L);
        expected.put("sk", 1L);
        expected.put("l23", 1L);
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void tokenize_NgramTest() {
        Tokenizer tokenizer = new NGramTokenizer(3, 5, "[^\\da-zA-Z]+");
        Map<String, Long> actual = tokenizer.tokenize("dog/hotdog/dogg");
        Map<String, Long> expected = new HashMap<>();
        expected.put("dogg",1L);
        expected.put("otdo",1L);
        expected.put("hotdog",1L);
        expected.put("hotdo",1L);
        expected.put("otdog",1L);
        expected.put("hotd",1L);
        expected.put("hot",1L);
        expected.put("dog",3L);
        expected.put("ogg",1L);
        expected.put("otd",1L);
        expected.put("tdo",1L);
        expected.put("tdog",1L);
        Assertions.assertEquals(expected, actual);
    }
}
