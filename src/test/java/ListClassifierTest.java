import dataStructures.ListClassifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ListClassifierTest {
    private ListClassifier listClassifier;

    @BeforeEach
    void set() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String contextTitle = "context" + i;
            set.add(contextTitle);
        }
        listClassifier = new ListClassifier(set);
    }

    @Test
    public void addToEssentialContext_firstTime() {
        Set<String> addedList = new HashSet<>(Arrays.asList("file1", "file2", "file3"));
        listClassifier.addToEssentialContexts(addedList);
        Set<String> result = listClassifier.getEssentialContexts();
        Assertions.assertEquals(addedList, result);
    }

    @Test
    public void adToEssentialContext_whileHasBeenCalledBefore() {
        listClassifier.addToEssentialContexts(new HashSet<>(Arrays.asList("file1", "file2", "file3")));
        listClassifier.addToEssentialContexts(new HashSet<>(Arrays.asList("file1", "file3", "file5")));
        Set<String> result = listClassifier.getEssentialContexts();
        Set<String> expected = new HashSet<>(Arrays.asList("file1", "file3"));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void intersectContext_initialTest() {
        listClassifier.addToEssentialContexts(new HashSet<>(Arrays.asList("file1", "file2", "file3")));
        listClassifier.addToOptionalContexts(new HashSet<>(Arrays.asList("file1", "file3", "file5")));
        listClassifier.addToForbiddenContexts(new HashSet<>(Arrays.asList("file2", "file3", "file5")));
        Set<String> result = listClassifier.intersectContexts();
        Set<String> expected = new HashSet<>(List.of("file1"));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void intersectContext_WhileThereIsNoEssentialContext() {
        listClassifier.addToOptionalContexts(new HashSet<>(Arrays.asList("file1", "file3", "file5")));
        listClassifier.addToForbiddenContexts(new HashSet<>(Arrays.asList("file2", "file4", "file5")));
        Set<String> result = listClassifier.intersectContexts();
        Set<String> expected = new HashSet<>(Arrays.asList("file1", "file3"));
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void intersectFiles_whileThereAreJustForbiddenContexts() {
        Set<String> set = new HashSet<>(Arrays.asList("file2", "file4", "file5"));
        listClassifier.addToForbiddenContexts(set);
        Set<String> result = listClassifier.intersectContexts();
        Set<String> expected = listClassifier.getAllContexts();
        expected.retainAll(set);
        Assertions.assertEquals(expected, result);
    }
}
