package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.MuseumCollectionColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.CrudOperation;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionInsertDTO;
import me.kristijanbobovec.museum.model.dto.museumcollection.MuseumCollectionUpdateDTO;
import me.kristijanbobovec.museum.service.MuseumCollectionService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class MuseumCollectionController extends CrudController<MuseumCollection> {

    private static final Logger log = LoggerFactory.getLogger(MuseumCollectionController.class);
    private final MuseumCollectionService museumCollectionService;

    public MuseumCollectionController(MuseumCollectionService museumCollectionService) {
        this.museumCollectionService = museumCollectionService;
    }

    @FXML
    private TextField nameField;


    private ObservableList<MuseumCollection> collections;
    private FilteredList<MuseumCollection> filteredCollections;
    private SortedList<MuseumCollection> sortedCollections;

    @FXML
    private void initialize() {
        try {
            collections = FXCollections.observableArrayList(museumCollectionService.getAll());
            filteredCollections = new FilteredList<>(collections);
            sortedCollections = new SortedList<>(filteredCollections);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        applySearch(filteredCollections);
        applySelection(() -> nameField.setText(tableView.getSelectionModel().getSelectedItem().getName()));
    }

    private void initTableView() {
        tableView.setItems(sortedCollections);
        sortedCollections.comparatorProperty().bind(tableView.comparatorProperty());

        for (MuseumCollectionColumnFactory col : MuseumCollectionColumnFactory.values()) {
            tableView.getColumns().add(col.createColumn());
        }
    }

    @Override
    public void handleUpdate() {
        clearError();
        try {
            MuseumCollection selectedMuseumCollection = tableView.getSelectionModel().getSelectedItem();
            if (selectedMuseumCollection == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idCollection = selectedMuseumCollection.getIdCollection();

            String name = nameField.getText();
            MuseumCollectionUpdateDTO museumCollectionDTO = new MuseumCollectionUpdateDTO(name);

            MuseumCollection updatedMuseumCollection = museumCollectionService.update(idCollection, museumCollectionDTO);
            tableViewUpdate(CrudOperation.UPDATE, collections, updatedMuseumCollection);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedMuseumCollection, updatedMuseumCollection));
            log.info(Messages.CRUD.UPDATED_LOG, selectedMuseumCollection, updatedMuseumCollection, UserSession.INSTANCE.getSession().username());
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.MuseumCollection.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String name = nameField.getText();
            MuseumCollectionInsertDTO museumCollectionDTO = new MuseumCollectionInsertDTO(name);

            MuseumCollection insertedMuseumCollection = museumCollectionService.insert(museumCollectionDTO);
            tableViewUpdate(CrudOperation.INSERT, collections, insertedMuseumCollection);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedMuseumCollection.getName()));
            log.info(Messages.CRUD.INSERT_LOG, insertedMuseumCollection.getName(), UserSession.INSTANCE.getSession().username());
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.MuseumCollection.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleClear() {
        clearNameField();
        unFocusTableView();
    }

    @Override
    public void handleDelete() {
        clearError();
        try {
            MuseumCollection selectedMuseumCollection = tableView.getSelectionModel().getSelectedItem();
            if (selectedMuseumCollection == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idCollection = selectedMuseumCollection.getIdCollection();

            museumCollectionService.delete(idCollection);
            tableViewUpdate(CrudOperation.DELETE, collections, selectedMuseumCollection);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedMuseumCollection.getName()));
            log.info(Messages.CRUD.DELETED_LOG, selectedMuseumCollection.getName(), UserSession.INSTANCE.getSession().username());
        } catch (SQLException e) {
            log.error(Messages.MuseumCollection.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void clearNameField() {
        nameField.clear();
    }
}
