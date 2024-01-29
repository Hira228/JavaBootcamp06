package edu.school21.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String err) {
        super(err);
    }
}
