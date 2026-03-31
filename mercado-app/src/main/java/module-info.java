module com.mercadoapp.mercadoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mercadoapp.mercadoapp to javafx.fxml;
    exports com.mercadoapp.mercadoapp;
}