package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.repository.ExhibitRepository;

import java.sql.SQLException;
import java.util.List;

public class ExhibitService {
    private final ExhibitRepository exhibitRepository;

    public ExhibitService(ExhibitRepository exhibitRepository) {
        this.exhibitRepository = exhibitRepository;
    }

    public List<Exhibit> getAllByMuseumId(int museumId) throws SQLException {
        return exhibitRepository.getByMuseumId(museumId);
    }


}
