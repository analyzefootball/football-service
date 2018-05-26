package football.analyze.play;

import lombok.Getter;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Group {

    private String name;

    private List<Team> teams;

    private Group() {
    }

    public Group(String name, List<Team> teams) {
        this.name = name;
        this.teams = teams;
    }
}
