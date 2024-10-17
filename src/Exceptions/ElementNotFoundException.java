package Exceptions;

public class ElementNotFoundException extends Exception {

    public static final String DEFAULT_MSG = "Element not found in collection.";
    public ElementNotFoundException() {
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}
