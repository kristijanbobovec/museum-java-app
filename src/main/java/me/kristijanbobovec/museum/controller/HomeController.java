package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ObservableList<Museum> listOfMuseums;
    private FilteredList<Museum> filteredList;

    public HomeController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @FXML
    private void initialize() {
        try {
            listOfMuseums = FXCollections.observableArrayList(museumService.getAllWithoutDetails());
            initList();
            applyFilters();
        } catch (SQLException e) {
            log.error(Messages.Museum.MUSEUMS_LOAD_FAIL_LOG, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initList() {
        filteredList = new FilteredList<>(listOfMuseums, m -> true);
        museumsListView.setItems(filteredList);
        updateView();
    }

    private void applyFilters() {
        searchField.textProperty().addListener((obs, oldval, newval) -> {
            filteredList.setPredicate(m -> {
                if (newval == null || newval.isEmpty()) return true;
                return m.getName().toLowerCase().contains(newval.toLowerCase());
            });
            updateView(Messages.Filter.NO_RESULTS.formatted(newval));
        });
    }

    private void updateView() {
        boolean isEmpty = filteredList.isEmpty();
        noMuseumsLabel.setVisible(isEmpty);
        noMuseumsLabel.setManaged(isEmpty);
        noMuseumsLabel.setText(Messages.Museum.NO_MUSEUMS);
        museumsListView.setVisible(!isEmpty);
        museumsListView.setManaged(!isEmpty);
    }

    private void updateView(String fallbackText) {
        updateView();
        noMuseumsLabel.setText(fallbackText);
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

    @FXML
    private void handleNewMuseum(ActionEvent event) {}

    @FXML
    private void handleNewCollection(ActionEvent event) {}

    @FXML
    private void handleEditExhibit(ActionEvent event) {}

    @FXML
    private void handleEditCurator(ActionEvent event) {}

    @FXML
    private void handleCreateBackup(ActionEvent event) {}

    @FXML
    private void handleLoadBackup(ActionEvent event) {}

    @FXML
    private void handleDeleteAll(ActionEvent event) {}
}
