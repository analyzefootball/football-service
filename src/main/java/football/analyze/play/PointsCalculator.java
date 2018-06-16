package football.analyze.play;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 6/16/18
 */
public class PointsCalculator {

    private final List<Match> playedMatches;

    public PointsCalculator(List<Match> playedMatches) {
        this.playedMatches = playedMatches;
    }

    public UserPoints calculateUserPoints(String user, List<Prediction> readyPredictions) {
        final UserPoints userPoints = new UserPoints();
        userPoints.setUser(user);
        readyPredictions.forEach(prediction -> {
            int indexOf = playedMatches.indexOf(prediction.getMatch());
            Match actual = indexOf == -1 ? null : playedMatches.get(indexOf);
            if (actual == null) {
                //do nothing
            } else if (actual.getMatchType().equals(MatchType.GROUP)) {
                userPoints.addToRoundOneCorrectResult(prediction.getCorrectResultPoints(actual));
                userPoints.addToRoundOneBonus(prediction.getBonusPoints(actual));
            } else if (actual.getMatchType().equals(MatchType.ROUND16)) {
                userPoints.addToRoundSecondCorrectResult(prediction.getCorrectResultPoints(actual));
                userPoints.addToRoundSecondBonus(prediction.getBonusPoints(actual));
            } else if (actual.getMatchType().equals(MatchType.QUARTERS)) {
                userPoints.addToQuarterCorrectResult(prediction.getCorrectResultPoints(actual));
                userPoints.addToQuarterBonus(prediction.getBonusPoints(actual));
            } else if (actual.getMatchType().equals(MatchType.SEMIS)) {
                userPoints.addToSemiCorrectResult(prediction.getCorrectResultPoints(actual));
                userPoints.addToSemiBonus(prediction.getBonusPoints(actual));
            } else if (actual.getMatchType().equals(MatchType.THIRD)) {
                userPoints.addToThirdCorrectResult(prediction.getCorrectResultPoints(actual));
                userPoints.addToThirdBonus(prediction.getBonusPoints(actual));
            } else if (actual.getMatchType().equals(MatchType.FINAL)) {
                userPoints.addToFinalCorrectResultt(prediction.getCorrectResultPoints(actual));
                userPoints.addToFinalBonus(prediction.getBonusPoints(actual));
            }
        });

        return userPoints;
    }

}
