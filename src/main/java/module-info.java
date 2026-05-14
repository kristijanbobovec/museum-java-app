module me.kristijanbobovec.museum {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires com.zaxxer.hikari;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires jbcrypt;
    requires java.management;


    opens me.kristijanbobovec.museum to javafx.fxml;
    opens me.kristijanbobovec.museum.controller to javafx.fxml;
    exports me.kristijanbobovec.museum;
    opens me.kristijanbobovec.museum.database to javafx.fxml;
    opens me.kristijanbobovec.museum.manager to javafx.fxml;
}