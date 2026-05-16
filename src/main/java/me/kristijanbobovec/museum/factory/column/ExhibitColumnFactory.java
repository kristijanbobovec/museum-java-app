package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.Exhibit;

import java.util.function.Function;

public enum ExhibitColumnFactory {
    IMAGE("Image", Exhibit::getImagePath),
    NAME("Name", Exhibit::getName),
    DESCRIPTION("Description", Exhibit::getDescription),
    CREATIONYEAR("Creation year", e -> String.valueOf(e.getCreationYear())),
    COLLECTION("Collection", e -> e.getMuseumCollection().getName());

    private final String title;
    private final Function<Exhibit, String> func;

    ExhibitColumnFactory(String title, Function<Exhibit, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<Exhibit, String> createColumn() {
        return this == IMAGE ? ColumnFactory.createColumnImage(title, func) : ColumnFactory.createColumnString(title, func);
    }
}
