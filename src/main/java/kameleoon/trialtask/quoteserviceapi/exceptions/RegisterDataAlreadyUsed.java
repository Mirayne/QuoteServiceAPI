package kameleoon.trialtask.quoteserviceapi.exceptions;

public class RegisterDataAlreadyUsed extends RuntimeException{
    public RegisterDataAlreadyUsed(String message) {
        super(message);
    }
}
