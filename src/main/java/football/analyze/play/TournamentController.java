package football.analyze.play;

import football.analyze.system.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@RestController
@RequestMapping("/tournaments")
@Slf4j
public class TournamentController {

    private final TournamentRepository tournamentRepository;

    private final UserService userService;

    public TournamentController(TournamentRepository tournamentRepository, UserService userService) {
        this.tournamentRepository = tournamentRepository;
        this.userService = userService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getAllTournaments() {
        Resources<Tournament> resource = new Resources<>(tournamentRepository.findAll());
        resource.add(linkTo(methodOn(TournamentController.class).getAllTournaments()).withRel("self"));
        resource.add(linkTo(methodOn(TournamentController.class).getTournament(null)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value = "{tournamentName}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getTournament(@PathVariable String tournamentName) {
        Resource<Tournament> resource = new Resource<>(tournamentRepository.findByName(tournamentName));
        resource.add(linkTo(methodOn(TournamentController.class).getTournament(tournamentName)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping(value = "{tournamentName}/match")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM') or #username == authentication.name")
    public ResponseEntity updateMatch(@PathVariable String tournamentName,
                                      @RequestBody Match match) {
        try {
            Tournament tournament = tournamentRepository.findByName(tournamentName);
            tournament.updateMatchResult(match);
            tournamentRepository.save(tournament);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in TournamentController updateUser", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "{tournamentName}/match/teams")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SYSTEM') or #username == authentication.name")
    public ResponseEntity updateMatchTeams(@PathVariable String tournamentName,
                                      @RequestBody Match match) {
        try {
            Tournament tournament = tournamentRepository.findByName(tournamentName);
            Match existing = tournament.updateMatchTeams(match);
            tournamentRepository.save(tournament);
            userService.updateUsersWithMatch(existing);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error in TournamentController updateUser", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
