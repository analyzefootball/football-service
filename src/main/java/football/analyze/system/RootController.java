package football.analyze.system;

import football.analyze.play.TournamentController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RestController
@RequestMapping({"", "/"})
public class RootController {

    @GetMapping
    public ResourceSupport allApplicableURIs() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(TournamentController.class).getAllTournaments()).withRel("tournaments"));
        resource.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        return resource;
    }
}
