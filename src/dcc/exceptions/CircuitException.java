package dcc.exceptions;

public abstract class CircuitException extends RuntimeException {
    public CircuitException(String message) {
        super(message);
    }
}