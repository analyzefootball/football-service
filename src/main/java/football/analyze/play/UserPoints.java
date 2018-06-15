package football.analyze.play;

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

    private Integer roundOneCorrectResult;

    private Integer roundOneBonus;
}
