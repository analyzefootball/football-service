package football.analyze.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * @author hassan
 * @since 5/14/18
 */
@Configuration
@EnableWebSecurity
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/VAADIN/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Server", "Serving Football Analyze Application")).and()
//                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and()
//                .authorizeRequests().antMatchers("/actuator/**").permitAll().and()
//                .authorizeRequests().antMatchers("/static/**").permitAll().and()
//                .authorizeRequests().antMatchers("/vaadinServlet/UIDL/**").permitAll().and()
//                .authorizeRequests().antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll()
//                .anyRequest().authenticated().and()
//                .formLogin().failureUrl("/?error").defaultSuccessUrl("/predictions")
//                .loginPage("/").permitAll()
//                .loginProcessingUrl("/api/login")
//                .and().logout().logoutUrl("/api/logout").logoutSuccessUrl("/").permitAll();

        http.csrf().disable()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("Server", "Serving Football Analyze Application")).and()
                .authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
