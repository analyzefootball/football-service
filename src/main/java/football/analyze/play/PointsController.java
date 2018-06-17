package football.analyze.play;

import football.analyze.system.Role;
import football.analyze.system.User;
import football.analyze.system.UserService;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hassan Mushtaq
 * @since 6/16/18
 */
@RestController
@RequestMapping("/points")
public class PointsController {

    private final TournamentRepository tournamentRepository;

    private final UserService userService;

    public PointsController(TournamentRepository tournamentRepository, UserService userService) {
        this.tournamentRepository = tournamentRepository;
        this.userService = userService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getPointsTable() {
        List<User> userList = userService.findAll();

        userList.removeIf(user -> user.getRole().equals(Role.SYSTEM));
        Tournament tournament = tournamentRepository.findByName("Fifa 2018 World Cup");

        List<Match> playedMatches = tournament.getSchedule().getMatches().stream()
                .filter(match -> !match.getResultType().equals(ResultType.INVALID)).collect(Collectors.toList());

        PointsCalculator pointsCalculator = new PointsCalculator(playedMatches);

        List<UserPoints> userPointsList = new ArrayList<>();

        userList.forEach(user -> {
            List<Prediction> lockedPredictions = user.getPredictions().stream().filter(Prediction::isReady).collect(Collectors.toList());
            userPointsList.add(pointsCalculator.calculateUserPoints(user.getDisplayName(), lockedPredictions));
        });

        PointsTable pointsTable = new PointsTable();

        userPointsList.sort(Comparator.comparing(UserPoints::getTotal));

        pointsTable.setUserPoints(userPointsList);

        Resource<PointsTable> resource = new Resource<>(pointsTable);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
