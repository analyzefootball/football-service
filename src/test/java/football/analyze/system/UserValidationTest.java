package football.analyze.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidationTest {

    @Autowired
    private Validator validator;

    @Test
    public void shouldValidateDisplayName() {
        final User user = new User(null, Role.ADMIN, "username", "password", null);
        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldValidateRole() {
        final User user = new User("Display Name", null, "username", "password", null);
        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldValidateUsername() {
        final User user = new User("display name", Role.ADMIN, " ", "password", null);
        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.stream().findFirst().get().getMessage());
    }

    @Test
    public void shouldValidatePassword() {
        final User user = new User("display name", Role.ADMIN, "username", "", null);
        final Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.stream().findFirst().get().getMessage());
    }
}