package me.kristijanbobovec.museum.factory.column;


import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import me.kristijanbobovec.museum.util.ImageHelper;

import java.util.function.Function;

public class ColumnFactory {

    private ColumnFactory() {}

    public static <T> TableColumn<T, String> createColumnString(String title, Function<T, String> dataInCell) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(data -> new SimpleStringProperty(dataInCell.apply(data.getValue())));
        return col;
    }

    public static <T> TableColumn<T, String> createColumnImage(String title, Function<T, String> dataInCell) {
        TableColumn<T, String> col = createColumnString(title, dataInCell);
        col.setSortable(false);

        col.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String imagePath, boolean empty) {
                super.updateItem(imagePath, empty);
                if (empty || imagePath == null) {
                    setGraphic(null);
                } else {
                    Image img = ImageHelper.loadImage(this.getClass().getResource(imagePath));
                    if (img == null) { setGraphic(null); return; }
                    ImageView imageView = new ImageView(img);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(80);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });

        return col;
    }
}
