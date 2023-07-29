package filter.tokenizer;

import lombok.RequiredArgsConstructor;
import java.util.*;

@RequiredArgsConstructor
public class NGramTokenizer implements Tokenizer {
    private final int min;
    private final int max;
    private final String regex;
    private final Map<String, Long> tokenWords = new HashMap<>();

    @Override
    public Map<String, Long> tokenize(String line) {
        tokenWords.clear();
        Map<String, Long> splitTokens = new SplitTokenizer(regex).tokenize(line);
        for (Map.Entry<String, Long> entry : splitTokens.entrySet()) {
            addToTokenWords(entry.getKey(), entry.getValue());
            for (int i = min; i <= max; i++) {
                n_Grams(i, entry.getKey(), entry.getValue());
            }
        }
        return tokenWords;
    }

    private void n_Grams(int n, String token, Long occurrence) {
        String str;
        if (token.length() > n) {
            for (int i = 0; i < token.length() - n + 1; i++) {
                str = token.substring(i, i + n);
                addToTokenWords(str, occurrence);
            }
        }
    }

    private void addToTokenWords(String token, Long occurrence) {
        if (tokenWords.containsKey(token))
            tokenWords.replace(token, tokenWords.get(token) + occurrence);
        else
            tokenWords.put(token, occurrence);
    }
}
