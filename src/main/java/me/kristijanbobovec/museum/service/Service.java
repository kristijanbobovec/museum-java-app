package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.dto.Dto;

import java.sql.SQLException;
import java.util.List;

public interface Service<T, D extends Dto, C extends Dto> {
    boolean delete(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    T insert(D dto) throws SQLException;
    T update(int id, C dto) throws SQLException;
}
