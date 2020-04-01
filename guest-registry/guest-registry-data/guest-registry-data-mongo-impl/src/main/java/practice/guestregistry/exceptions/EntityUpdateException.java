package practice.guestregistry.exceptions;

public class EntityUpdateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EntityUpdateException() {}
    public EntityUpdateException(String message) {
        super(message);
    }
    public EntityUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
