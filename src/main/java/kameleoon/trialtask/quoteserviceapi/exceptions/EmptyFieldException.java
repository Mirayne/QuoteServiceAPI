package kameleoon.trialtask.quoteserviceapi.exceptions;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message) {
        super(message);
    }
}
