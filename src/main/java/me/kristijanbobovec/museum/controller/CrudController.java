package me.kristijanbobovec.museum.controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import me.kristijanbobovec.museum.model.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public abstract class CrudController<T extends Named> {
    private static final Logger log = LoggerFactory.getLogger(CrudController.class);

    @FXML
    protected TableView<T> tableView;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField searchField;


    public abstract void handleUpdate();
    public abstract void handleCreate();
    public abstract void handleClear();
    public abstract void handleDelete();

    protected void removeFromObservableList(ObservableList<T> list, T item) {
        list.remove(item);
    }

    protected void addToObservableList(ObservableList<T> list, T item) {
        list.add(item);
    }

    protected void updateInObservableList(ObservableList<T> list, T item) {
        int index = list.indexOf(item);
        list.set(index, item);
    }

    protected void unFocusTableView() {
        tableView.getSelectionModel().clearSelection();
    }

    protected void focusTableViewItem(T item) {
        tableView.getSelectionModel().select(item);
    }

    protected void showError(String message) {
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        errorLabel.setText(message);
    }

    protected void clearError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    protected void applySearch(FilteredList<T> filteredList) {
        searchField.textProperty().addListener((obs, oldval, newval) -> {
            filteredList.setPredicate(c -> {
                if (newval == null || newval.isEmpty()) return true;
                return c.getName().toLowerCase().contains(newval.toLowerCase());
            });
        });
    }

    protected <T> void comboBoxSetItems(ComboBox<T> comboBox, ObservableList<T> itemList) {
        comboBox.getItems().addAll(itemList);
    }

    protected <T> void comboBoxFormat(ComboBox<T> comboBox, Function<T, String> func) {
        comboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : func.apply(item));
            }
        });

        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : func.apply(item));
            }
        });
    }
}
