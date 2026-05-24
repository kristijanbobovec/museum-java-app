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
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.CityColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.City;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.CrudOperation;
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
        applySelecton(() -> {
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
            countryComboBox.getSelectionModel().select(tableView.getSelectionModel().getSelectedItem().getCountry());
        });
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
            City selectedCity = tableView.getSelectionModel().getSelectedItem();
            if (selectedCity == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idCity = selectedCity.getIdCity();

            String name = nameField.getText();
            int countryId = Optional.ofNullable(countryComboBox.getSelectionModel().getSelectedItem())
                    .map(Country::getIdCountry)
                    .orElse(0);

            CityUpdateDTO cityDto = new CityUpdateDTO(name, countryId);
            City updatedCity = cityService.update(idCity, cityDto);

            tableViewUpdate(CrudOperation.UPDATE, cities, updatedCity);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedCity, updatedCity));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedCity, updatedCity, UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
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
            int countryId = Optional.ofNullable(countryComboBox.getSelectionModel().getSelectedItem())
                    .map(Country::getIdCountry)
                    .orElse(0);

            CityInsertDTO cityDto = new CityInsertDTO(name, countryId);
            City insertedCity = cityService.insert(cityDto);

            tableViewUpdate(CrudOperation.INSERT, cities, insertedCity);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedCity.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedCity.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.City.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
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
            City selectedCity = tableView.getSelectionModel().getSelectedItem();
            if (selectedCity == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idCity = selectedCity.getIdCity();

            cityService.delete(idCity);

            tableViewUpdate(CrudOperation.DELETE, cities, selectedCity);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedCity.getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedCity.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.City.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

}
