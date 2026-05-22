package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.dto.country.CountryInsertDTO;
import me.kristijanbobovec.museum.model.dto.country.CountryUpdateDTO;
import me.kristijanbobovec.museum.repository.CountryRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryService implements Service<Country, CountryInsertDTO, CountryUpdateDTO> {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll() throws SQLException {
        return countryRepository.getAll();
    }

    public boolean delete(int id) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));

        return countryRepository.delete(id);
    }

    public Country insert(CountryInsertDTO countryDto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (countryDto.name() == null || countryDto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Country> countryOpt = countryRepository.insert(countryDto.mapToCountry());

        if (countryOpt.isPresent()) return countryOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", countryDto));
    }

    public Country update(int id, CountryUpdateDTO countryDto) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));

        List<String> errors = new ArrayList<>();
        if (countryDto.name() == null || countryDto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Country> countryOpt = countryRepository.update(id, countryDto.mapToCountry());

        if (countryOpt.isPresent()) return countryOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", countryDto));
    }
}
