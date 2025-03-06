package gui.interpreter.exceptions;

public class KeyNotFoundException extends ADTException {
    public KeyNotFoundException() {
        super("Key not found");
    }
}
