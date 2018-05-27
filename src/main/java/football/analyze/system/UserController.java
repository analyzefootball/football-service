package football.analyze.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getAllUsers() {
        User user1 = new User(UUID.randomUUID().toString(), "user1", Role.Admin, new Credentials("user1@aabc.com", "password"));
        User user2 = new User(UUID.randomUUID().toString(), "user2", Role.Regular, new Credentials("user2@aabc.com", "password2"));
        List<User> users = Arrays.asList(user1, user2);
        Resources<User> resource = new Resources<>(users);
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value = "{username}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getUser(@PathVariable String username) {
        User user1 = new User(UUID.randomUUID().toString(), "user1", Role.Admin, new Credentials(username, "password"));
        Resource<User> resource = new Resource<>(user1);
        resource.add(linkTo(methodOn(UserController.class).getUser(username)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        ResourceSupport restUris = new ResourceSupport();
        restUris.add(linkTo(methodOn(UserController.class).getUser(user.getCredentials().getUsername())).withRel("self"));
        return new ResponseEntity<>(restUris, HttpStatus.CREATED);
    }
}
