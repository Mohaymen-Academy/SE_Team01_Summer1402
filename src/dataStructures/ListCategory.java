package dataStructures;

import java.util.HashSet;
import java.util.Set;

public class ListCategory {

    private final Set<String> allFiles;
    private final Set<String> essentialFiles;
    private final Set<String> optionalFiles;
    private final Set<String> forbiddenFiles;
    private boolean isFirstTime;
    private boolean hasEssentialWords;
    private boolean hasOptionalWords;

    public ListCategory(Set<String> files) {
        essentialFiles = new HashSet<>();
        optionalFiles = new HashSet<>();
        forbiddenFiles = new HashSet<>();
        isFirstTime = true;
        allFiles = files;
    }

    public void addToEssentialFile(Set<String> files) {
        hasEssentialWords = true;
        if (isFirstTime) {
            essentialFiles.addAll(files);
            isFirstTime = false;
        }
        else essentialFiles.retainAll(files);
    }

    public void addToOptionalFile(Set<String> files) {
        hasOptionalWords = true;
        optionalFiles.addAll(files);
    }

    public void addToForbiddenFile(Set<String> files) {
        forbiddenFiles.addAll(files);
    }
    public Set<String> intersectFiles() {
        Set<String> finalFiles = hasEssentialWords ? essentialFiles : optionalFiles;
        if (hasOptionalWords)
            finalFiles.retainAll(optionalFiles);
        finalFiles.removeAll(forbiddenFiles);
        if (!hasEssentialWords && !hasOptionalWords) {
            finalFiles = allFiles;
            finalFiles.removeAll(forbiddenFiles);
        }
        return finalFiles;
    }
}
