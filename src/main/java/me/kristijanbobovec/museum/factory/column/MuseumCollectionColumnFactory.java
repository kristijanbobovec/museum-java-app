package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.MuseumCollection;

import java.util.function.Function;

public enum MuseumCollectionColumnFactory {
    NAME("Collection", MuseumCollection::getName);
    private final String title;
    private final Function<MuseumCollection, String> func;

    MuseumCollectionColumnFactory(String title, Function<MuseumCollection, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<MuseumCollection, String> createColumn() {
        return ColumnFactory.createColumnString(title, func);
    }
}

