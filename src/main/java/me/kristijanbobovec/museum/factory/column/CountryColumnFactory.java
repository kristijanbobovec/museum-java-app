package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.Country;

import java.util.function.Function;

public enum CountryColumnFactory {
    NAME("Name", Country::getName);

    private final String title;
    private final Function<Country, String> func;

    CountryColumnFactory(String title, Function<Country, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<Country, String> createColumn() {
        return ColumnFactory.createColumnString(title, func);
    }
}

