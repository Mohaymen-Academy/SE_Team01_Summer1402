package filter.tokenizer;

import java.util.*;

public class SplitTokenizer implements Tokenizer {
    private final String regex;

    /**
     * if there was an instance of splitTokenizer class with any argument,then by default,
     * it separates the sentences in non-alphabetic order
     */
    public SplitTokenizer() {
        this.regex = "\\P{Alpha}+";
    }

    public SplitTokenizer(String regex) {
        List<String> splitMarks = new ArrayList<>(Arrays.asList(regex.split("")));
        this.regex = "[" + String.join("", splitMarks) + "]";
        // this.regex = regex;
    }

    @Override
    public Set<String> tokenize(String line) {
        return new HashSet<>(Arrays.asList(line.split(regex)));
    }
}
