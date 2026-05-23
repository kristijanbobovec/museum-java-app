package me.kristijanbobovec.museum.repository;

import java.sql.SQLException;

public interface ImagePathRepository {
    int countReferences(String path) throws SQLException;
}
