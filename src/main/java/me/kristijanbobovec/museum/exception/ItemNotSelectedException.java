package me.kristijanbobovec.museum.exception;

import me.kristijanbobovec.museum.util.Messages;

public class ItemNotSelectedException extends RuntimeException {
    public ItemNotSelectedException() {
        this(Messages.CRUDValidation.ITEM_NOT_SELECTED_GENERIC);
    }
    public ItemNotSelectedException(String message) {
        super(message);
    }
}
