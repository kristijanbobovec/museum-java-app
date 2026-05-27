package me.kristijanbobovec.museum.exception;

public class ControllerInstantiationException extends RuntimeException {
    public ControllerInstantiationException(Class<?> controllerClass, Throwable cause) {
        super("Cannot instantiate controller: " + controllerClass, cause);
    }
}
