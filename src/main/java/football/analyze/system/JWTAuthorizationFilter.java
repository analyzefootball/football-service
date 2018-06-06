package football.analyze.system;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static football.analyze.system.SecurityConstants.*;

/**
 * @author Hassan Mushtaq
 * @since 6/5/18
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String jwtSecret;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String jwtSecret) {
        super(authenticationManager);
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String token = req.getHeader(HEADER_STRING);

        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(token.replace(TOKEN_PREFIX, ""));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws UnsupportedEncodingException {
        // parse the token.
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT jwt = verifier.verify(token.replace(TOKEN_PREFIX, ""));

        String user = jwt.getSubject();

        Claim claim = jwt.getClaim(AUTHORITIES);

        List<String> roleList = claim.asList(String.class);

        List<GrantedAuthority> grantedAuthorities = roleList.stream().map((Function<String, GrantedAuthority>) SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
    }
}
