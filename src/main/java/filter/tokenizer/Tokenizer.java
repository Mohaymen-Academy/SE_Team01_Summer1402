package filter.tokenizer;

import java.util.Map;

public interface Tokenizer {
    Map<String, Long> tokenize(String line);
}
