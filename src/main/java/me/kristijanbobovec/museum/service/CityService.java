package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.repository.CityRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.List;

public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllWithDetails() throws SQLException {
        return cityRepository.getAllWithDetails();
    }

    public boolean delete(int id) throws SQLException {
        if (id == -1) throw new ItemNotSelectedException(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));

        return cityRepository.delete(id);
    }
}
