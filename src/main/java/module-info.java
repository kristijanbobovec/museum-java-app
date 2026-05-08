module me.kristijanbobovec.museum {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;

    opens me.kristijanbobovec.museum to javafx.fxml;
    opens me.kristijanbobovec.museum.controller to javafx.fxml;
    exports me.kristijanbobovec.museum;
}