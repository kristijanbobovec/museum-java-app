package me.kristijanbobovec.museum.exception;

import java.util.List;

public class OmitingRequiredFieldsException extends RuntimeException {
    private final List<String> errors;

    public OmitingRequiredFieldsException(List<String> errors) {
        super(String.join(" ", errors));
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
