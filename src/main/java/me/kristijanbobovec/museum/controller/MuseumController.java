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
import me.kristijanbobovec.museum.factory.column.MuseumColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.CrudOperation;
import me.kristijanbobovec.museum.model.ImagePath;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.dto.museum.MuseumInsertDTO;
import me.kristijanbobovec.museum.model.dto.museum.MuseumUpdateDTO;
import me.kristijanbobovec.museum.service.CityService;
import me.kristijanbobovec.museum.service.ImagePathService;
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

public class MuseumController extends CrudController<Museum> {

    private static final Logger log = LoggerFactory.getLogger(MuseumController.class);
    private final MuseumService museumService;
    private final CityService cityService;
    private final ImagePathService imagePathService;

    private ObservableList<Museum> museums;
    private FilteredList<Museum> filteredMuseums;
    private SortedList<Museum> sortedMuseums;
    private ObservableList<City> cities;

    private String originalPath;

    @FXML
    private ComboBox<City> cityComboBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField yearField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField imagePathField;


    public MuseumController(MuseumService museumService, CityService cityService, ImagePathService imagePathService) {
        this.museumService = museumService;
        this.cityService = cityService;
        this.imagePathService = imagePathService;
    }

    @FXML
    private void initialize() {
        try {
            museums = FXCollections.observableArrayList(museumService.getAll());
            cities = FXCollections.observableArrayList(cityService.getAll());
            filteredMuseums = new FilteredList<>(museums);
            sortedMuseums = new SortedList<>(filteredMuseums);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        initCombobox();
        applySearch(filteredMuseums);
        applySelection(() -> {
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
            addressField.setText(tableView.getSelectionModel().getSelectedItem().getAdress());
            yearField.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getEstablishmentYear()));
            descriptionField.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getDescription()));
            imagePathField.setText(tableView.getSelectionModel().getSelectedItem().getImagePath());
            cityComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getCity());
        });
    }

    private void initCombobox() {
        comboBoxFormat(cityComboBox, City::getName);
        comboBoxSetItems(cityComboBox, cities);
    }

    private void initTableView() {
        tableView.setItems(sortedMuseums);
        sortedMuseums.comparatorProperty().bind(tableView.comparatorProperty());

        for (MuseumColumnFactory col : MuseumColumnFactory.values()) {
            tableView.getColumns().add(col.createColumn());
        }
    }

    @Override
    public void handleClear() {
        clearNameField();
        clearAddressField();
        clearYearField();
        clearDescriptionField();
        clearImagePathField();
        clearCitySelection();
        clearOriginalPath();
        unFocusTableView();
    }

    private void clearCitySelection() {
        cityComboBox.getSelectionModel().clearSelection();
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

    private void clearAddressField() {
        addressField.clear();
    }

    private void clearOriginalPath() {
        originalPath = null;
    }

    @Override
    public void handleUpdate() {
        clearError();
        try {
            Museum selectedMuseum = tableView.getSelectionModel().getSelectedItem();
            if (selectedMuseum == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idMuseum = selectedMuseum.getIdMuseum();

            String name = nameField.getText();
            String address = addressField.getText();
            String imagePath = imagePathField.getText();
            int establishmentYear = NumberUtils.toInt(yearField.getText(), 0);
            String desc = descriptionField.getText();
            int cityId = Optional.ofNullable(cityComboBox.getSelectionModel().getSelectedItem())
                    .map(City::getIdCity)
                    .orElse(0);

            MuseumUpdateDTO museumDto = new MuseumUpdateDTO(name, address, establishmentYear, desc, imagePath, cityId);

            Museum updatedMuseum = museumService.update(idMuseum, museumDto);

            String oldPath = selectedMuseum.getImagePath();
            imagePathService.update(oldPath, new ImagePath(originalPath, imagePath));
            originalPath = null;

            tableViewUpdate(CrudOperation.UPDATE, museums, updatedMuseum);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedMuseum, updatedMuseum));
            log.info(Messages.CRUD.UPDATED_LOG, selectedMuseum, updatedMuseum, UserSession.INSTANCE.getSession().username());
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Museum.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
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
            String address = addressField.getText();
            String imagePath = imagePathField.getText();
            int establishmentYear = NumberUtils.toInt(yearField.getText(), 0);
            String desc = descriptionField.getText();
            int cityId = Optional.ofNullable(cityComboBox.getSelectionModel().getSelectedItem())
                    .map(City::getIdCity)
                    .orElse(0);

            MuseumInsertDTO museumDto = new MuseumInsertDTO(name, address, establishmentYear, desc, imagePath, cityId);

            Museum insertedMuseum = museumService.insert(museumDto);

            imagePathService.create(new ImagePath(originalPath, imagePath));
            originalPath = null;

            tableViewUpdate(CrudOperation.INSERT, museums, insertedMuseum);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedMuseum.getName()));
            log.info(Messages.CRUD.INSERT_LOG, insertedMuseum.getName(), UserSession.INSTANCE.getSession().username());
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Museum.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
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
            Museum selectedMuseum = tableView.getSelectionModel().getSelectedItem();
            if (selectedMuseum == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idMuseum = selectedMuseum.getIdMuseum();

            museumService.delete(idMuseum);

            String imagePath = selectedMuseum.getImagePath();
            imagePathService.remove(imagePath);

            tableViewUpdate(CrudOperation.DELETE, museums, selectedMuseum);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedMuseum.getName()));
            log.info(Messages.CRUD.DELETED_LOG, selectedMuseum.getName(), UserSession.INSTANCE.getSession().username());
        } catch (SQLException e) {
            log.error(Messages.Museum.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        } catch (IOException e) {
            log.error(Messages.Image.FAILED_TO_MANIPULATE.formatted("delete"), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
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
