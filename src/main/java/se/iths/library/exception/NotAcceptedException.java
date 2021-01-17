package se.iths.library.exception;

public class NotAcceptedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public NotAcceptedException(String message) {
        super(message);
    }
}
