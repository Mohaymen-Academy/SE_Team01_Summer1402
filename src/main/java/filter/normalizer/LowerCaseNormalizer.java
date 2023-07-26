package filter.normalizer;

public class LowerCaseNormalizer implements Normalizer {
    @Override
    public String normalize(String word) {
        return word.toLowerCase();
    }
}
