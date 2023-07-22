package filter.normalizer;

public class LoweCaseNormalizer implements Normalizer {
    @Override
    public String normalize(String word) {
        return word.toLowerCase();
    }
}
