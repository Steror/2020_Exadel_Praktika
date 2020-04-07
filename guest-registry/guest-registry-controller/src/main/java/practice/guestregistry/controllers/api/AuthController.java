package practice.guestregistry.controllers.api;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import practice.guestregistry.controllers.api.authentication.service.UserDetailsServiceImpl;
import practice.guestregistry.domain.User;
import practice.guestregistry.services.service.WorkerService;


import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@Api
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final WorkerService workerService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    AuthController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @RequestMapping("/login")   //TODO: Pagalvoti ar reikia sito metodo
    public boolean login(@RequestBody User user) {
        return workerService.matchUser(user);
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();

        log.debug(new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0]);

        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
