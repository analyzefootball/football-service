package football.analyze.play;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Hassan Mushtaq
 * @since 6/11/18
 */
public class Prediction {

    @Getter
    private Match match;

    private Prediction() {
    }

    public Prediction(Match match) {
        this.match = match;
    }

    public void predictHomeTeamScore(Integer score) {
        if (isLocked()) {
            throw new UnsupportedOperationException();
        }
        match.setHomeTeamScore(score);
    }

    public void predictAwayTeamScore(Integer score) {
        if (isLocked()) {
            throw new UnsupportedOperationException();
        }
        match.setAwayTeamScore(score);
    }

    public void predictHomeTeamScoreForce(Integer score) {
        match.setHomeTeamScore(score);
    }

    public void predictAwayTeamScoreForce(Integer score) {
        match.setAwayTeamScore(score);
    }

    boolean isLocked() {
        return LocalDateTime.now().plusHours(1).isAfter(match.getDateTime());
    }

    boolean isPredicted() {
        return match.getHomeTeamScore() != null && match.getHomeTeamScore() != null;
    }

    boolean isReady() {
        return isLocked() && isPredicted();
    }

    private boolean isCorrectResult(Match actual) {
        return this.match.equals(actual) && this.match.getResultType().equals(actual.getResultType());
    }

    private boolean isBonusResult(Match actual) {
        return isCorrectResult(actual) && this.match.getHomeTeamScore().equals(actual.getHomeTeamScore())
                && this.match.getAwayTeamScore().equals(actual.getAwayTeamScore());
    }

    int getCorrectResultPoints(Match actual) {
        return isCorrectResult(actual) ? match.getMatchType().getCorrectPoints() : 0;
    }

    int getBonusPoints(Match actual) {
        return isBonusResult(actual) ? match.getMatchType().getBonusPoints() : 0;
    }
}
