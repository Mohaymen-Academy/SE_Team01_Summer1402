package sort;

import dataStructures.InvertedIndex;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Setter
public abstract class Sorter {

    protected final InvertedIndex invertedIndex;
    protected Set<String> finalFiles;
    protected Set<String> queryWords;

    public abstract List<Map.Entry<String, Double>> sort();
}
