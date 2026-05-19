package me.kristijanbobovec.museum.repository;

import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.Museum;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CuratorRepositoryImpl implements CuratorRepository{
    @Override
    public List<Museum> getAllWithDetails() throws SQLException {
        return List.of();
    }

    @Override
    public Optional<Museum> getByIdWithDetails(int id) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Curator mapRow(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public void mapForInsert(CallableStatement cs, Curator item) throws SQLException {

    }

    @Override
    public void mapForUpdate(CallableStatement cs, int id, Curator originalItem, Curator toUpdate) throws SQLException {

    }
}
