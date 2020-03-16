package practice.guestregistry.exceptions;

//@ControllerAdvice()
//public class RestExceptionHandler extends ResponseEntityExceptionHandler
//{
//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(
//            HttpMessageNotReadableException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimeStamp(new Date().getTime());
//        errorDetail.setStatus(status.value());
//        errorDetail.setTitle("Message Not Readable");
//        errorDetail.setDetail(ex.getMessage());
//        errorDetail.setDeveloperMessage(ex.getClass().getName());
//        return handleExceptionInternal(ex, errorDetail, headers, status, request);
//    }
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNot
//                                                                       ValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {
//// implementation removed for brevity
//        return handleExceptionInternal(manve, errorDetail, headers, status, request);
//    }
//}