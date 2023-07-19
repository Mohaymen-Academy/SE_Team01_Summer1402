package filter.normalizer;

import java.util.Set;

public interface Normalizer {

    Set<String> normalize(Set<String> words);

}
