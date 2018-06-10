package football.analyze.play;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentRepository tournamentRepository;

    public TournamentController(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getAllTournaments() {
        Resources<Tournament> resource = new Resources<>(tournamentRepository.findAll());
        resource.add(linkTo(methodOn(TournamentController.class).getAllTournaments()).withRel("self"));
        resource.add(linkTo(methodOn(TournamentController.class).getTournament(null)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping(value = "{tournamentId}", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getTournament(@PathVariable String tournamentId) {
        Resource<Tournament> resource = new Resource<>(tournamentRepository.findById(tournamentId).get());
        resource.add(linkTo(methodOn(TournamentController.class).getTournament(tournamentId)).withRel("self"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
