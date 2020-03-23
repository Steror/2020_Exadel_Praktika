package practice.guestregistry.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final String headerValue;

    public Http401AuthenticationEntryPoint(String headerValue) {
        this.headerValue = headerValue;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException, ServletException {
        httpServletResponse.setHeader("WWW-Authenticate", this.headerValue);
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage());
    }
}
