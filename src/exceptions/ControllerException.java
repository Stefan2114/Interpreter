package exceptions;

public class ControllerException extends RuntimeException {
    public ControllerException(Throwable cause) {
        super(cause);
    }
}