package filter.tokenizer;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class NGramTokenizer implements Tokenizer {
    private final int min;
    private final int max;
    private final String regex;

    @Override
    public List<String> tokenize(String line) {
        List<String> split_tokens = new SplitTokenizer(regex).tokenize(line);
        List<String> tokens = new ArrayList<>();
        for (int i = min; i <= max; i++)
            tokens.addAll(n_Grams(i, split_tokens));
        return tokens;
    }

    private List<String> n_Grams(int n, List<String> split_tokens) {
        List<String> nLengthTokens = new ArrayList<>();
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
