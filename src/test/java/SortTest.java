import dataStructures.InvertedIndex;
import dataStructures.Score;
import filter.WordValidator;
import filter.normalizer.LowerCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import search.Sorter;
import java.util.*;
import static org.mockito.ArgumentMatchers.anyString;

public class SortTest {
    InvertedIndex invertedIndex;
    Map<String, Map<String, Score>> indexmap = new HashMap<>();


    @BeforeEach
    public void setup_expectedMap() {
        Map<String, Score> abcMap = new HashMap<>();
        abcMap.put("context1", new Score(7, 2));
        abcMap.put("context2", new Score(9, 3));
        indexmap.put("abc", abcMap);
        Map<String, Score> salamMap = new HashMap<>();
        salamMap.put("context1", new Score(7, 1));
        indexmap.put("salam", salamMap);
        Map<String, Score> patternMap = new HashMap<>();
        patternMap.put("context1", new Score(7, 3));
        indexmap.put("pattern", patternMap);
        Map<String, Score> uidMap = new HashMap<>();
        uidMap.put("context1", new Score(7, 1));
        uidMap.put("context2", new Score(9, 5));
        indexmap.put("uid", uidMap);
        Map<String, Score> byeMap = new HashMap<>();
        byeMap.put("context2", new Score(9, 1));
        indexmap.put("bye", byeMap);
    }

    @BeforeEach
    void setup_invertIndex() {
        invertedIndex = Mockito.mock(InvertedIndex.class);
        Mockito.when(invertedIndex.getWordValidator()).thenReturn(new WordValidator(true));
        Mockito.when(invertedIndex.getNormalizer()).thenReturn(new LowerCaseNormalizer());
        Mockito.when(invertedIndex.getTokenizer()).thenReturn(new SplitTokenizer("[\\s]+"));
        Mockito.when(invertedIndex.getIndexMap()).thenReturn(indexmap);
        Mockito.when(invertedIndex.checkForStem(anyString())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
    }

    @Test
    public void sortIDF() {
        Set<String> finalFiles = new HashSet<>(Arrays.asList("context1", "context2"));
        Set<String> queryWords = new HashSet<>(Arrays.asList("salam", "abc", "bye", "pattern","uid"));
        Sorter sorter = new Sorter(queryWords, invertedIndex, finalFiles);
        List<Map.Entry<String, Double>> result=sorter.sort();
        List<String>titles=new ArrayList<>();
        List<String>expected=new ArrayList<>(Arrays.asList("context2","context1"));
        for (Map.Entry<String, Double> entry : result) {
            titles.add(entry.getKey());
        }
        Assertions.assertEquals(titles,expected);

    }
}
