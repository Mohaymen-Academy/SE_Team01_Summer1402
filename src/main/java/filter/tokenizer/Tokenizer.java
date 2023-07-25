package filter.tokenizer;

import java.util.List;
import java.util.Set;

public interface Tokenizer {
    List<String> tokenize(String line);
}
