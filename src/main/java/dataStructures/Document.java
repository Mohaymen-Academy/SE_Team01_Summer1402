package dataStructures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Document {
    private final String fileName;
    private final long totalWordsNum;
    private long wordNum;

    public double getScore() {
        return Math.log((double) wordNum / totalWordsNum);
    }
}
