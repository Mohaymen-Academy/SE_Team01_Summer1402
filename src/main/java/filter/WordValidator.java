package filter;

import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class WordValidator {
    private final boolean calledForValidator;

    public WordValidator() {
        calledForValidator = false;
    }

    private final Set<String> stopWords =
            new HashSet<>(Arrays.asList("as", "of", "from", "at", "ago",
                    "before", "by", "during", "for", "the", "until", "a", "an",
                    "to", "in", "on", "over", "though", "with", "or",
                    "that", "is", "i", "you", "he", "she", "us", "and", "be",
                    "we", "it", "they", "me", "his", "her", "their", "till"));

    public boolean isAcceptable(String word) {
        if (!calledForValidator) return true;
        return !stopWords.contains(word.trim().toLowerCase()) && word.length() > 2;
    }
}
