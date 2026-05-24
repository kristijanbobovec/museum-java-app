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
import me.kristijanbobovec.museum.factory.column.CountryColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.model.CrudOperation;
import me.kristijanbobovec.museum.model.dto.country.CountryInsertDTO;
import me.kristijanbobovec.museum.model.dto.country.CountryUpdateDTO;
import me.kristijanbobovec.museum.service.CountryService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class CountryController extends CrudController<Country> {

    private static final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @FXML
    private TextField nameField;


    private ObservableList<Country> countries;
    private FilteredList<Country> filteredCountries;
    private SortedList<Country> sortedCountries;

    @FXML
    private void initialize() {
        try {
            countries = FXCollections.observableArrayList(countryService.getAll());
            filteredCountries = new FilteredList<>(countries);
            sortedCountries = new SortedList<>(filteredCountries);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        applySearch(filteredCountries);
        applySelecton(() -> nameField.setText(tableView.getSelectionModel().getSelectedItem().getName()));
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
            Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
            if (selectedCountry == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idCountry = selectedCountry.getIdCountry();

            String name = nameField.getText();

            CountryUpdateDTO countryDto = new CountryUpdateDTO(name);
            Country updatedCountry = countryService.update(idCountry, countryDto);

            tableViewUpdate(CrudOperation.UPDATE, countries, updatedCountry);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedCountry, updatedCountry));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedCountry, updatedCountry, UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Country.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String name = nameField.getText();

            CountryInsertDTO countryDTO = new CountryInsertDTO(name);
            Country insertedCountry = countryService.insert(countryDTO);

            tableViewUpdate(CrudOperation.INSERT, countries, insertedCountry);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedCountry.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedCountry.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Country.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
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
            Country selectedCountry = tableView.getSelectionModel().getSelectedItem();
            if (selectedCountry == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idCountry = selectedCountry.getIdCountry();

            countryService.delete(idCountry);

            tableViewUpdate(CrudOperation.DELETE, countries, selectedCountry);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedCountry.getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedCountry.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Country.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void clearNameField() {
        nameField.clear();
    }
}
