import dataStructures.InvertedIndex;
import dataStructures.Score;
import filter.WordValidator;
import filter.normalizer.LowerCaseNormalizer;
import filter.tokenizer.SplitTokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import search.searchMode.NormalSearch;
import search.searchMode.Search;
import search.searchMode.advancedSearch.AdvancedSearch;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;

public class SearchTest {
    InvertedIndex invertedIndex;
    Map<String, Map<String, Score>> indexMap = new HashMap<>();
    String query;

    @BeforeEach
    public void setContext() {
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
    void setUp() {
        invertedIndex = Mockito.mock(InvertedIndex.class);
        Mockito.when(invertedIndex.getWordValidator()).thenReturn(new WordValidator(true));
        Mockito.when(invertedIndex.getNormalizer()).thenReturn(new LowerCaseNormalizer());
        Mockito.when(invertedIndex.getTokenizer()).thenReturn(new SplitTokenizer("[\\s]+"));
        Mockito.when(invertedIndex.getIndexMap()).thenReturn(indexMap);
        Mockito.when(invertedIndex.checkForStem(anyString())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);


    }

    @Test
    public void search() {
        query = "salam";
        Search search = new NormalSearch(invertedIndex, query);
        Set<String> result = search.getAllDocuments();
        Set<String> expected = new HashSet<>(List.of("context1"));
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void NormalSearch_doesNotExist() {
        query = "salammm";
        Search search = new NormalSearch(invertedIndex, query);
        Set<String> result = search.getAllDocuments();
        Set<String> expected = new HashSet<>();
        Assertions.assertEquals(result, expected);

    }

    @Test
    public void AdvancedSearch_optional() {
        query = "+salam +bye";
        Search search = new AdvancedSearch(invertedIndex, query);
        Set<String> result = search.getAllDocuments();
        Set<String> expected = new HashSet<>(Arrays.asList("context1","context2"));
        Assertions.assertEquals(result, expected);
    }


    @Test
    public void AdvancedSearch_forbidden() {
        query = "-salam";
        Search search = new AdvancedSearch(invertedIndex, query);
        Set<String> result = search.getAllDocuments();
        Set<String> expected = new HashSet<>(List.of("context2"));
        Assertions.assertEquals(result, expected);
    }

    @Test
    public void AdvancedSearch_all() {
        query = "-salam +abc bye";
        Search search = new AdvancedSearch(invertedIndex, query);
        Set<String> result = search.getAllDocuments();
        Set<String> expected = new HashSet<>(List.of("context2"));
        Assertions.assertEquals(result, expected);
    }


}
