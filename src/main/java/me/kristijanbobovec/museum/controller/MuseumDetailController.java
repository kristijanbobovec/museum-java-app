package me.kristijanbobovec.museum.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import me.kristijanbobovec.museum.factory.column.ExhibitColumnFactory;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.service.ExhibitService;
import me.kristijanbobovec.museum.util.AlertHelper;
import me.kristijanbobovec.museum.util.ImageHelper;
import me.kristijanbobovec.museum.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

public final class MuseumDetailController {

    private static final Logger log = LoggerFactory.getLogger(MuseumDetailController.class);
    private final ExhibitService exhibitService;

    public MuseumDetailController(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }

    @FXML private Label museumNameLabel;
    @FXML private Label museumAddressLabel;
    @FXML private Label museumCityLabel;
    @FXML private Label museumCountryLabel;
    @FXML private Label museumYearLabel;
    @FXML private Label museumDescriptionLabel;

    @FXML private ComboBox<MuseumCollection> collectionComboBox;
    @FXML private TextField searchField;

    @FXML private Label exhibitCountLabel;

    @FXML private TableView<Exhibit> exhibitsTableView;
    @FXML private Label noExhibitsLabel;
    @FXML private ImageView museumImageView;

    private Museum museum;
    private ObservableList<Exhibit> exhibits;
    private FilteredList<Exhibit> filteredExhibits;

    public void setMuseum(Museum museum) {
        try {
            this.museum = museum;
            exhibits = FXCollections.observableArrayList(exhibitService.getAllByMuseumId(museum.getIdMuseum()));
            filteredExhibits = new FilteredList<>(exhibits);
            initView();
        } catch (SQLException e) {
            log.error(Messages.Exhibit.EXHIBITS_LOAD_FAIL_LOG, e);
            AlertHelper.displayAlert(Alert.AlertType.ERROR, Messages.Server.SERVER_ERROR_HEADER, Messages.Server.SERVER_ERROR_CONTENT);
        }
    }

    private void initView() {
        setLabels();
        bindVisibility();
        loadMuseumFacade();
        initTableView();
        updateLabels();
    }

    private void initTableView() {
        exhibitsTableView.setItems(filteredExhibits);

        for (ExhibitColumnFactory col : ExhibitColumnFactory.values()) {
            exhibitsTableView.getColumns().add(col.createColumn());
        }
    }


    private void updateLabels() {
        updateNoExhibitsLabel();
        updateExhibitCountLabel();
    }

    private void updateExhibitCountLabel() {
        exhibitCountLabel.setText(filteredExhibits.size() + " exhibits shown.");
    }

    private void updateNoExhibitsLabel() {
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
    }

    private void loadMuseumFacade() {
        Optional<String> imagePath = Optional.ofNullable(museum.getImagePath());
        imagePath.ifPresent(path -> museumImageView.setImage(ImageHelper.loadImage(this.getClass().getResource(path))));
    }

    @FXML
    private void handleCollectionFilter(ActionEvent event) {

    }


    @FXML
    private void handleBack(ActionEvent event) {
        SceneManager.switchTo("home.fxml");
    }
}
