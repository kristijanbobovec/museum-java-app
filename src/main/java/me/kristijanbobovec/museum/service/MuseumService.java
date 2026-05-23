package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.dto.museum.MuseumInsertDTO;
import me.kristijanbobovec.museum.model.dto.museum.MuseumUpdateDTO;
import me.kristijanbobovec.museum.repository.MuseumRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MuseumService implements Service<Museum, MuseumInsertDTO, MuseumUpdateDTO> {
    private final MuseumRepository museumRepository;

    public MuseumService(MuseumRepository museumRepository) {
        this.museumRepository = museumRepository;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));

        return museumRepository.delete(id);
    }


    @Override
    public List<Museum> getAll() throws SQLException {
        return museumRepository.getAll();
    }

    @Override
    public Museum insert(MuseumInsertDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.address() == null || dto.address().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("address"));
        if (dto.establishmentYear() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("establishment year"));
        if (dto.cityId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("city id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Museum> museumOpt = museumRepository.insert(dto.mapToMuseum());

        if (museumOpt.isPresent()) return museumOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", dto));
    }

    @Override
    public Museum update(int id, MuseumUpdateDTO dto) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));

        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.address() == null || dto.address().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("address"));
        if (dto.establishmentYear() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("establishment year"));
        if (dto.cityId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("city id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Museum> museumOpt = museumRepository.update(id, dto.mapToMuseum());


        if (museumOpt.isPresent()) return museumOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", dto));
    }


}
