package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitInsertDTO;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitUpdateDTO;
import me.kristijanbobovec.museum.repository.ExhibitRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExhibitService implements Service<Exhibit, ExhibitInsertDTO, ExhibitUpdateDTO> {
    private final ExhibitRepository exhibitRepository;

    public ExhibitService(ExhibitRepository exhibitRepository) {
        this.exhibitRepository = exhibitRepository;
    }

    public List<Exhibit> getAllByMuseumId(int museumId) throws SQLException {
        return exhibitRepository.getByMuseumId(museumId);
    }


    @Override
    public boolean delete(int id) throws SQLException {
        return exhibitRepository.delete(id);
    }

    @Override
    public List<Exhibit> getAll() throws SQLException {
        return exhibitRepository.getAll();
    }

    @Override
    public Exhibit insert(ExhibitInsertDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.museumId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("museum id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Exhibit> exhibitOpt = exhibitRepository.insert(dto.mapToExhibit());

        if (exhibitOpt.isPresent()) return exhibitOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", dto));
    }

    @Override
    public Exhibit update(int id, ExhibitUpdateDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.name() == null || dto.name().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("name"));
        if (dto.museumId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("museum id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Exhibit> exhibitOpt = exhibitRepository.update(id, dto.mapToExhibit());

        if (exhibitOpt.isPresent()) return exhibitOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", dto));
    }
}
