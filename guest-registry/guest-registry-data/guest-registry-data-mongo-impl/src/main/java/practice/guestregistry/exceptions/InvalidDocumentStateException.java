package practice.guestregistry.exceptions;

public class InvalidDocumentStateException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public InvalidDocumentStateException() {}
    public InvalidDocumentStateException(String message) {super(message);}
    public InvalidDocumentStateException(String message, Throwable cause) {super(message, cause);}

}
