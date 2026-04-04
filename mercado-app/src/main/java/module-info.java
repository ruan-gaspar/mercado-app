module com.mercadoapp.mercadoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;


    opens com.mercadoapp.mercadoapp to javafx.fxml;
    exports com.mercadoapp.mercadoapp;
    exports com.mercadoapp.mercadoapp.controller;
    opens com.mercadoapp.mercadoapp.controller to javafx.fxml;
}