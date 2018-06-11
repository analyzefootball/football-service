package football.analyze.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @IsAdmin
    public ResponseEntity getAllUsers() {
        Resources<User> resource = new Resources<>(userService.findAll());
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value = "{username}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM') or #username == authentication.name")
    public ResponseEntity getUser(@PathVariable String username) {
        Resource<User> resource = new Resource<>(userService.findByUsername(username));
        resource.add(linkTo(methodOn(UserController.class).getUser(username)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(value = "{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM') or #username == authentication.name")
    public ResponseEntity updateUser(@PathVariable String username, @RequestBody User user) {
        try {
            userService.updateUser(username, user);
            ResourceSupport restUris = new ResourceSupport();
            restUris.add(linkTo(methodOn(UserController.class).getUser(username)).withRel("self"));
            return new ResponseEntity<>(restUris, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in UserController updateUser", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
