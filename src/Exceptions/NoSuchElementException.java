package Exceptions;

public class NoSuchElementException extends Exception {

    public static final String DEFAULT_MSG = "Element not found in Collection";

    public NoSuchElementException() {
    }

    public NoSuchElementException(String message) {
        super(message);
    }
}
