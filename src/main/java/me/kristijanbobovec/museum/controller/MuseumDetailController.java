package me.kristijanbobovec.museum.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.kristijanbobovec.museum.manager.SceneManager;
import me.kristijanbobovec.museum.model.Exhibit;
import me.kristijanbobovec.museum.model.Museum;
import me.kristijanbobovec.museum.model.MuseumCollection;
import me.kristijanbobovec.museum.service.ExhibitService;

import java.sql.SQLException;

public final class MuseumDetailController {

    private final ExhibitService exhibitService;

    public MuseumDetailController(ExhibitService exhibitService) {
        this.exhibitService = exhibitService;
    }


    // ── FXML polja ─────────────────────────────────────────────────────────────

    @FXML private Label museumNameLabel;
    @FXML private Label museumAddressLabel;
    @FXML private Label museumCityLabel;
    @FXML private Label museumCountryLabel;
    @FXML private Label museumYearLabel;
    @FXML private Label museumDescriptionLabel;

    @FXML private ComboBox<MuseumCollection> collectionComboBox;
    @FXML private TextField searchField;
    @FXML private ComboBox<?> sortComboBox;
    @FXML private Label exhibitCountLabel;

    @FXML private TableView<Exhibit> exhibitsTableView;
    @FXML private TableColumn<Exhibit, String> imageColumn;
    @FXML private TableColumn<Exhibit, String> nameColumn;
    @FXML private TableColumn<Exhibit, String> collectionColumn;
    @FXML private Label noExhibitsLabel;
    @FXML private ImageView museumImageView;

    private Museum museum;

    public void setMuseum(Museum museum) {
        this.museum = museum;
        setLabels();
        imageColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImagePath()));
        imageColumn.setCellFactory(col -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            {
                imageView.setFitWidth(80);
                imageView.setFitHeight(55);
                imageView.setPreserveRatio(true);
            }
            @Override
            protected void updateItem(String path, boolean empty) {
                super.updateItem(path, empty);
                if (empty || path == null || path.isBlank()) {
                    setGraphic(null);
                } else {
                    try {
                        String url = path.startsWith("/")
                                ? getClass().getResource(path).toExternalForm()
                                : path;
                        imageView.setImage(new Image(url, true));
                        setGraphic(imageView);
                    } catch (Exception e) {
                        setGraphic(null);
                    }
                }
            }
        });
        nameColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
        collectionColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMuseumCollection().getName()));
        exhibitsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        try {
            ObservableList<Exhibit> exhibits = FXCollections.observableArrayList(exhibitService.getAllByMuseumId(museum.getIdMuseum()));
            exhibitsTableView.setItems(exhibits);
        } catch (SQLException e) {
            noExhibitsLabel.setManaged(true);
            noExhibitsLabel.setVisible(true);
        }
        //setImage();
    }

    private void setLabels() {
        museumNameLabel.setText(museum.getName());
        museumAddressLabel.setText(museum.getAdress());
        museumCityLabel.setText(museum.getCity().getName());
        museumCountryLabel.setText(museum.getCountry().getName());
        museumDescriptionLabel.setText(museum.getDescription());
        museumYearLabel.setText(String.valueOf(museum.getEstablishmentYear()));
    }

    @FXML
    private void handleCollectionFilter(ActionEvent event) {

    }

    @FXML
    private void handleSort(ActionEvent event) {

    }

    @FXML
    private void handleBack(ActionEvent event) {
        SceneManager.switchTo("home.fxml");
    }
}
