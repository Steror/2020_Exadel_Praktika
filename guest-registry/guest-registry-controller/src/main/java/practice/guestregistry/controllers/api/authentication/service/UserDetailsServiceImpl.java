package practice.guestregistry.controllers.api.authentication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import practice.guestregistry.domain.User;
import practice.guestregistry.services.service.WorkerService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private WorkerService workerService;
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
//    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(this + " Entered UserDetails method");
        User user = new User(username, new BCryptPasswordEncoder().encode("456"), null);
        workerService.matchUser(user);
//        User user = userRepository.findByUsername(username);
        if (user.getRole() == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
        log.debug(String.valueOf(new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities)));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
