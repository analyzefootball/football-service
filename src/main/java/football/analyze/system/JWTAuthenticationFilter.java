package football.analyze.system;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static football.analyze.system.SecurityConstants.*;

/**
 * @author Hassan Mushtaq
 * @since 6/4/18
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final String jwtSecret;

    private final UserRepository userRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   String jwtSecret, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtSecret = jwtSecret;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        ZonedDateTime zdt = LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

        User appUser = userRepository.findByUsername(user.getUsername());

        Collection<GrantedAuthority> grantedAuthorities = user.getAuthorities();

        String[] authorities = new String[grantedAuthorities.size()];

        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toArray(authorities);

        String token = JWT.create().withSubject(user.getUsername())
                .withArrayClaim(AUTHORITIES, authorities)
                .withClaim("admin", appUser.isAdmin())
                .withClaim("displayName", appUser.getDisplayName())
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(jwtSecret));

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
