package me.kristijanbobovec.museum.util;

public final class Messages {
    private Messages() {}

    public static final class Configuration {
        public static final String FILE_NOT_FOUND = "\"%s\" not found. Using default configuration!%n";
        public static final String FILE_ERROR = "Error with reading a file \"%s\". Using default configuration!%n";
    }

    public static final class Server {
        public static final String SERVER_ERROR_HEADER = "Server error!";
        public static final String SERVER_ERROR_CONTENT = "There is a problem with the server try again later!";
    }

    public static final class Auth {
        public static final String LOGIN_FAILED = "Your username or password is wrong!";
        public static final String SERVER_ERROR_CONTENT = "There is a problem with the server try again later!";
        public static final String USERNAME_TAKEN = "Username is already taken.";
        public static final String EMAIL_TAKEN = "Email is already in use.";
        public static final String EMPTY_FIELDS = "You must fill out all fields.";
        public static final String EMAIL_FORMAT = "Email format is not valid (Ex: kbobovec@algebra.hr).";
        public static final String PASSWORD_NOT_VALID = "Password must be at least 8 characters long!";
    }
}
