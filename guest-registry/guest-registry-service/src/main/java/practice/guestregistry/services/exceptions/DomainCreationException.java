package practice.guestregistry.services.exceptions;

public class DomainCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DomainCreationException() {
    }
    public DomainCreationException(String message) {
        super(message);
    }
    public DomainCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
