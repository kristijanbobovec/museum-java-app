package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.Exhibit;

import java.util.function.Function;

public enum ExhibitDetailsColumnFactory {
    IMAGE("Image", Exhibit::getImagePath),
    NAME("Name", Exhibit::getName),
    DESCRIPTION("Description", Exhibit::getDescription),
    CREATIONYEAR("Creation year", e -> String.valueOf(e.getCreationYear())),
    COLLECTION("Collection", e -> e.getMuseumCollection() == null ? "Null" : e.getMuseumCollection().getName()),
    MUSEUM("Museum", e -> e.getMuseum() == null ? "Null" : e.getMuseum().getName());

    private final String title;
    private final Function<Exhibit, String> func;

    ExhibitDetailsColumnFactory(String title, Function<Exhibit, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<Exhibit, String> createColumn() {
        return this == IMAGE ? ColumnFactory.createColumnImage(title, func) : ColumnFactory.createColumnString(title, func);
    }
}
