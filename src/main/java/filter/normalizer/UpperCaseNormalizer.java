package filter.normalizer;

public class UpperCaseNormalizer implements Normalizer {
    @Override
    public String normalize(String word) {
        return word.toUpperCase();
    }
}
