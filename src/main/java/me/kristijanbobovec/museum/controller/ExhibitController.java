package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.ExhibitDetailsColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.*;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitInsertDTO;
import me.kristijanbobovec.museum.model.dto.exhibit.ExhibitUpdateDTO;
import me.kristijanbobovec.museum.service.ExhibitService;
import me.kristijanbobovec.museum.service.ImagePathService;
import me.kristijanbobovec.museum.service.MuseumCollectionService;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.ImageHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ExhibitController extends CrudController<Exhibit> {

    private static final Logger log = LoggerFactory.getLogger(ExhibitController.class);

    private final ExhibitService exhibitService;
    private final MuseumService museumService;
    private final MuseumCollectionService museumCollectionService;
    private final ImagePathService imagePathService;

    private ObservableList<Museum> museums;
    private ObservableList<Exhibit> exhibits;
    private ObservableList<MuseumCollection> collections;
    private FilteredList<Exhibit> filteredExhibits;
    private SortedList<Exhibit> sortedExhibits;

    private String originalPath;

    @FXML
    private TextField imagePathField;

    @FXML
    private ComboBox<MuseumCollection> collectionComboBox;
    @FXML
    private ComboBox<Museum> museumComboBox;


    @FXML
    private TextField nameField;
    @FXML
    private TextField yearField;
    @FXML
    private TextArea descriptionField;




    public ExhibitController(ExhibitService exhibitService, MuseumService museumService, MuseumCollectionService museumCollectionService, ImagePathService imagePathService) {
        this.exhibitService = exhibitService;
        this.museumService = museumService;
        this.museumCollectionService = museumCollectionService;
        this.imagePathService = imagePathService;
    }

    @FXML
    private void initialize() {
        try {
            exhibits = FXCollections.observableArrayList(exhibitService.getAll());
            museums = FXCollections.observableArrayList(museumService.getAll());
            collections = FXCollections.observableArrayList(museumCollectionService.getAll());
            filteredExhibits = new FilteredList<>(exhibits);
            sortedExhibits = new SortedList<>(filteredExhibits);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        initCombobox();
        applySearch(filteredExhibits);
        applySelecton(() -> {
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
            imagePathField.setText(tableView.getSelectionModel().getSelectedItem().getImagePath());
            yearField.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getCreationYear()));
            descriptionField.setText(tableView.getSelectionModel().getSelectedItem().getDescription());
            collectionComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getMuseumCollection());
            museumComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getMuseum());
        });
    }

    private void initCombobox() {
        comboBoxFormat(museumComboBox, Museum::getName);
        comboBoxSetItems(museumComboBox, museums);

        comboBoxFormat(collectionComboBox, MuseumCollection::getName);
        comboBoxSetItems(collectionComboBox, collections);
    }

    private void initTableView() {
        tableView.setItems(sortedExhibits);
        sortedExhibits.comparatorProperty().bind(tableView.comparatorProperty());

        for (ExhibitDetailsColumnFactory col : ExhibitDetailsColumnFactory.values()) {
            tableView.getColumns().add(col.createColumn());
        }
    }

    @Override
    public void handleUpdate() {
        clearError();
        try {
            Exhibit selectedExhibit = tableView.getSelectionModel().getSelectedItem();
            if (selectedExhibit == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idExhibit = selectedExhibit.getIdExhibit();

            String name = nameField.getText();
            String imagePath = imagePathField.getText();
            int creationYear = NumberUtils.toInt(yearField.getText(), 0);
            String desc = descriptionField.getText();

            int museumId = Optional.ofNullable(museumComboBox.getSelectionModel().getSelectedItem())
                    .map(Museum::getIdMuseum)
                    .orElse(0);

            // Optional
            Integer museumCollectionId = Optional.ofNullable(collectionComboBox.getSelectionModel().getSelectedItem())
                    .map(MuseumCollection::getIdCollection)
                    .orElse(null);

            ExhibitUpdateDTO exhibitDto = new ExhibitUpdateDTO(name, desc, creationYear, imagePath, museumId, museumCollectionId);
            System.out.println(exhibitDto);

            Exhibit updatedExhibit = exhibitService.update(idExhibit, exhibitDto);

            String oldPath = selectedExhibit.getImagePath();
            imagePathService.update(oldPath, new ImagePath(originalPath, imagePath));
            originalPath = null;

            tableViewUpdate(CrudOperation.UPDATE, exhibits, updatedExhibit);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedExhibit, updatedExhibit));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedExhibit, updatedExhibit, UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Exhibit.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        } catch (IOException e) {
            log.error(Messages.Image.FAILED_TO_MANIPULATE.formatted("update"), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String name = nameField.getText();
            String imagePath = imagePathField.getText();
            int creationYear = NumberUtils.toInt(yearField.getText(), 0);
            String desc = descriptionField.getText();

            int museumId = Optional.ofNullable(museumComboBox.getSelectionModel().getSelectedItem())
                    .map(Museum::getIdMuseum)
                    .orElse(0);

            // Optional
            Integer museumCollectionId = Optional.ofNullable(collectionComboBox.getSelectionModel().getSelectedItem())
                    .map(MuseumCollection::getIdCollection)
                    .orElse(null);

            ExhibitInsertDTO exhibitDto = new ExhibitInsertDTO(name, desc, creationYear, imagePath, museumId, museumCollectionId);

            Exhibit insertedExhibit = exhibitService.insert(exhibitDto);

            imagePathService.create(new ImagePath(originalPath, imagePath));
            originalPath = null;

            tableViewUpdate(CrudOperation.INSERT, exhibits, insertedExhibit);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedExhibit.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedExhibit.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Exhibit.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        } catch (IOException e) {
            log.error(Messages.Image.FAILED_TO_MANIPULATE.formatted("create"), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleDelete() {
        clearError();
        try {
            Exhibit selectedExhibit = tableView.getSelectionModel().getSelectedItem();
            if (selectedExhibit == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idExhibit = selectedExhibit.getIdExhibit();

            exhibitService.delete(idExhibit);

            String imagePath = selectedExhibit.getImagePath();
            imagePathService.remove(imagePath);

            tableViewUpdate(CrudOperation.DELETE, exhibits, selectedExhibit);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedExhibit.getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedExhibit.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (SQLException e) {
            log.error(Messages.Exhibit.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        } catch (IOException e) {
            log.error(Messages.Image.FAILED_TO_MANIPULATE.formatted("delete"), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleClear() {
        clearNameField();
        clearYearField();
        clearDescriptionField();
        clearImagePathField();
        clearMuseumSelection();
        clearCollecitonSelection();
        clearOriginalPath();
        unFocusTableView();
    }

    private void clearMuseumSelection() {
        museumComboBox.getSelectionModel().clearSelection();
    }
    private void clearCollecitonSelection() {
        collectionComboBox.getSelectionModel().clearSelection();
    }

    private void clearNameField() {
        nameField.clear();
    }

    private void clearImagePathField() {
        imagePathField.clear();
    }

    private void clearDescriptionField() {
        descriptionField.clear();
    }

    private void clearYearField() {
        yearField.clear();
    }

    private void clearOriginalPath() {
        originalPath = null;
    }

    @FXML
    private void handleBrowse() {
        ImageHelper.handleBrowse(
                this::setOriginalPath,
                this::setImagePathField
        );
    }

    public void setImagePathField(String path) {
        imagePathField.setText(path);
    }

    public void setOriginalPath(String path) {
        originalPath = path;
    }
}
