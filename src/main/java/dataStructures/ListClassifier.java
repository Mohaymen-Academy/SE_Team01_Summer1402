package dataStructures;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class ListClassifier {

    private final Set<String> allContexts;
    private final Set<String> essentialContexts;
    private final Set<String> optionalContexts;
    private final Set<String> forbiddenContexts;
    private boolean isFirstTime;
    private boolean hasEssentialWords;
    private boolean hasOptionalWords;

    public ListClassifier(Set<String> files) {
        essentialContexts = new HashSet<>();
        optionalContexts = new HashSet<>();
        forbiddenContexts = new HashSet<>();
        isFirstTime = true;
        allContexts = files;
    }

    public void addToEssentialContexts(Set<String> files) {
        hasEssentialWords = true;
        if (isFirstTime) {
            essentialContexts.addAll(files);
            isFirstTime = false;
        }
        else essentialContexts.retainAll(files);
    }

    public void addToOptionalContexts(Set<String> files) {
        hasOptionalWords = true;
        optionalContexts.addAll(files);
    }

    public void addToForbiddenContexts(Set<String> files) {
        forbiddenContexts.addAll(files);
    }
    public Set<String> intersectContexts() {
        Set<String> finalFiles = hasEssentialWords ? essentialContexts : optionalContexts;
        if (hasOptionalWords)
            finalFiles.retainAll(optionalContexts);
        finalFiles.removeAll(forbiddenContexts);
        if (!hasEssentialWords && !hasOptionalWords) {
            finalFiles = allContexts;
            finalFiles.removeAll(forbiddenContexts);
        }
        return finalFiles;
    }
}
