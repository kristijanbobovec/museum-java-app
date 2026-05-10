package me.kristijanbobovec.museum.config;

import me.kristijanbobovec.museum.util.Messages;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ApplicationProperties {
    private ApplicationProperties() {}

    private static final Properties defaultProps = new Properties();
    private static final Properties props = new Properties(defaultProps);

    static {
        defaultProps.setProperty("db.host", "localhost");
        defaultProps.setProperty("db.port", "1401");
        defaultProps.setProperty("db.database", "museum");
        defaultProps.setProperty("db.url", "jdbc:sqlserver://%s:%d;databaseName=%s;trustServerCertificate=true");

        defaultProps.setProperty("hikari.maximumPoolSize", "10");
        defaultProps.setProperty("hikari.minimumIdle", "2");
        defaultProps.setProperty("hikari.connectionTimeout", "30000");
        defaultProps.setProperty("hikari.idleTimeout", "600000");
        defaultProps.setProperty("hikari.maxLifetime", "1800000");

        try (InputStream is = ApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is == null) throw new FileNotFoundException();
            props.load(is);
        } catch (FileNotFoundException e) {
            System.out.printf(Messages.Configuration.FILE_NOT_FOUND, "application.properties");
        } catch (IOException e) {
            System.out.printf(Messages.Configuration.FILE_ERROR, "application.properties");
        }

        loadEnvVariable("DB_USERNAME");
        loadEnvVariable("DB_PASSWORD");
        loadEnvVariable("ADMIN_PASSWORD");
    }

    private static void loadEnvVariable(String key) {
        String val = Optional.ofNullable(System.getenv(key))
                .orElse("development");

        props.setProperty(key, val);
    }

    public final static class Administrator {
        public static String getPassword() {
            return props.getProperty("ADMIN_PASSWORD");
        }
    }

    public final static class Database {
        public static String getHost() { return props.getProperty("db.host"); }
        public static String getURL() { return props.getProperty("db.url"); }
        public static int getPort() { return Integer.parseInt(props.getProperty("db.port")); }
        public static String getDatabase() { return props.getProperty("db.database"); }
        public static String getUsername() {
            return props.getProperty("DB_USERNAME");
        }

        public static String getPassword() {
            return props.getProperty("DB_PASSWORD");
        }
    }


    public final static class Hikari {
        public static int getMaximumPoolSize() {
            return Integer.parseInt(props.getProperty("hikari.maximumPoolSize"));
        }

        public static int getMinimumIdle() {
            return Integer.parseInt(props.getProperty("hikari.minimumIdle"));
        }
        public static long getConnectionTimeout() {
            return Long.parseLong(props.getProperty("hikari.connectionTimeout"));
        }
        public static long getIdleTimeout() {
            return Long.parseLong(props.getProperty("hikari.idleTimeout"));
        }
        public static long getMaxLifetime() {
            return Long.parseLong(props.getProperty("hikari.maxLifetime"));
        }
    }

}
