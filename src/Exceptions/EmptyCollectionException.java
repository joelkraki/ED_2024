package Exceptions;

public class EmptyCollectionException extends Exception {

    public static final String DEFAULT_MESSAGE = "The collection is empty.";

    public EmptyCollectionException() {
    }

    public EmptyCollectionException(String message) {
        super(message);
    }
}
