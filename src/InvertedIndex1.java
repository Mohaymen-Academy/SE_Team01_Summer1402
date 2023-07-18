import java.util.HashMap;
import java.util.HashSet;

public class InvertedIndex1 {
    private final HashMap<String, HashSet<String>> engine;

    public InvertedIndex1() {
        this.engine = new HashMap<>();
    }

    public void add(String root, String fileName) {
        if (!engine.containsKey(root)) {
            engine.put(root, new HashSet<>());
        }
        engine.get(root).add(fileName);
    }

    public HashSet<String> getFiles(String root) {
        if (!engine.containsKey(root)) return new HashSet<>();
        return engine.get(root);
    }
}
