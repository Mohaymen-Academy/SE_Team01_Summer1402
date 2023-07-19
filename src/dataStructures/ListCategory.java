package dataStructures;

import java.util.HashSet;
import java.util.Set;

public class ListCategory {

    private final Set<String> essentialFiles;
    private final Set<String> optionalFiles;
    private final Set<String> forbiddenFiles;
    private Set<String> finalFiles;
    private boolean isFirstTime;

    public ListCategory() {
        essentialFiles = new HashSet<>();
        optionalFiles = new HashSet<>();
        forbiddenFiles = new HashSet<>();
        isFirstTime = true;
    }

    public Set<String> getEssentialFiles() {
        return essentialFiles;
    }

    public Set<String> getOptionalFiles() {
        return optionalFiles;
    }

    public Set<String> getForbiddenFiles() {
        return forbiddenFiles;
    }

    public Set<String> getFinalFiles() {return finalFiles;}

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
        finalFiles = essentialFiles;
        finalFiles.retainAll(optionalFiles);
        finalFiles.removeAll(forbiddenFiles);
        return finalFiles;
    }
}
