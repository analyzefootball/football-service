package football.analyze.play;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Team {

    private String name;

    private String flagUrl;

    private Team() {
    }

    public Team(String name, String flagUrl) {
        this.name = name;
        this.flagUrl = flagUrl;
    }

    public boolean isActualTeam()   {
        return StringUtils.isNotBlank(flagUrl);
    }
}
