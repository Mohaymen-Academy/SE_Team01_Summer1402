import dataStructures.InvertedIndex;
import dataStructures.Score;
import filter.WordValidator;
import filter.normalizer.LowerCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InvertedIndexTest {
    InvertedIndex invertedIndex;
    Map<String, Map<String, Score>> expected = new HashMap<>();

    @BeforeEach
    public void set() {
        invertedIndex = InvertedIndex.builder().
                tokenizer(new SplitTokenizer("[\\s]+")).
                normalizer(new LowerCaseNormalizer()).
                doStem(false).
                wordValidator(new WordValidator(true)).
                build();

    }

    @BeforeEach
    public void setContext() {
        Map<String, Score> abcMap = new HashMap<>();
        abcMap.put("context1", new Score(7, 2));
        abcMap.put("context2", new Score(9, 3));
        expected.put("abc", abcMap);
        Map<String, Score> salamMap = new HashMap<>();
        salamMap.put("context1", new Score(7, 1));
        expected.put("salam", salamMap);
        Map<String, Score> patternMap = new HashMap<>();
        patternMap.put("context1", new Score(7, 3));
        expected.put("pattern", patternMap);
        Map<String, Score> uidMap = new HashMap<>();
        uidMap.put("context1", new Score(7, 1));
        uidMap.put("context2", new Score(9, 5));
        expected.put("uid", uidMap);
        Map<String, Score> byeMap = new HashMap<>();
        byeMap.put("context2", new Score(9, 1));
        expected.put("bye", byeMap);
    }

    @Test
    public void addNewContext_addWordToEngine() {
        invertedIndex.addNewContext("context1", "abc pattern uid salam pattern abc pattern");
        invertedIndex.addNewContext("context2", "abc uid uid bye abc uid uid abc uid");
        Assertions.assertEquals(expected, invertedIndex.getIndexMap());
    }
}
