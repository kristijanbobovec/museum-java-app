package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.Museum;

import java.util.function.Function;

public enum MuseumColumnFactory {
    IMAGE("Image", Museum::getImagePath),
    NAME("Museum", Museum::getName),
    ADDRESS("Address", Museum::getAdress),
    ESTYEAR("Establishment year", m -> String.valueOf(m.getEstablishmentYear())),
    CITY("City", m -> m.getCity().getName()),
    DESCRIPTION("Description", Museum::getDescription);

    private final String title;
    private final Function<Museum, String> func;

    MuseumColumnFactory(String title, Function<Museum, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<Museum, String> createColumn() {
        return this == IMAGE ? ColumnFactory.createColumnImage(title, func) : ColumnFactory.createColumnString(title, func);
    }
}

