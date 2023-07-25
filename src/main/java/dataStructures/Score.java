package dataStructures;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Score {
    private final long totalWordsNum;
    private long wordNum;

    public double getScore() {
        return -Math.log((double) wordNum / totalWordsNum);
    }

    public Score add() {
        wordNum++;
        return this;
    }
}
