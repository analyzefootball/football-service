package football.analyze.play;

import lombok.Getter;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@Getter
public enum MatchType {
    GROUP(2, 1), ROUND16(3, 1), QUARTERS(4, 2), SEMIS(5, 2), THIRD(3, 1), FINAL(7, 3);

    private final Integer correctPoints;

    private final Integer bonusPoints;


    MatchType(Integer correctPoints, Integer bonusPoints) {
        this.correctPoints = correctPoints;
        this.bonusPoints = bonusPoints;
    }
}