package dataStructures;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Score {
    private final long totalWordsNum;
    private final long wordNum;

    public double getScore() {
        return -Math.log((double) wordNum / totalWordsNum);
    }

}
