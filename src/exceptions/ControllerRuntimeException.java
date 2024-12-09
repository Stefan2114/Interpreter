package exceptions;

public class ControllerRuntimeException extends RuntimeException {
    public ControllerRuntimeException(Throwable cause) {
        super(cause);
    }
}
