package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.Curator;

import java.util.function.Function;

public enum CuratorColumnFactory {
    FIRSTNAME("First Name", Curator::getFirstName),
    LASTNAME("Last Name", Curator::getLastName),
    MUSEUM("Last Name", c -> c.getMuseum().getName());

    private final String title;
    private final Function<Curator, String> func;

    CuratorColumnFactory(String title, Function<Curator, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<Curator, String> createColumn() {
        return ColumnFactory.createColumnString(title, func);
    }
}
