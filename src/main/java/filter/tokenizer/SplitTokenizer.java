package filter.tokenizer;

import java.util.*;

public class SplitTokenizer implements Tokenizer {
    private final String regex;

    public SplitTokenizer(String regex) {
        this.regex = regex;
    }

    @Override
    public Set<String> tokenize(String line) {
        return new HashSet<>(Arrays.asList(line.trim().split(regex)));
    }
}
