package me.kristijanbobovec.museum.exception;

import me.kristijanbobovec.museum.util.Messages;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        this(Messages.Auth.LOGIN_FAILED);
    }
    public WrongCredentialsException(String message) {
        super(message);
    }
}
