package me.kristijanbobovec.museum.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> getAll() throws SQLException;
    Optional<T> getById(int id) throws SQLException;
    Optional<T> insert(T t) throws SQLException;
    boolean update(int id, T t) throws SQLException;
    boolean delete(int id) throws SQLException;
}
