package Exceptions;

public class NonComparableElementException extends Exception {

    public static final String DEFAULT_MSG = "Parameter element is not Comparable.";
    public NonComparableElementException() {
    }

    public NonComparableElementException(String message) {
        super(message);
    }
}
