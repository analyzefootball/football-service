package football.analyze.invite;

import football.analyze.system.User;
import football.analyze.system.UserController;
import football.analyze.system.UserService;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@RestController
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "{inviteId}")
    public ResponseEntity createUser(@PathVariable String inviteId, @Valid @RequestBody User user) {
        try {
            userService.signUpUser(inviteId, user);
            ResourceSupport restUris = new ResourceSupport();
            restUris.add(linkTo(methodOn(UserController.class).getUser(user.getUsername())).withRel("self"));
            return new ResponseEntity<>(restUris, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
