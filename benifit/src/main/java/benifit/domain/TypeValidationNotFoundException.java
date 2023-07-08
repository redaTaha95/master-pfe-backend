package benifit.domain;

public class TypeValidationNotFoundException extends RuntimeException {
    public TypeValidationNotFoundException(String message) {
        super(message);
    }
}
