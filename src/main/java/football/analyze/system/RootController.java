package football.analyze.system;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RestController
@RequestMapping({"", "/"})
public class RootController {

    @GetMapping
    public ResourceSupport allApplicableURIs() {
        ResourceSupport restUris = new ResourceSupport();
        restUris.add(linkTo(LoginController.class).withRel("login"));
        return restUris;
    }
}
