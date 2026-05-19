package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import me.kristijanbobovec.museum.factory.column.CountryColumnFactory;
import me.kristijanbobovec.museum.model.Country;
import me.kristijanbobovec.museum.service.CountryService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;


public class CountryController extends NavigationController implements CrudController {

    private static final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @FXML
    private TableView<Country> tableView;

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

    }

    @Override
    public void handleCreate() {

    }

    @Override
    public void handleClear() {

    }

    @Override
    public void handleDelete() {

    }
}
