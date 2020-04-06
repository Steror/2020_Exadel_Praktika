package practice.guestregistry.exceptions;

public class EntityDeletionException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public EntityDeletionException() {}
    public EntityDeletionException(String message) {
        super(message);
    }
    public EntityDeletionException(String message, Throwable cause) {
        super(message, cause);
    }
}
