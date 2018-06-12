package football.analyze.play;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static football.analyze.system.SecurityConstants.AUTHORITIES;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Hassan Mushtaq
 * @since 5/27/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TournamentControllerTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String token;

    @Before
    public void setup() throws UnsupportedEncodingException {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        token = createJWT();
    }

    @Test
    public void getAllTournaments() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/tournaments/").header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._links.self[0].href", is("http://localhost/tournaments")))
                .andExpect(jsonPath("$._embedded.tournamentList[0].name", is("Fifa 2018 World Cup")));

    }

    @Test
    public void getTournamentByName() throws Exception {
        Tournament tournament = createTournament();
        MockHttpServletRequestBuilder request =
                get("/tournaments/" + tournament.getName()).header("Authorization", "Bearer " + token);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/tournaments/" + tournament.getName())))
                .andExpect(jsonPath("$.name", is("UniverseCup")));
    }

    private Tournament createTournament() {
        String name = "UniverseCup";
        LocalDate startDate = LocalDate.of(2020, 6, 15);
        LocalDate endDate = LocalDate.of(2020, 7, 14);

        Team team1 = new Team("Team1", "http://flags.com/team1");
        Team team2 = new Team("Team2", "http://flags.com/team2");
        Team team3 = new Team("Team3", "http://flags.com/team3");
        Team team4 = new Team("Team4", "http://flags.com/team4");
        Team team5 = new Team("Team5", "http://flags.com/team5");
        Team team6 = new Team("Team6", "http://flags.com/team6");
        Team team7 = new Team("Team7", "http://flags.com/team7");
        Team team8 = new Team("Team8", "http://flags.com/team8");
        Team team9 = new Team("Team9", "http://flags.com/team9");
        Team team10 = new Team("Team10", "http://flags.com/team10");
        Team team11 = new Team("Team11", "http://flags.com/team11");
        Team team12 = new Team("Team12", "http://flags.com/team12");
        Team team13 = new Team("Team13", "http://flags.com/team13");
        Team team14 = new Team("Team14", "http://flags.com/team14");
        Team team15 = new Team("Team15", "http://flags.com/team15");
        Team team16 = new Team("Team16", "http://flags.com/team16");
        List<Team> teams = Arrays.asList(team1, team2, team3, team4, team5, team6, team7, team8,
                team9, team10, team11, team12, team13, team14, team15, team16);

        Group group1 = new Group("Group1", Arrays.asList(team1, team2, team3, team4));
        Group group2 = new Group("Group2", Arrays.asList(team5, team6, team7, team8));
        Group group3 = new Group("Group3", Arrays.asList(team9, team10, team11, team12));
        Group group4 = new Group("Group4", Arrays.asList(team13, team14, team15, team16));
        List<Group> groups = Arrays.asList(group1, group2, group3, group4);

        Match match1 = new Match(team1, team2, LocalDateTime.now(), 1, MatchType.GROUP);
        Match match2 = new Match(team3, team4, LocalDateTime.now(), 2, MatchType.GROUP);
        Match match3 = new Match(team5, team6, LocalDateTime.now(), 3, MatchType.GROUP);
        Match match4 = new Match(team7, team8, LocalDateTime.now(), 4, MatchType.ROUND16);
        Match match5 = new Match(team9, team10, LocalDateTime.now(), 5, MatchType.QUARTERS);
        Match match6 = new Match(team11, team12, LocalDateTime.now(), 6, MatchType.SEMIS);
        Match match7 = new Match(team13, team14, LocalDateTime.now(), 7, MatchType.THIRD);
        Match match8 = new Match(team15, team16, LocalDateTime.now(), 8, MatchType.FINAL);
        match8.setAwayTeam(team1);
        match8.setHomeTeam(team2);
        match8.setAwayTeamScore(4);
        match8.setHomeTeamScore(2);


        List<Match> matchList = Arrays.asList(match1, match2, match3, match4, match5, match6, match7, match8);

        Schedule schedule = new Schedule(matchList);

        Tournament tournament = new Tournament(name, startDate, endDate, teams, groups, schedule);

        tournamentRepository.save(tournament);
        return tournament;
    }

    private String createJWT() throws UnsupportedEncodingException {
        ZonedDateTime zdt = LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());
        String[] grantedAuthorities = {"ROLE_ADMIN"};
        return JWT.create().withSubject("admin")
                .withArrayClaim(AUTHORITIES, grantedAuthorities)
                .withClaim("admin", true)
                .withClaim("displayName", "Super Admin")
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(jwtSecret));
    }
}