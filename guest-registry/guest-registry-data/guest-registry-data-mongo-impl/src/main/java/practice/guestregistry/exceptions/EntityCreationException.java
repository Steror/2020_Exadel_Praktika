package practice.guestregistry.exceptions;

public class EntityCreationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EntityCreationException() {
    }
    public EntityCreationException(String message) {
        super(message);
    }
    public EntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
