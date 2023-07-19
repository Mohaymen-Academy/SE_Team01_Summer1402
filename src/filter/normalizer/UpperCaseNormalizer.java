package filter.normalizer;

import java.util.HashSet;
import java.util.Set;

public class UpperCaseNormalizer implements Normalizer {
    @Override
    public Set<String> normalize(Set<String> words) {
        Set<String> normalizedWords = new HashSet<>();
        for (String word : words) normalizedWords.add(word.toUpperCase());
        return normalizedWords;
    }
}
