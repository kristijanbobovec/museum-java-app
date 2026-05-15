package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Exhibit;

import java.sql.SQLException;
import java.util.List;

public interface ExhibitRepository extends Repository<Exhibit> {
    List<Exhibit> getByMuseumId(int museumId) throws SQLException;
}
