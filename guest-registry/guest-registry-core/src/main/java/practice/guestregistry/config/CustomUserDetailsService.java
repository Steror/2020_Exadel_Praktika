//package practice.guestregistry.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import practice.guestregistry.services.service.PersonService;
//
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private PersonService personService;
//
//    public CustomUserDetailsService(PersonService personService) {
//        this.personService = personService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(userName)
//                      .orElseThrow(() -> new UsernameNotFoundException("Email "+userName+"
//                not found"));
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                getAuthorities(user)
//        );
//    }
//}
