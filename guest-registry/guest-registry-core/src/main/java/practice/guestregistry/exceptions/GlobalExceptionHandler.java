package practice.guestregistry.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(InvalidDocumentStateException.class)
    public ResponseEntity<?> invalidDocumentState(InvalidDocumentStateException ex) {
        ErrorDetails err = new ErrorDetails();
        err.setErrorCode(HttpStatus.BAD_REQUEST.value());
        err.setTimeStamp(new Date());
        err.setErrorMessage(ex.getMessage());
        err.setDevErrorMessage("THIS STUFF CAN BE LOGGED HERE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetails err = new ErrorDetails();
        err.setErrorCode(HttpStatus.BAD_REQUEST.value());
        err.setTimeStamp(new Date());
        err.setErrorMessage(ex.getMessage());
        err.setDevErrorMessage("THIS STUFF CAN BE LOGGED HERE");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
//            details.add(error.getDefaultMessage());
//        }
//        ErrorResponse error = new ErrorResponse("Validation Failed", details);
//        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
//    }

    //Form rest of errors to hide/add information
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> exception(Exception e) {
//        ErrorDetails errorDetails = new ErrorDetails();
//        errorDetails.setErrorMessage(e.getMessage());
//        errorDetails.setDevErrorMessage(getStackTraceAsString(e));
//        errorDetails.setTimeStamp(new Date());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private String getStackTraceAsString(Exception e) {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        return sw.toString();
//    }
//------------------------------------------------------------------



// @ExceptionHandler(MethodArgumentNotValidException.class)
//   public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException manve,
// HttpServletRequest request)
}
