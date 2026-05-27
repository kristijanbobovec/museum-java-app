package me.kristijanbobovec.museum.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import me.kristijanbobovec.museum.factory.column.ExhibitColumnFactory;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.service.ExhibitService;
import me.kristijanbobovec.museum.service.MuseumCollectionService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.ImageHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.controlsfx.control.CheckComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

public final class MuseumDetailController {

    private static final Logger log = LoggerFactory.getLogger(MuseumDetailController.class);
    private final ExhibitService exhibitService;
    private final MuseumCollectionService museumCollectionService;

    public MuseumDetailController(ExhibitService exhibitService,MuseumCollectionService museumCollectionService) {
        this.exhibitService = exhibitService;
        this.museumCollectionService = museumCollectionService;
    }

    @FXML private Label museumNameLabel;
    @FXML private Label museumAddressLabel;
    @FXML private Label museumCityLabel;
    @FXML private Label museumCountryLabel;
    @FXML private Label museumYearLabel;
    @FXML private Label museumDescriptionLabel;

    @FXML private CheckComboBox<MuseumCollection> collectionComboBox;
    @FXML private TextField searchField;

    @FXML private Label exhibitCountLabel;

    @FXML private TableView<Exhibit> exhibitsTableView;
    @FXML private Label noExhibitsLabel;
    @FXML private ImageView museumImageView;

    private Museum museum;
    private ObservableList<Exhibit> exhibits;
    private ObservableList<MuseumCollection> collections;
    private FilteredList<Exhibit> filteredExhibits;
    private ObservableList<MuseumCollection> selectedCollections;
    private SortedList<Exhibit> sortedExhibits;

    public void setMuseum(Museum museum) {
        try {
            this.museum = museum;
            exhibits = FXCollections.observableArrayList(exhibitService.getAllByMuseumId(museum.getIdMuseum()));
            collections = FXCollections.observableArrayList(museumCollectionService.getByMuseumId(museum.getIdMuseum()));
            filteredExhibits = new FilteredList<>(exhibits);
            sortedExhibits = new SortedList<>(filteredExhibits);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Database.LOAD_FAIL, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        initTableView();
        initCollectionComboBox();
        setLabels();
        loadMuseumImage();
        bindVisibility();
        applyFilters();
    }

    private void applyFilters() {
        selectedCollections = collectionComboBox.getCheckModel().getCheckedItems();

        selectedCollections.addListener((ListChangeListener<MuseumCollection>) e ->
            filteredExhibits.setPredicate(this::buildPredicate));

        searchField.textProperty().addListener((obs, oldVal, newVal) ->
            filteredExhibits.setPredicate(this::buildPredicate));
    }

    private boolean buildPredicate(Exhibit exhibit) {
        boolean inSelectedCollections = selectedCollections == null ||
                selectedCollections.isEmpty() ||
                selectedCollections.contains(exhibit.getMuseumCollection());

        boolean matchesSearch = searchField.getText() == null ||
                searchField.getText().isEmpty() ||
                exhibit.getName().toLowerCase().contains(searchField.getText().toLowerCase());

        return matchesSearch && inSelectedCollections;
    }

    private void initCollectionComboBox() {
        collectionComboBox.getItems().addAll(collections);
    }

    private void initTableView() {
        exhibitsTableView.setItems(sortedExhibits);
        sortedExhibits.comparatorProperty().bind(exhibitsTableView.comparatorProperty());

        for (ExhibitColumnFactory col : ExhibitColumnFactory.values()) {
            exhibitsTableView.getColumns().add(col.createColumn());
        }
    }


    private void bindExhibitCountLabel() {
        exhibitCountLabel.textProperty().bind(Bindings.size(filteredExhibits).asString("%d exhibits shown"));
    }

    private void setNoExhibitsLabel() {
        String text = exhibits.isEmpty() ? Messages.Exhibit.NO_EXHIBITS : Messages.Filter.NO_RESULTS;
        noExhibitsLabel.setText(text);
    }

    private void bindVisibility() {
        bindExhibitListViewVisibility();
        bindExhibitCountLabelVisibility();
        bindNoExhibitsLabelVisibility();
    }

    private void bindNoExhibitsLabelVisibility() {
        noExhibitsLabel.visibleProperty().bind(Bindings.isEmpty(filteredExhibits));
        noExhibitsLabel.managedProperty().bind(Bindings.isEmpty(filteredExhibits));
    }

    private void bindExhibitCountLabelVisibility() {
        exhibitCountLabel.visibleProperty().bind(Bindings.isEmpty(filteredExhibits).not());
        exhibitCountLabel.managedProperty().bind(Bindings.isEmpty(filteredExhibits).not());
    }

    private void bindExhibitListViewVisibility() {
        exhibitsTableView.visibleProperty().bind(Bindings.isEmpty(filteredExhibits).not());
        exhibitsTableView.managedProperty().bind(Bindings.isEmpty(filteredExhibits).not());
    }


    private void setLabels() {
        museumNameLabel.setText(museum.getName());
        museumAddressLabel.setText(museum.getAdress());
        museumCityLabel.setText(museum.getCity().getName());
        museumCountryLabel.setText(museum.getCountry().getName());
        museumDescriptionLabel.setText(museum.getDescription());
        museumYearLabel.setText(String.valueOf(museum.getEstablishmentYear()));

        setNoExhibitsLabel();
        bindExhibitCountLabel();
    }

    private void loadMuseumImage() {
        Optional<String> imagePath = Optional.ofNullable(museum.getImagePath());
        imagePath.ifPresent(path -> museumImageView.setImage(ImageHelper.loadImage(this.getClass().getResource(path))));
    }



    @FXML
    private void handleBack() {
        SceneManager.switchTo("home.fxml");
    }
}
