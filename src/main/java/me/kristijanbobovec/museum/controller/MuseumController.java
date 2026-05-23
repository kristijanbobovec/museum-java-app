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
import javafx.stage.FileChooser;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.MuseumColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.dto.museum.MuseumInsertDTO;
import me.kristijanbobovec.museum.model.dto.museum.MuseumUpdateDTO;
import me.kristijanbobovec.museum.service.CityService;
import me.kristijanbobovec.museum.service.ImagePathService;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.ImageHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Optional;

public class MuseumController extends CrudController<Museum> {

    private static final Logger log = LoggerFactory.getLogger(MuseumController.class);
    private final MuseumService museumService;
    private final CityService cityService;
    private final ImagePathService imagePathService;

    ObservableList<Museum> museums;
    FilteredList<Museum> filteredMuseums;
    SortedList<Museum> sortedMuseums;
    ObservableList<City> cities;

    private String originalImagePath;

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
        applySelection();
    }

    private void initCombobox() {
        comboBoxFormat(cityComboBox, City::getName);
        comboBoxSetItems(cityComboBox, cities);
    }

    private void applySelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(tableView.getSelectionModel().getSelectedItem() == null) return;
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
            addressField.setText(tableView.getSelectionModel().getSelectedItem().getAdress());
            yearField.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getEstablishmentYear()));
            descriptionField.setText(String.valueOf(tableView.getSelectionModel().getSelectedItem().getDescription()));
            imagePathField.setText(tableView.getSelectionModel().getSelectedItem().getImagePath());
            cityComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getCity());
        });
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
        clearCountrySelection();
        unFocusTableView();
    }

    private void clearCountrySelection() {
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

    @Override
    public void handleUpdate() {
        clearError();
        try {
            Optional<Museum> selectedMuseum = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedMuseum.map(Museum::getIdMuseum).orElse(-1);

            String name = nameField.getText();
            String address = addressField.getText();
            String imagePath = imagePathField.getText();

            int establishmentYear;
            try {
                establishmentYear = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                establishmentYear = 0;
            }

            String desc = descriptionField.getText();
            City selectedCity = cityComboBox.getSelectionModel().getSelectedItem();
            int cityId = selectedCity == null ? 0 : selectedCity.getIdCity();

            MuseumUpdateDTO museumDto = new MuseumUpdateDTO(name, address, establishmentYear, desc, imagePath, cityId);

            Museum updatedMuseum = museumService.update(id, museumDto);
            handleClear();
            updateInObservableList(museums, updatedMuseum);
            focusTableViewItem(updatedMuseum);


            // if selectedMuseum does not have imagePath just check if the updated museum has if it has just createImageIfNotExists
            // if selectedMuseum has imagePath check if imagePath is changed if it is check if there are references to that imagePath if not delete image
            // TODO IMPLEMENT IMAGE LOGIC



            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedMuseum, updatedMuseum));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedMuseum, updatedMuseum, UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Museum.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
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

            int establishmentYear;
            try {
                establishmentYear = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException e) {
                establishmentYear = 0;
            }

            String desc = descriptionField.getText();
            City selectedCity = cityComboBox.getSelectionModel().getSelectedItem();
            int cityId = selectedCity == null ? 0 : selectedCity.getIdCity();

            MuseumInsertDTO museumDto = new MuseumInsertDTO(name, address, establishmentYear, desc, imagePath, cityId);

            Museum insertedMuseum = museumService.insert(museumDto);
            addToObservableList(museums, insertedMuseum);
            handleClear();
            focusTableViewItem(insertedMuseum);

            // If originalPath and otherPath are not null and not empty!
            // Original Path of image + createIfNotExists
            // Copy image if not exists into the assets/images
            // TODO IMPLEMENT IMAGE LOGIC

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedMuseum.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedMuseum.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Museum.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleDelete() {
        clearError();
        try {
            Optional<Museum> selectedMuseum = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedMuseum.map(Museum::getIdMuseum).orElse(-1);

            museumService.delete(id);
            removeFromObservableList(museums, selectedMuseum.get());
            handleClear();

            // TODO IMPLEMENT IMAGE LOGIC

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedMuseum.get().getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedMuseum.get().getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Museum.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All images", "*.png", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        fileChooser.setTitle("Choose the image");

        Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(nameField.getParent().getScene().getWindow()));

        file.ifPresent(f -> {
            Path path = f.toPath();
            originalImagePath = path.toString();
            imagePathField.setText(ImageHelper.buildStringURL(path.getFileName().toString()));
        });
    }
}
