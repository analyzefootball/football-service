package football.analyze.play;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hassan Mushtaq
 * @since 6/14/18
 */
@Setter
@Getter
@NoArgsConstructor
public class UserPoints {

    private String user;

    private Integer roundOneCorrectResult = 0;

    private Integer roundOneBonus = 0;

    private Integer roundSecondCorrectResult = 0;

    private Integer roundSecondBonus = 0;

    private Integer quarterCorrectResult = 0;

    private Integer quarterBonus = 0;

    private Integer semiCorrectResult = 0;

    private Integer semiBonus = 0;

    private Integer thirdCorrectResult = 0;

    private Integer thirdBonus = 0;

    private Integer finalCorrectResult = 0;

    private Integer finalBonus = 0;

    void addToRoundOneCorrectResult(Integer points) {
        roundOneCorrectResult += points;
    }

    void addToRoundOneBonus(Integer points) {
        roundOneBonus += points;
    }

    void addToRoundSecondCorrectResult(Integer points) {
        roundSecondCorrectResult += points;
    }

    void addToRoundSecondBonus(Integer points) {
        roundSecondBonus += points;
    }

    void addToQuarterCorrectResult(Integer points) {
        quarterCorrectResult += points;
    }

    void addToQuarterBonus(Integer points) {
        quarterBonus += points;
    }

    void addToSemiCorrectResult(Integer points) {
        semiCorrectResult += points;
    }

    void addToSemiBonus(Integer points) {
        semiBonus += points;
    }

    void addToThirdCorrectResult(Integer points) {
        thirdCorrectResult += points;
    }

    void addToThirdBonus(Integer points) {
        thirdBonus += points;
    }

    void addToFinalCorrectResultt(Integer points) {
        finalCorrectResult += points;
    }

    void addToFinalBonus(Integer points) {
        finalBonus += points;
    }

    @JsonGetter("total")
    Integer getTotal() {
        return roundOneCorrectResult +

                roundOneBonus +

                roundSecondCorrectResult +

                roundSecondBonus +

                quarterCorrectResult +

                quarterBonus +

                semiCorrectResult +

                semiBonus +

                thirdCorrectResult +

                thirdBonus +

                finalCorrectResult +

                finalBonus;
    }
}
