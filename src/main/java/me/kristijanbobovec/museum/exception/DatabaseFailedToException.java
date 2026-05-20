package me.kristijanbobovec.museum.exception;

import me.kristijanbobovec.museum.util.Messages;

public class DatabaseFailedToException extends RuntimeException {
    public DatabaseFailedToException() {
        this(Messages.Database.DATABASE_FAILEDTO_GENERIC);
    }
    public DatabaseFailedToException(String message) {
        super(message);
    }
}
