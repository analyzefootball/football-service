package football.analyze.common;

import lombok.Getter;
import org.springframework.data.annotation.Id;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public abstract class Entity {

    @Id
    private String id;

    protected Entity() {
    }
}
