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

    public static RoleType fromValue(int value) {
        for (RoleType r : values()) {
            if (r.value == value) return r;
        }
        return RoleType.USER;
    }
}
