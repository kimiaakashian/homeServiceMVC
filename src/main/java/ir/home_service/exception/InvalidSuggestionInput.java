package ir.home_service.exception;

public class InvalidSuggestionInput extends RuntimeException{
    public InvalidSuggestionInput(String message) {
        super(message);
    }
}
