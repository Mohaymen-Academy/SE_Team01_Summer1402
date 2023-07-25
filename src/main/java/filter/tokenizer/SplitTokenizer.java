package filter.tokenizer;

import java.util.*;

public class SplitTokenizer implements Tokenizer {
    private final String regex;

    public SplitTokenizer(String regex) {
        this.regex = regex;
    }

    @Override
    public List<String> tokenize(String line) {
        return new ArrayList<>(Arrays.asList(line.trim().split(regex)));
    }
}
