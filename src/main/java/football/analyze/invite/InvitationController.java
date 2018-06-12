package football.analyze.invite;

import football.analyze.system.IsAdmin;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@RestController
@RequestMapping("/invitations")
@IsAdmin
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getAllInvitations() {
        Resources<Invitation> resource = new Resources<>(invitationService.findAll());
        resource.add(linkTo(methodOn(InvitationController.class).getAllInvitations()).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value = "{inviteId}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getInvitation(@PathVariable String inviteId) {
        Invitation invitation = invitationService.findById(inviteId);
        if (invitation != null) {
            Resource<Invitation> resource = new Resource<>(invitation);
            resource.add(linkTo(methodOn(InvitationController.class).getInvitation(inviteId)).withRel("self"));
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity sendInvite(@RequestBody Invitation invitation) {
        invitationService.sendInvite(invitation);
        ResourceSupport restUris = new ResourceSupport();
        restUris.add(linkTo(methodOn(InvitationController.class).getInvitation(invitation.getId())).withRel("self"));
        return new ResponseEntity<>(restUris, HttpStatus.CREATED);
    }
}
