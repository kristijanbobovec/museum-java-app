package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.factory.StatementFactory;
import me.kristijanbobovec.museum.manager.DatabaseManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    public T mapRow(ResultSet rs) throws SQLException;
    public void mapForInsert(CallableStatement cs, T item) throws SQLException;
    public void mapForUpdate(CallableStatement cs, int id, T originalItem, T toUpdate) throws SQLException;

    default List<T> getAll() throws SQLException {
        List<T> listOfElements = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "getAll"))) {

            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                listOfElements.add(mapRow(rs));
            }

        }

        return listOfElements;
    }

    default Optional<T> getById(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "getById"));
        ) {
            cs.setInt(1, id);

            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    default Optional<T> insert(T t) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "insert"));
        ) {
            mapForInsert(cs, t);

            cs.execute();
            int id = cs.getInt("IDInserted");
            return getById(id);
        }
    }

    default boolean update(int id, T toUpdate) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "update"));
        ) {
            Optional<T> originalItem = getById(id);
            if (originalItem.isEmpty()) return false;

            mapForUpdate(cs, id, originalItem.get(), toUpdate);
            return cs.executeUpdate() > 0;
        }
    }

    default boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement cs = conn.prepareCall(StatementFactory.INSTANCE.getStatement(this.getClass(), "delete"));) {

            cs.setInt(1, id);

            return cs.executeUpdate() > 0;
        }
    }
}
