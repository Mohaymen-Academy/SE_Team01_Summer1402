package dataStructures;

import java.util.HashSet;
import java.util.Set;

public class ListCategory {

    private final Set<String> essentialFiles;
    private final Set<String> optionalFiles;
    private final Set<String> forbiddenFiles;
    private boolean isFirstTime;

    public ListCategory() {
        essentialFiles = new HashSet<>();
        optionalFiles = new HashSet<>();
        forbiddenFiles = new HashSet<>();
        isFirstTime = true;
    }

    public void addToEssentialFile(Set<String> files) {
        if (isFirstTime) {
            essentialFiles.addAll(files);
            isFirstTime = false;
        }
        else essentialFiles.retainAll(files);
    }

    public void addToOptionalFile(Set<String> files) {
        optionalFiles.addAll(files);
    }

    public void addToForbiddenFile(Set<String> files) {
        forbiddenFiles.addAll(files);
    }
    public Set<String> intersectFiles() {
        Set<String> finalFiles = essentialFiles;
        finalFiles.retainAll(optionalFiles);
        finalFiles.removeAll(forbiddenFiles);
        return finalFiles;
    }
}
