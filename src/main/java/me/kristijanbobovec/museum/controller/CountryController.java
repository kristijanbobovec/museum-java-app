package me.kristijanbobovec.museum.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import me.kristijanbobovec.museum.model.Country;


public class CountryController extends NavigationController implements CrudController {

    @FXML
    private TableView<Country> tableView;

    private ObservableList<Country> countries;

    @FXML
    private void initialize() {
    }

    private void getData() {

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
