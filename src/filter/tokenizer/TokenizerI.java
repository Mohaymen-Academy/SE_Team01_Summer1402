package filter.tokenizer;

import java.util.List;

public interface TokenizerI {
    List<String> tokenize(String line);
}
