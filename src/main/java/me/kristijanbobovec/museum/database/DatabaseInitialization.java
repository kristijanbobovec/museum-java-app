package me.kristijanbobovec.museum.database;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import me.kristijanbobovec.museum.config.ApplicationProperties;
import me.kristijanbobovec.museum.model.RoleType;
import me.kristijanbobovec.museum.util.Messages;
import me.kristijanbobovec.museum.util.SQLErrors;
import me.kristijanbobovec.museum.util.SQLQueries;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;

public class DatabaseInitialization {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitialization.class);

    private DatabaseInitialization() {}

    public static void init() throws SQLException {
        var dataSource = new SQLServerDataSource();
        dataSource.setTrustServerCertificate(true);
        dataSource.setPortNumber(ApplicationProperties.Database.getPort());
        dataSource.setServerName(ApplicationProperties.Database.getHost());
        dataSource.setUser(ApplicationProperties.Database.getUsername());
        dataSource.setPassword(ApplicationProperties.Database.getPassword());

        try (Connection conn = dataSource.getConnection()) {
            dropDatabase(dataSource);
            if (checkDatabase(conn)) return;


            createDatabaseIfNotExists(conn);

            conn.setAutoCommit(false);
            try {
                createTablesIfNotExists(conn);
                createProceduresIfNotExists(conn);
                createRoles(conn);
                createAdminAccount(conn);
                seedData(conn);
                conn.commit();

                log.info(Messages.Database.DATABASE_CREATED);
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(true);
                try {
                    dropDatabase(dataSource);
                } catch (SQLException dropEx) {
                    log.error(Messages.Database.DATABASE_FAIL_DROP, dropEx);
                }
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private static void seedData(Connection conn) throws SQLException {
        log.debug(Messages.Database.DATABASE_SEEDING);
        try (Statement statement = conn.createStatement()) {
            statement.execute(SQLQueries.USE_DATABASE.formatted(ApplicationProperties.Database.getDatabase()));
            statement.execute(SQLQueries.SEED);
        }
    }

    private static void createAdminAccount(Connection conn) throws SQLException {
        log.debug(Messages.Database.DATABASE_CREATING_ADMIN);
        try (
                CallableStatement csInsertUser = conn.prepareCall(SQLQueries.INSERT_USER);
        ) {
            csInsertUser.setString(1, "admin");
            csInsertUser.setString(2, "admin@algebra.hr");
            csInsertUser.setString(3, "User");
            csInsertUser.setString(4, "Admin");
            csInsertUser.setString(5, BCrypt.hashpw(ApplicationProperties.Administrator.getPassword(), BCrypt.gensalt()));
            csInsertUser.setInt(6, RoleType.ADMINISTRATOR.getValue());
            csInsertUser.registerOutParameter(7, Types.INTEGER);
            csInsertUser.execute();
        }
    }

    private static void createRoles(Connection conn) throws SQLException {
        log.debug(Messages.Database.DATABASE_CREATING_ROLES);
        try (
            CallableStatement csInsertRole = conn.prepareCall(SQLQueries.INSERT_ROLE);
        ) {
            for (RoleType type : RoleType.values()) {
                csInsertRole.setInt(1, type.getValue());
                csInsertRole.setString(2, type.toString());
                csInsertRole.execute();
            }
        }
    }

    private static void createProceduresIfNotExists(Connection conn) throws SQLException {
        log.debug(Messages.Database.DATABASE_CREATING_PROCEDURES);
        try(Statement statement = conn.createStatement()) {
            statement.execute(SQLQueries.USE_DATABASE.formatted(ApplicationProperties.Database.getDatabase()));
            String[] procedures = SQLQueries.PROCEDURES.split("(?im)^\\s*GO\\s*$");
            for (String procedure : procedures) {
                statement.execute(procedure);
            }
        }
    }

    private static void createTablesIfNotExists(Connection conn) throws SQLException {
        log.debug(Messages.Database.DATABASE_CREATING_TABLES);
        try(Statement statement = conn.createStatement()) {
            statement.execute(SQLQueries.USE_DATABASE.formatted(ApplicationProperties.Database.getDatabase()));
            String[] ddls = SQLQueries.DDL.split("(?im)^\\s*GO\\s*$");
            for (String ddl : ddls) {
                statement.addBatch(ddl);
            }
            statement.executeBatch();
        }
    }

    private static void createDatabaseIfNotExists(Connection conn) throws SQLException {
        log.info(Messages.Database.DATABASE_STARTED_CREATION);
        try (Statement statement = conn.createStatement()) {
            statement.execute(SQLQueries.CREATE_DATABASE.formatted(ApplicationProperties.Database.getDatabase()));
        }
    }

    private static void dropDatabase(DataSource ds) throws SQLException {
        try (
                Connection conn = ds.getConnection();
                Statement statement = conn.createStatement()) {
            String db = ApplicationProperties.Database.getDatabase();
            statement.execute(SQLQueries.DROP_DATABASE.formatted(db, db));
        }
    }

    private static boolean checkDatabase(Connection conn) throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(SQLQueries.USE_DATABASE.formatted(ApplicationProperties.Database.getDatabase()));
        } catch (SQLException e) {
            if (e.getErrorCode() == SQLErrors.DATABASE_NOT_FOUND) {
                return false;
            } else {
                throw e;
            }
        }
        return true;
    }
}
