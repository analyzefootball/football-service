package football.analyze;

import football.analyze.play.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * Spring boot application starting point.
 *
 * @author hassan
 * @since 5/7/18
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TournamentRepository tournamentRepository;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        LocalDate startDate = LocalDate.of(2018, 6, 14);
//        LocalDate endDate = LocalDate.of(2018, 7, 15);
//        Team saudi = new Team("Saudi Arabia",
//                "https://s3-us-west-2.amazonaws.com/football-static-content/fifa2018/flags/Saudi_Arabia.png", 32);
//        Team iran = new Team("Iran",
//                "https://s3-us-west-2.amazonaws.com/football-static-content/fifa2018/flags/Iran.png", 21);
//        List<Team> teams = Arrays.asList(saudi, iran);
//        Group groupA = new Group("A", teams);
//        Group groupB = new Group("B", teams);
//        List<Group> groups = Arrays.asList(groupA, groupB);
//        LocalDateTime localDateTime1 = LocalDateTime.of(2018, 6, 14, 14, 30);
//        LocalDateTime localDateTime2 = LocalDateTime.of(2018, 6, 21, 14, 30);
//        Match match1 = new Match(saudi, iran, localDateTime1);
//        Match match2 = new Match(iran, saudi, localDateTime2);
//        List<Match> matches = Arrays.asList(match1, match2);
//        Schedule schedule = new Schedule(matches);
//        Tournament tournament = new Tournament("Fifa 2018 World Cup",
//                startDate, endDate, teams, groups, schedule);
//        tournamentRepository.save(tournament);
    }
}
