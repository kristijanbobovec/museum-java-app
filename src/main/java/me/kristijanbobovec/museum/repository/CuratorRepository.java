package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Curator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CuratorRepository extends Repository<Curator> {
    List<Curator> getAllWithDetails() throws SQLException;
    Optional<Curator> getByIdWithDetails(int id) throws SQLException;
}
