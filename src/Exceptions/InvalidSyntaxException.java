package Exceptions;

public class InvalidSyntaxException extends Exception{

    public InvalidSyntaxException() {
    }

    public InvalidSyntaxException(String message) {
        super(message);
    }
}
