package filter.tokenizer;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class NGramTokenizer implements Tokenizer {
    private final int min;
    private final int max;
    private final String regex;

    @Override
    public Set<String> tokenize(String line) {
        Set<String> split_tokens = new SplitTokenizer(regex).tokenize(line);
        Set<String> tokens = new HashSet<>();
        for (int i = min; i <= max; i++)
            tokens.addAll(n_Grams(i, split_tokens));
        return tokens;
    }

    private Set<String> n_Grams(int n, Set<String> split_tokens) {
        Set<String> nLengthTokens = new HashSet<>();
        for (String token : split_tokens) {
            if (token.length() < n)
                nLengthTokens.add(token);
            for (int i = 0; i < token.length() - n + 1; i++) {
                nLengthTokens.add(token.substring(i, i + n));
            }
        }
        return nLengthTokens;
    }
}
