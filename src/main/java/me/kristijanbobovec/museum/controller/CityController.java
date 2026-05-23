package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.CityColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.dto.city.CityInsertDTO;
import me.kristijanbobovec.museum.model.dto.city.CityUpdateDTO;
import me.kristijanbobovec.museum.service.CityService;
import me.kristijanbobovec.museum.service.CountryService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;


public class CityController extends CrudController<City> {

    private static final Logger log = LoggerFactory.getLogger(CityController.class);
    private final CountryService countryService;
    private final CityService cityService;

    private ObservableList<Country> countries;
    private ObservableList<City> cities;
    private FilteredList<City> filteredCities;
    private SortedList<City> sortedCities;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private TextField nameField;

    public CityController(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @FXML
    private void initialize() {
        try {
            countries = FXCollections.observableArrayList(countryService.getAll());
            cities = FXCollections.observableArrayList(cityService.getAll());
            filteredCities = new FilteredList<>(cities);
            sortedCities = new SortedList<>(filteredCities);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }


    private void initView() {
        initTableView();
        initCombobox();
        applySearch(filteredCities);
        applySelecton();
    }

    private void initCombobox() {
        comboBoxFormat(countryComboBox, Country::getName);
        comboBoxSetItems(countryComboBox, countries);
    }

    private void initTableView() {
        tableView.setItems(sortedCities);
        sortedCities.comparatorProperty().bind(tableView.comparatorProperty());

        for (CityColumnFactory col : CityColumnFactory.values()) {
            tableView.getColumns().add(col.createColumn());
        }
    }

    @Override
    public void handleUpdate() {
        clearError();
        try {
            Optional<City> selectedCity = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedCity.map(City::getIdCity).orElse(-1);

            String name = nameField.getText();
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            int countryId = selectedCountry == null ? 0 : selectedCountry.getIdCountry();
            CityUpdateDTO cityDto = new CityUpdateDTO(name, countryId);

            City updatedCity = cityService.update(id, cityDto);
            handleClear();
            updateInObservableList(cities, updatedCity);
            focusTableViewItem(updatedCity);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedCity, updatedCity));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedCity, updatedCity, UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.City.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String name = nameField.getText();
            Country selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
            int countryId = selectedCountry == null ? 0 : selectedCountry.getIdCountry();
            CityInsertDTO cityDto = new CityInsertDTO(name, countryId);

            City insertedCity = cityService.insert(cityDto);
            addToObservableList(cities, insertedCity);
            handleClear();
            focusTableViewItem(insertedCity);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedCity.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedCity.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        }  catch (SQLException e) {
            log.error(Messages.City.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void applySelecton() {
        tableView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(tableView.getSelectionModel().getSelectedItem() == null) return;
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
            countryComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getCountry());
        });
    }

    @Override
    public void handleClear() {
        clearNameField();
        clearCountrySelection();
        unFocusTableView();
    }

    private void clearCountrySelection() {
        countryComboBox.getSelectionModel().clearSelection();
    }

    private void clearNameField() {
        nameField.clear();
    }

    @Override
    public void handleDelete() {
        clearError();
        try {
            Optional<City> selectedCity = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedCity.map(City::getIdCity).orElse(-1);

            cityService.delete(id);
            removeFromObservableList(cities, selectedCity.get());
            handleClear();

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedCity.get().getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedCity.get().getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.City.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }
}
