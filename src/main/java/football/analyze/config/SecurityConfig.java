package football.analyze.config;

import football.analyze.system.JWTAuthenticationFilter;
import football.analyze.system.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * @author hassan
 * @since 5/14/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String jwtSecret;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          @Value("${jwt.secret}") String jwtSecret) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtSecret = jwtSecret;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("Server", "Serving Football Analyze Application")).and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtSecret))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtSecret))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

}
