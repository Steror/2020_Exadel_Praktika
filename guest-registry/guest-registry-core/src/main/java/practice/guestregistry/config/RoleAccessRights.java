package practice.guestregistry.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class RoleAccessRights extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/books*").hasAnyRole("USER", "GUEST")
                .antMatchers(HttpMethod.POST, "/books*").hasRole("USER")
        .antMatchers(HttpMethod.DELETE, "/books*")
                .access("hasRole('ROLE_ADMIN') or hasIpAddress('127.0.0.1') " +
                        "or hasIpAddress('0:0:0:0:0:0:0:1')");
    }
}
