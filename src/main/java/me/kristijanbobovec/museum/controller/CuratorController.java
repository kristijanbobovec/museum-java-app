package me.kristijanbobovec.museum.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import me.kristijanbobovec.museum.factory.column.CuratorColumnFactory;
import me.kristijanbobovec.museum.model.Curator;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.service.CuratorCollectionService;
import me.kristijanbobovec.museum.service.CuratorService;
import me.kristijanbobovec.museum.service.MuseumCollectionService;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CuratorController extends CrudController<Curator> {

    private static final Logger log = LoggerFactory.getLogger(CuratorController.class);
    private final MuseumService museumService;
    private final CuratorService curatorService;
    private final CuratorCollectionService curatorCollectionService;

    private final MuseumCollectionService museumCollectionService;

    private ObservableList<Curator> curators;
    private ObservableList<Museum> museums;
    private FilteredList<Curator> filteredCurators;
    private SortedList<Curator> sortedCurators;

    private ObservableList<MuseumCollection> allCollections;
    private Map<Integer, List<MuseumCollection>> collectionsByMuseum;
    private FilteredList<MuseumCollection> filteredAvailableCollections;

    @FXML
    private ComboBox<Museum> museumComboBox;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ListView<MuseumCollection> availableCollectionsListView;
    @FXML
    private ListView<MuseumCollection> assignedCollectionsListView;


    public CuratorController(CuratorService curatorService, MuseumService museumService, CuratorCollectionService curatorCollectionService, MuseumCollectionService museumCollectionService) {
        this.museumService = museumService;
        this.curatorService = curatorService;
        this.curatorCollectionService = curatorCollectionService;
        this.museumCollectionService = museumCollectionService;
    }

    @FXML
    private void initialize() {
        try {
            museums = FXCollections.observableArrayList(museumService.getAll());
            curators = FXCollections.observableArrayList(curatorService.getAll());
            allCollections = FXCollections.observableArrayList(museumCollectionService.getAll());
            collectionsByMuseum = museumCollectionService.getAllGroupedByMuseum(museums);
            filteredAvailableCollections = new FilteredList<>(allCollections);
            filteredCurators = new FilteredList<>(curators);
            sortedCurators = new SortedList<>(filteredCurators);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        initListView();
        initCombobox();
        applySearch(filteredCurators);
        applySelecton(() -> {
            Curator selected = tableView.getSelectionModel().getSelectedItem();
            firstNameField.setText(selected.getFirstName());
            lastNameField.setText(selected.getLastName());
            museumComboBox.getSelectionModel().select(selected.getMuseum());
        });
        applyComboboxSelection();
    }

    private void applyComboboxSelection() {
        museumComboBox.getSelectionModel().selectedItemProperty().addListener((obs, newVal, selected) -> {
            if (selected == null) return;
            List<MuseumCollection> collections = collectionsByMuseum.getOrDefault(selected.getIdMuseum(), List.of());
            filteredAvailableCollections.setPredicate(collections::contains);
            updateAssignedCollectionsListView();
        });
    }

    private void updateAssignedCollectionsListView() {
        Curator selectedCurator = tableView.getSelectionModel().getSelectedItem();
        Museum selectedMuseum = museumComboBox.getSelectionModel().getSelectedItem();
        if (selectedCurator == null
                || selectedMuseum == null
                || selectedMuseum.getIdMuseum() != selectedCurator.getMuseumId()) return;

        assignedCollectionsListView.setItems(FXCollections.observableArrayList(selectedCurator.getMuseumCollections()));
    }

    private void initListView() {
        availableCollectionsListView.setItems(filteredAvailableCollections);
    }


    private void initCombobox() {
        comboBoxFormat(museumComboBox, Museum::getName);
        comboBoxSetItems(museumComboBox, museums);
    }


    private void initTableView() {
        tableView.setItems(sortedCurators);
        sortedCurators.comparatorProperty().bind(tableView.comparatorProperty());

        for (CuratorColumnFactory col : CuratorColumnFactory.values()) {
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
        clearFirstNameField();
        clearLastNameField();
        clearMuseumSelection();
        unFocusTableView();
    }


    private void clearFirstNameField() {
        firstNameField.clear();
    }
    private void clearMuseumSelection() {
        museumComboBox.getSelectionModel().clearSelection();
    }
    private void clearLastNameField() {
        lastNameField.clear();
    }

    @Override
    public void handleDelete() {

    }
}
