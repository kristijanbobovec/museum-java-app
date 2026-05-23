package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.dto.city.CityInsertDTO;
import me.kristijanbobovec.museum.model.dto.city.CityUpdateDTO;
import me.kristijanbobovec.museum.repository.CityRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityService implements Service<City, CityInsertDTO, CityUpdateDTO> {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));

        return cityRepository.delete(id);
    }

    @Override
    public List<City> getAll() throws SQLException {
        return cityRepository.getAll();
    }

    @Override
    public City insert(CityInsertDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.countryID() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("country id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<City> cityOpt = cityRepository.insert(dto.mapToCity());

        if (cityOpt.isPresent()) return cityOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", dto));
    }

    @Override
    public City update(int id, CityUpdateDTO dto) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));

        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.countryID() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("country id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<City> cityOpt = cityRepository.update(id, dto.mapToCity());

        if (cityOpt.isPresent()) return cityOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", dto));
    }

}
