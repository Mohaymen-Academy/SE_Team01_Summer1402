package dataStructures;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import java.util.Objects;

@ToString
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

}
