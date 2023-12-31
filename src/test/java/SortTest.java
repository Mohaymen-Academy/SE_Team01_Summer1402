import dataStructures.InvertedIndex;
import dataStructures.Score;
import filter.WordValidator;
import filter.normalizer.LowerCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sort.IDFSorter;
import java.util.*;
import static org.mockito.ArgumentMatchers.anyString;

public class SortTest {
    InvertedIndex invertedIndex;
    Map<String, Map<String, Score>> indexMap = new HashMap<>();


    @BeforeEach
    public void setup_expectedMap() {
        Map<String, Score> abcMap = new HashMap<>();
        abcMap.put("context1", new Score(7, 2));
        abcMap.put("context2", new Score(9, 3));
        indexMap.put("abc", abcMap);
        Map<String, Score> salamMap = new HashMap<>();
        salamMap.put("context1", new Score(7, 1));
        indexMap.put("salam", salamMap);
        Map<String, Score> patternMap = new HashMap<>();
        patternMap.put("context1", new Score(7, 3));
        indexMap.put("pattern", patternMap);
        Map<String, Score> uidMap = new HashMap<>();
        uidMap.put("context1", new Score(7, 1));
        uidMap.put("context2", new Score(9, 5));
        indexMap.put("uid", uidMap);
        Map<String, Score> byeMap = new HashMap<>();
        byeMap.put("context2", new Score(9, 1));
        indexMap.put("bye", byeMap);
    }

    @BeforeEach
    void setupInvertIndex() {
        invertedIndex = Mockito.mock(InvertedIndex.class);
        Mockito.when(invertedIndex.getWordValidator()).thenReturn(new WordValidator(true));
        Mockito.when(invertedIndex.getNormalizer()).thenReturn(new LowerCaseNormalizer());
        Mockito.when(invertedIndex.getTokenizer()).thenReturn(new SplitTokenizer("[\\s]+"));
        Mockito.when(invertedIndex.getIndexMap()).thenReturn(indexMap);
        Mockito.when(invertedIndex.checkForStem(anyString())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }

    @Test
    public void sort_byFrequency_test() {
        Set<String> finalDocuments = new HashSet<>(Arrays.asList("context1", "context2"));
        Set<String> queryWords = new HashSet<>(Arrays.asList("salam", "abc", "bye", "pattern", "uid"));
        IDFSorter IDFSorter = new IDFSorter(invertedIndex);
        IDFSorter.setQueryWords(queryWords);
        IDFSorter.setFinalFiles(finalDocuments);
        List<Map.Entry<String, Double>> result = IDFSorter.sort();
        List<String> actual = new ArrayList<>();
        List<String> expected = new ArrayList<>(Arrays.asList("context2", "context1"));
        for (Map.Entry<String, Double> entry : result)
            actual.add(entry.getKey());
        Assertions.assertEquals(actual, expected);

    }
}
