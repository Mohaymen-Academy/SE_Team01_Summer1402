package dataStructures;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class Score {
    private final long totalWordsCount;
    private final long targetWordCount;

    public double getScore() {
        return -Math.log((double) targetWordCount / totalWordsCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return totalWordsCount == score.totalWordsCount && targetWordCount == score.targetWordCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalWordsCount, targetWordCount);
    }

    @Override
    public String toString() {
        return "Score{" +
                "totalWordsNum=" + totalWordsCount +
                ", wordNum=" + targetWordCount +
                '}';
    }
}
