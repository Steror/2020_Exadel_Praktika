package practice.guestRegistry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DocumentNotFound.class)
    public ResponseEntity<?> documentNotFoundException(DocumentNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }




//    @ExceptionHandler(ServletRequestBindingException.class)
//    public ResponseEntity<?> servletRequestBindingException(ServletRequestBindingException e) {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setErrorMessage(e.getMessage());
//        errorDetails.setDevErrorMessage(getStackTraceAsString(e));
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({SequenceException.class})
//    public ResponseEntity<?> deal(SequenceException ex) {
//        System.out.println("i have been called");
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> exception(Exception e) {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setErrorMessage(e.getMessage());
//        errorDetails.setDevErrorMessage(getStackTraceAsString(e));
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    private String getStackTraceAsString(Exception e)
//    {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        return sw.toString();
//    }

}
