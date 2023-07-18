package filter.normalizer;

import java.util.HashSet;
import java.util.Set;

public class LoweCaseNormalizer implements NormalizerI{
    @Override
    public Set<String> normalize(Set<String> words) {
        Set<String> normalizedWords = new HashSet<>();
        for (String word : words) normalizedWords.add(word.toLowerCase());
        return normalizedWords;
    }
}
