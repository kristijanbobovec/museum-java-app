package me.kristijanbobovec.museum.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public final class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @FXML private ListView<Museum> museumsListView;
    @FXML private Label noMuseumsLabel;
    @FXML private TextField searchField;

    private final MuseumService museumService;
    private ObservableList<Museum> museums;
    private FilteredList<Museum> filteredMuseums;

    public HomeController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @FXML
    private void initialize() {
        try {
            museums = FXCollections.observableArrayList(museumService.getAll());
            filteredMuseums = new FilteredList<>(museums, m -> true);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        setNoMuseumsLabel();
        initList();
        bindVisibility();
        applyFilters();
    }

    private void bindVisibility() {
        bindNoMuseumsLabelVisibility();
        bindMuseumsListViewVisibility();
    }

    private void bindMuseumsListViewVisibility() {
        museumsListView.visibleProperty().bind(Bindings.isEmpty(filteredMuseums).not());
        museumsListView.managedProperty().bind(Bindings.isEmpty(filteredMuseums).not());
    }

    private void bindNoMuseumsLabelVisibility() {
        noMuseumsLabel.visibleProperty().bind(Bindings.isEmpty(filteredMuseums));
        noMuseumsLabel.managedProperty().bind(Bindings.isEmpty(filteredMuseums));
    }


    private void initList() {
        museumsListView.setItems(filteredMuseums);
    }

    private void applyFilters() {
        searchField.textProperty().addListener((obs, oldval, newval) ->
            filteredMuseums.setPredicate(m -> {
                if (newval == null || newval.isEmpty()) return true;
                return m.getName().toLowerCase().contains(newval.toLowerCase());
            }));
    }

    private void setNoMuseumsLabel() {
        String text = museums.isEmpty() ? Messages.Museum.NO_MUSEUMS : Messages.Filter.NO_RESULTS;
        noMuseumsLabel.setText(text);
    }

    @FXML
    private void handleMuseumSelected(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            Museum selected = museumsListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                SceneManager.switchTo("museum-detail.fxml", selected.getName(),
                        (MuseumDetailController c) -> c.setMuseum(selected));
            }
        }
    }

}
