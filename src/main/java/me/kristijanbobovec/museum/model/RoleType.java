package me.kristijanbobovec.museum.model;

public enum RoleType {
    USER(1),
    ADMINISTRATOR(2);

    private final int value;

    RoleType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
