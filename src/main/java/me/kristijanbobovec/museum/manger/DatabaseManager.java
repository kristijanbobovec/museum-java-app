package me.kristijanbobovec.museum.manger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.kristijanbobovec.museum.config.ApplicationProperties;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    private DatabaseManager() {}

    static {
        config.setJdbcUrl(ApplicationProperties.Database.getURL()
                .formatted(ApplicationProperties.Database.getHost(),
                        ApplicationProperties.Database.getPort(),
                        ApplicationProperties.Database.getDatabase()));

        config.setUsername(ApplicationProperties.Database.getUsername());
        config.setPassword(ApplicationProperties.Database.getPassword());

        config.setMaximumPoolSize(ApplicationProperties.Hikari.getMaximumPoolSize());
        config.setMinimumIdle(ApplicationProperties.Hikari.getMinimumIdle());
        config.setConnectionTimeout(ApplicationProperties.Hikari.getConnectionTimeout());
        config.setIdleTimeout(ApplicationProperties.Hikari.getIdleTimeout());
        config.setMaxLifetime(ApplicationProperties.Hikari.getMaxLifetime());

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
