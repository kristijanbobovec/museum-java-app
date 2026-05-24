package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionInsertDTO;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionUpdateDTO;
import me.kristijanbobovec.museum.repository.MuseumCollectionRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MuseumCollectionService implements Service<MuseumCollection, MuseumCollectionInsertDTO, MuseumCollectionUpdateDTO> {

    private final MuseumCollectionRepository museumCollectionRepository;

    public MuseumCollectionService(MuseumCollectionRepository museumCollectionRepository) {
        this.museumCollectionRepository = museumCollectionRepository;
    }


    public List<MuseumCollection> getByMuseumId(int museumId) throws SQLException {
        return museumCollectionRepository.getByMuseumId(museumId);
    }

    public Map<Integer, List<MuseumCollection>> getAllGroupedByMuseum(List<Museum> museums) throws SQLException {
        Map<Integer, List<MuseumCollection>> map = new HashMap<>();
        for (Museum museum : museums) {
            map.put(museum.getIdMuseum(), museumCollectionRepository.getByMuseumId(museum.getIdMuseum()));
        }
        return map;
    }


    @Override
    public boolean delete(int id) throws SQLException {
        return museumCollectionRepository.delete(id);
    }

    @Override
    public List<MuseumCollection> getAll() throws SQLException {
        return museumCollectionRepository.getAll();
    }

    @Override
    public MuseumCollection insert(MuseumCollectionInsertDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<MuseumCollection> museumCollectionOpt = museumCollectionRepository.insert(dto.mapToMuseumCollection());

        if (museumCollectionOpt.isPresent()) return museumCollectionOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", dto));
    }

    @Override
    public MuseumCollection update(int id, MuseumCollectionUpdateDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<MuseumCollection> museumCollectionOpt = museumCollectionRepository.update(id, dto.mapToMuseumCollection());

        if (museumCollectionOpt.isPresent()) return museumCollectionOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", dto));
    }
}
