package practice.guestregistry.exceptions;


//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException() {}
    public ResourceNotFoundException(String message) {
        super(message);
        System.out.println("ERROR CALLED");
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
