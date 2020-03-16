package practice.guestregistry.api;

import org.springframework.web.bind.annotation.*;
import practice.guestregistry.models.User;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {
        return
                user.getUsername().equals("user") && user.getPassword().equals("pass");
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
