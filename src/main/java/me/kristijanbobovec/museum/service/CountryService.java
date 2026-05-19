package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.repository.CountryRepository;

import java.sql.SQLException;
import java.util.List;

public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll() throws SQLException {
        return countryRepository.getAll();
    }
}
