package football.analyze.play;

import football.analyze.system.User;
import football.analyze.system.UserService;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@RestController
@RequestMapping("/predictions")
public class PredictionController {

    private final UserService userService;

    public PredictionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaTypes.HAL_JSON_UTF8_VALUE)
    public ResponseEntity getAllLockedPredictions() {
        List<User> userList = userService.findAll();

        List<PlayedMatch> matchPredictions = new ArrayList<>();

        List<UserPrediction> userPredictions = userList.stream().map(user -> {
            List<Prediction> lockedPredictions = user.getPredictions().stream().filter(Prediction::isReady).collect(Collectors.toList());
            return lockedPredictions.stream().map(prediction -> {
                UserPrediction userPrediction = new UserPrediction();
                userPrediction.setUser(user.getDisplayName());
                userPrediction.setMatch(prediction.getMatch());
                return userPrediction;
            }).collect(Collectors.toList());
        }).flatMap(Collection::stream)
                .collect(Collectors.toList());

        Map<Match, List<UserPrediction>> matchListMap = userPredictions.stream().collect(groupingBy(UserPrediction::getMatch));

        matchListMap.forEach((key, value) -> matchPredictions.add(new PlayedMatch(key, fromUserPrediction(value))));

        Resources<PlayedMatch> resource = new Resources<>(matchPredictions);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PostMapping(value = "{username}")
    public ResponseEntity savePrediction(@PathVariable String username, @RequestBody Prediction prediction) {
        try {
            User user = userService.findByUsername(username);
            user.predict(prediction);
            userService.saveUser(user);
            ResourceSupport restUris = new ResourceSupport();
            return new ResponseEntity<>(restUris, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private List<UserMatchPrediction> fromUserPrediction(List<UserPrediction> userPredictions) {
        return userPredictions.stream().map(userPrediction -> new UserMatchPrediction(userPrediction.getUser(), userPrediction.getMatch().getHomeTeamScore(),
                userPrediction.getMatch().getAwayTeamScore())).collect(Collectors.toList());
    }
}
