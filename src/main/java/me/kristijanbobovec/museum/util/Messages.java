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

    public static final class Database {
        public static final String DATABASE_STARTED_CREATION = "Database not found creating new one!";
        public static final String DATABASE_CREATED = "Database is created!";
        public static final String DATABASE_FAIL_CREATION = "Database failed with creation!";
        public static final String DATABASE_FAIL_DROP = "Failed to drop database after failed initialization.";
        public static final String DATABASE_CREATING_TABLES = "Creating tables...";
        public static final String DATABASE_CREATING_PROCEDURES = "Creating stored procedures...";
        public static final String DATABASE_CREATING_ROLES = "Creating roles...";
        public static final String DATABASE_CREATING_ADMIN = "Creating admin account...";
        public static final String DATABASE_SEEDING = "Seeding initial data...";
    }

    public static final class Museum {
        public static final String MUSEUMS_LOAD_FAIL_LOG = "Failed to load museums";
        public static final String NO_MUSEUMS = "There is no museums. Add new museum. (EDIT -> NEW MUSEUM)";
    }

    public static final class Exhibit {
        public static final String EXHIBITS_LOAD_FAIL_LOG = "Failed to load exponats for museum.";
        public static final String NO_EXHIBITS = "There is no exhibits for this museum. (EDIT -> NEW EXHIBIT)";
    }

    public static final class SceneManager {
        public static final String NOT_INITIALIZED = "Stage is not initialized and/or controller factory is not injected!";
    }

    public static final class Filter {
        public static final String NO_RESULTS = "There is no results for this filters!";
    }

    public static final class Auth {
        public static final String LOGIN_FAILED = "Your username or password is wrong!";
        public static final String SERVER_ERROR_CONTENT = "There is a problem with the server try again later!";
        public static final String USERNAME_TAKEN = "Username is already taken.";
        public static final String EMAIL_TAKEN = "Email is already in use.";
        public static final String EMPTY_FIELDS = "You must fill out all fields.";
        public static final String EMAIL_FORMAT = "Email format is not valid (Ex: kbobovec@algebra.hr).";
        public static final String PASSWORD_NOT_VALID = "Password must be at least 8 characters long!";
        public static final String LOGIN_SUCCEED_HEADER = "Successfully logged!";
        public static final String LOGIN_SUCCEED_CONTENT = "Welcome back %s %s!";

        public static final String REGISTER_SUCCEED_HEADER = "Successfully registered!";
        public static final String REGISTER_SUCCEED_CONTENT = "Welcome now login with your username and password!";

        public static final String LOGIN_SUCCEED_LOG = "User %s successfully logged in!";
        public static final String REGISTER_SUCCEED_LOG = "User %s successfully registered!";
        public static final String LOGIN_FAILED_LOG = "User %s failed at login!";
        public static final String USER_LOAD_FAIL_LOG = "Failed to load user during login";
    }
}
