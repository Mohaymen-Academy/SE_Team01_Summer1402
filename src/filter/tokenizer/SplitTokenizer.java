package filter.tokenizer;

import java.util.Arrays;
import java.util.List;

public class SplitTokenizer implements TokenizerI{
    private final String regex;

    public SplitTokenizer(String regex) {
        this.regex = regex;
    }

    @Override
    public List<String> tokenize(String line) {
        return Arrays.asList(line.split(regex));
    }
}
