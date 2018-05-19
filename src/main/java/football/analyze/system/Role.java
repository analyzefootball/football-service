package football.analyze.system;

import lombok.Getter;

/**
 * @author Hassan Mushtaq
 * @since 5/18/18
 */
@Getter
public enum Role {

    ADMIN("admin"),
    REGULAR("regular");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
