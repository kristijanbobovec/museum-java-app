package me.kristijanbobovec.museum.service;

import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.dto.curator.CuratorInsertDTO;
import me.kristijanbobovec.museum.model.dto.curator.CuratorUpdateDTO;
import me.kristijanbobovec.museum.repository.CuratorRepository;
import me.kristijanbobovec.museum.util.Messages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuratorService implements Service<Curator, CuratorInsertDTO, CuratorUpdateDTO> {
    private final CuratorRepository curatorRepository;

    public CuratorService(CuratorRepository curatorRepository) {
        this.curatorRepository = curatorRepository;
    }


    @Override
    public boolean delete(int id) throws SQLException {
        return curatorRepository.delete(id);
    }

    @Override
    public List<Curator> getAll() throws SQLException {
        return curatorRepository.getAll();
    }

    @Override
    public Curator insert(CuratorInsertDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.firstName() == null || dto.firstName().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("firstname"));
        if (dto.lastName() == null || dto.lastName().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("lastname"));
        if (dto.museumId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("museum id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Curator> curatorOpt = curatorRepository.insert(dto.mapToCurator());

        if (curatorOpt.isPresent()) return curatorOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("insert", dto));
    }

    @Override
    public Curator update(int id, CuratorUpdateDTO dto) throws SQLException {
        List<String> errors = new ArrayList<>();
        if (dto.firstName() == null || dto.firstName().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("firstname"));
        if (dto.lastName() == null || dto.lastName().isEmpty()) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("lastname"));
        if (dto.museumId() == 0) errors.add(Messages.CRUDValidation.FIELD_EMPTY.formatted("museum id"));
        if (!errors.isEmpty()) throw new OmitingRequiredFieldsException(errors);

        Optional<Curator> curatorOpt = curatorRepository.update(id, dto.mapToCurator());

        if (curatorOpt.isPresent()) return curatorOpt.get();
        throw new DatabaseFailedToException(Messages.Database.DATABASE_FAILEDTO.formatted("update", dto));
    }
}
