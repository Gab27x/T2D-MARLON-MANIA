package exceptions;

public class LoopsNotAllowedException extends Exception {
    public LoopsNotAllowedException(String message) {
        super(message);
    }
}