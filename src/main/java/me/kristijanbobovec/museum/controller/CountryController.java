package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.ItemNotSelectedException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.CountryColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.dto.country.CountryInsertDTO;
import me.kristijanbobovec.museum.model.dto.country.CountryUpdateDTO;
import me.kristijanbobovec.museum.service.CountryService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;


public class CountryController implements CrudController {

    private static final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @FXML
    private TableView<Country> tableView;

    @FXML
    private TextField nameField;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField searchField;


    private ObservableList<Country> countries;
    private FilteredList<Country> filteredCounties;
    private SortedList<Country> sortedCountries;

    @FXML
    private void initialize() {
        try {
            countries = FXCollections.observableArrayList(countryService.getAll());
            filteredCounties = new FilteredList<>(countries);
            sortedCountries = new SortedList<>(filteredCounties);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Country.COUNTRIES_LOAD_FAIL_LOG, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        applySearch();
        applySelecton();
    }

    private void initTableView() {
        tableView.setItems(sortedCountries);
        sortedCountries.comparatorProperty().bind(tableView.comparatorProperty());

        for (CountryColumnFactory col : CountryColumnFactory.values()) {
            tableView.getColumns().add(col.createColumn());
        }
    }

    @Override
    public void handleUpdate() {
        clearError();
        try {
            Optional<Country> selectedCountry = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedCountry.map(Country::getIdCountry).orElse(-1);

            String name = nameField.getText();
            CountryUpdateDTO countryDto = new CountryUpdateDTO(name);

            Country updatedCounty = countryService.update(id, countryDto);
            handleClear();
            updateCountyInList(updatedCounty);
            focusTableViewItem(updatedCounty);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedCountry, updatedCounty));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedCountry, updatedCounty, UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Country.COUNTRY_UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String name = nameField.getText();
            CountryInsertDTO countryDto = new CountryInsertDTO(name);

            Country insertedCountry = countryService.insert(countryDto);
            addCountryToList(insertedCountry);
            handleClear();
            focusTableViewItem(insertedCountry);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedCountry.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedCountry.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException | OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        }  catch (SQLException e) {
            log.error(Messages.Country.COUNTRY_INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
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
            Optional<Country> selectedCountry = Optional.ofNullable(tableView.getSelectionModel().getSelectedItem());
            int id = selectedCountry.map(Country::getIdCountry).orElse(-1);

            countryService.delete(id);
            removeCountryFromList(selectedCountry.get());
            handleClear();
            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedCountry.get().getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedCountry.get().getName(), UserSession.INSTANCE.getSession().username()));
        } catch (ItemNotSelectedException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Country.COUNTRY_DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }

    }

    private void applySelecton() {
        tableView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(tableView.getSelectionModel().getSelectedItem() == null) return;
            nameField.setText(tableView.getSelectionModel().getSelectedItem().getName());
        });
    }

    private void updateCountyInList(Country country) {
        removeCountryFromList(country);
        addCountryToList(country);
    }

    private void unFocusTableView() {
        tableView.getSelectionModel().clearSelection();
    }

    private void focusTableViewItem(Country country) {
        tableView.getSelectionModel().select(country);
    }

    private void showError(String message) {
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        errorLabel.setText(message);
    }

    private void clearError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private void removeCountryFromList(Country country) {
        countries.remove(country);
    }

    private void addCountryToList(Country country) {
        countries.add(country);
    }

    private void clearNameField() {
        nameField.clear();
    }

    private void applySearch() {
        searchField.textProperty().addListener((obs, oldval, newval) -> {
            filteredCounties.setPredicate(c -> {
                if (newval == null || newval.isEmpty()) return true;
                return c.getName().toLowerCase().contains(newval.toLowerCase());
            });
        });
    }
}
