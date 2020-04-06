package practice.guestregistry.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
//@Order(SecurityProperties.BASIC_AUTH_ORDER)    // ACCESS_OVERRIDE_ORDER undefined, probably old spring
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
//        securedEnabled = true,
//        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/*").hasRole("USER")
                .antMatchers("/login", "/user").permitAll()
                .antMatchers("/h2-console/*").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and().headers().frameOptions().sameOrigin();  // for h2 web console
//                .and().headers().frameOptions().disable();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("pass")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
