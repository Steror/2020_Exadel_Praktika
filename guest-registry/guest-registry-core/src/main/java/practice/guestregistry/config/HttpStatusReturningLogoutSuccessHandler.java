//package practice.guestregistry.config;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class HttpStatusReturningLogoutSuccessHandler implements LogoutSuccessHandler {
//
//    private final HttpStatus httpStatusToReturn;
//
//    public HttpStatusReturningLogoutSuccessHandler() {
//        this.httpStatusToReturn = HttpStatus.OK;
//    }
//
//    @Override
//    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
//                                HttpServletResponse httpServletResponse,
//                                Authentication authentication)
//            throws IOException, ServletException {
//        httpServletResponse.getStatus(httpStatusToReturn.value());
//        httpServletResponse.getWriter().flush();
//    }
//}
