package me.kristijanbobovec.museum.factory.column;

import javafx.scene.control.TableColumn;
import me.kristijanbobovec.museum.model.City;
import java.util.function.Function;

public enum CityColumnFactory {
    NAME("City", City::getName),
    COUNTRY("Country", c -> c.getCountry().getName()),
    COUNTRYID("CountryID", c -> String.valueOf(c.getCountryId()));

    private final String title;
    private final Function<City, String> func;

    CityColumnFactory(String title, Function<City, String> func) {
        this.title = title;
        this.func = func;
    }

    public TableColumn<City, String> createColumn() {
        return ColumnFactory.createColumnString(title, func);
    }
}

