package filter.tokenizer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SplitTokenizer implements Tokenizer {
    private final String regex;

    public SplitTokenizer(String regex) {
        this.regex = regex;
    }

    @Override
    public Map<String, Long> tokenize(String line) {
        return Arrays.stream(line.trim().split(regex))
                .collect( Collectors.groupingBy( Function.identity(), Collectors.counting() ));
    }
}
