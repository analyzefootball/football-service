package football.analyze.common;

import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

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

    protected Entity(String id) {
        this.id = id;
    }
}
