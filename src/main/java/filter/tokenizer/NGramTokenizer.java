package filter.tokenizer;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class NGramTokenizer implements Tokenizer {
    private final int min;
    private final int max;
    private final String regex;

    private Map<String, Long> tokenWords = new HashMap<>();


    @Override
    public Map<String, Long> tokenize(String line) {
        tokenWords.clear();
        Map<String, Long> split_tokens = new SplitTokenizer(regex).tokenize(line);
        for (int i = min; i <= max; i++) {
            for (Map.Entry<String, Long> entry : split_tokens.entrySet()) {
                n_Grams(i, entry.getKey(), entry.getValue());
            }
        }

        return tokenWords;
    }

    private void n_Grams(int n, String token, Long occurrence) {
        if (token.length() >= n) {
            for (int i = 0; i < token.length() - n + 1; i++) {
                token = token.substring(i, i + n);
                if (tokenWords.containsKey(token)) {
                    tokenWords.replace(token, tokenWords.get(token) + occurrence);
                } else
                    tokenWords.put(token, occurrence);
            }
        } else {
            if (tokenWords.containsKey(token)) {
                tokenWords.replace(token, tokenWords.get(token) + occurrence);
            } else
                tokenWords.put(token, occurrence);
        }
    }
}
