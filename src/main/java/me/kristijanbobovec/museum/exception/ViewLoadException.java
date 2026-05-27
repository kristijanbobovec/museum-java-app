package me.kristijanbobovec.museum.exception;

public class ViewLoadException extends RuntimeException {
    public ViewLoadException(String fxml, Throwable cause) {
        super("Failed to load a view: " + fxml, cause);
    }
}
