package football.analyze.system;

import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity getUsers() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1,user2);
        Resources<User> resource = new Resources<>(users);
        resource.add(linkTo(methodOn(UserController.class).getUsers()).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
