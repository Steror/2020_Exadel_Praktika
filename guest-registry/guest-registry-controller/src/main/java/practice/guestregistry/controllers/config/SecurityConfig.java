package practice.guestregistry.controllers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import practice.guestregistry.controllers.api.authentication.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"practice.guestregistry.controllers.api.authentication.service"})
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true)
//        securedEnabled = true,
//        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Qualifier("customAuthenticationProvider")
//    @Autowired
//    private AuthenticationProvider customAuthenticationProvider;
//    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsServiceImpl);//.passwordEncoder(passwordEncoder());
    }

//    @Bean
//    public AuthenticationManager customAuthenticationManager() throws Exception {
//        return authenticationManager();
//    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.authenticationProvider(customAuthenticationProvider);
//
//        auth.inMemoryAuthentication()
//                .withUser("memuser")
//                .password(passwordEncoder().encode("pass"))
//                .roles("USER");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/*").hasAuthority("USER")
                .antMatchers("/login", "/user").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("pass")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
