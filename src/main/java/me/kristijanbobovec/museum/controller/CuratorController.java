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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import me.kristijanbobovec.museum.exception.DatabaseFailedToException;
import me.kristijanbobovec.museum.exception.OmitingRequiredFieldsException;
import me.kristijanbobovec.museum.factory.column.CuratorColumnFactory;
import me.kristijanbobovec.museum.manager.UserSession;
import me.kristijanbobovec.museum.model.*;
import me.kristijanbobovec.museum.model.dto.curator.CuratorInsertDTO;
import me.kristijanbobovec.museum.model.dto.curator.CuratorUpdateDTO;
import me.kristijanbobovec.museum.service.CuratorCollectionService;
import me.kristijanbobovec.museum.service.CuratorService;
import me.kristijanbobovec.museum.service.MuseumCollectionService;
import me.kristijanbobovec.museum.service.MuseumService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private ObservableList<MuseumCollection> assignedCollections = FXCollections.observableArrayList();
    private ObservableList<MuseumCollection> museumCollections = FXCollections.observableArrayList();

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
        applySelection(() -> {
            Curator selected = tableView.getSelectionModel().getSelectedItem();
            firstNameField.setText(selected.getFirstName());
            lastNameField.setText(selected.getLastName());
            museumComboBox.getSelectionModel().select(selected.getMuseum());
            updateAssignedCollectionsListView();
            updateAvailableCollectionsListView();
        });
        applyComboboxSelection();
        setDragAndDrop(availableCollectionsListView, assignedCollectionsListView);
        setDragAndDrop(assignedCollectionsListView, availableCollectionsListView);
    }

    private void applyComboboxSelection() {
        museumComboBox.getSelectionModel().selectedItemProperty().addListener((obs, newVal, selected) -> {
            if (selected == null) {
                if (!assignedCollections.isEmpty()) clearAssignedListView();
                if (!museumCollections.isEmpty())   clearAvailableListView();
            };
            updateAssignedCollectionsListView();
            updateAvailableCollectionsListView();
        });
    }

    private void updateAvailableCollectionsListView() {
        Museum selectedMuseum = museumComboBox.getSelectionModel().getSelectedItem();
        if (selectedMuseum == null) return;

        List<MuseumCollection> forMuseum = collectionsByMuseum.getOrDefault(selectedMuseum.getIdMuseum(), List.of());
        museumCollections.setAll(forMuseum.stream()
                .filter(c -> assignedCollections.stream().noneMatch(a -> a.getIdCollection() == c.getIdCollection()))
                .toList());
    }



    private void updateAssignedCollectionsListView() {
        Curator selectedCurator = tableView.getSelectionModel().getSelectedItem();
        Museum selectedMuseum = museumComboBox.getSelectionModel().getSelectedItem();
        if (selectedCurator == null
                || selectedMuseum == null) return;

        if (selectedCurator.getMuseumId() != selectedMuseum.getIdMuseum()) {
            assignedCollections.clear();
        } else {
            assignedCollections.setAll(FXCollections.observableArrayList(
                    selectedCurator.getMuseumCollections()
            ));
        }
    }

    private void initListView() {
        availableCollectionsListView.setItems(museumCollections);
        assignedCollectionsListView.setItems(assignedCollections);
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

    private void setDragAndDrop(ListView<MuseumCollection> source, ListView<MuseumCollection> destination) {
        source.setOnDragDetected(e -> {
            MuseumCollection selectedCollection = source.getSelectionModel().getSelectedItem();
            if (selectedCollection == null) return;

            Dragboard db = source.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(selectedCollection.getIdCollection()));
            db.setContent(content);

            e.consume();
        });

        destination.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();

            if (e.getGestureSource() != destination && db.hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }

            e.consume();
        });

        destination.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();

            int museumCollectionId = Integer.parseInt(db.getString());
            Optional<MuseumCollection> museumCollection = allCollections
                    .stream()
                    .filter(c -> c.getIdCollection() == museumCollectionId)
                    .findFirst();

            if (museumCollection.isEmpty()) return;

            MuseumCollection collection = museumCollection.get();

            destination.getItems().add(collection);
            source.getItems().remove(collection);

            e.consume();
        });
    }


    @Override
    public void handleUpdate() {
        clearError();
        try {
            Curator selectedCurator = tableView.getSelectionModel().getSelectedItem();
            if (selectedCurator == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("update"));
                return;
            }
            int idCurator = selectedCurator.getIdCurator();

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            int museumId = Optional.ofNullable(museumComboBox.getSelectionModel().getSelectedItem())
                    .map(Museum::getIdMuseum)
                    .orElse(0);

            if (!new HashSet<>(assignedCollections).equals(new HashSet<>(selectedCurator.getMuseumCollections()))) {
                curatorCollectionService.removeAllByCuratorId(selectedCurator.getIdCurator());
                assignCollectionsToCurator(selectedCurator);
            }

            CuratorUpdateDTO curatorDto = new CuratorUpdateDTO(firstName, lastName, museumId);
            Curator updatedCurator = curatorService.update(idCurator, curatorDto);

            tableViewUpdate(CrudOperation.UPDATE, curators, updatedCurator);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.UPDATED_HEADER, Messages.CRUD.UPDATED_CONTENT.formatted(selectedCurator, updatedCurator));
            log.info(Messages.CRUD.UPDATED_LOG.formatted(selectedCurator, updatedCurator, UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Curator.UPDATE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    @Override
    public void handleCreate() {
        clearError();
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            int museumId = Optional.ofNullable(museumComboBox.getSelectionModel().getSelectedItem())
                    .map(Museum::getIdMuseum)
                    .orElse(0);


            CuratorInsertDTO curatorDto = new CuratorInsertDTO(firstName, lastName, museumId);
            Curator insertedCurator = curatorService.insert(curatorDto);

            if (!assignedCollections.isEmpty()) {
                assignCollectionsToCurator(insertedCurator);
            }

            tableViewUpdate(CrudOperation.INSERT, curators, insertedCurator);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.INSERTED_HEADER, Messages.CRUD.INSERTED_CONTENT.formatted(insertedCurator.getName()));
            log.info(Messages.CRUD.INSERT_LOG.formatted(insertedCurator.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (DatabaseFailedToException | SQLException e) {
            log.error(Messages.Curator.INSERT_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void assignCollectionsToCurator(Curator curator) throws SQLException {
        curator.getMuseumCollections().addAll(assignedCollections);

        for (MuseumCollection mc : assignedCollections) {
            curatorCollectionService.assign(curator.getIdCurator(), mc.getIdCollection());
        }
    }

    @Override
    public void handleClear() {
        clearFirstNameField();
        clearLastNameField();
        clearMuseumSelection();
        unFocusTableView();
    }

    private void clearAssignedListView() {
        assignedCollectionsListView.getItems().clear();
    }
    private void clearAvailableListView() {
        availableCollectionsListView.getItems().clear();
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
        clearError();
        try {
            Curator selectedCurator = tableView.getSelectionModel().getSelectedItem();
            if (selectedCurator == null) {
                showError(Messages.CRUDValidation.ITEM_NOT_SELECTED.formatted("delete"));
                return;
            }
            int idCurator = selectedCurator.getIdCurator();

            curatorService.delete(idCurator);

            tableViewUpdate(CrudOperation.DELETE, curators, selectedCurator);

            AlertHelper.displayAlert(Alert.AlertType.INFORMATION, Messages.CRUD.DELETED_HEADER, Messages.CRUD.DELETED_CONTENT.formatted(selectedCurator.getName()));
            log.info(Messages.CRUD.DELETED_LOG.formatted(selectedCurator.getName(), UserSession.INSTANCE.getSession().username()));
        } catch (OmitingRequiredFieldsException e) {
            showError(e.getMessage());
        } catch (SQLException e) {
            log.error(Messages.Curator.DELETE_FAIL_LOG.formatted(UserSession.INSTANCE.getSession().username()), e);
            showError(Messages.Server.SERVER_ERROR_CONTENT);
        }
    }
}
