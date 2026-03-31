package com.mercadoapp.mercadoapp;

import com.mercadoapp.mercadoapp.util.SQLiteConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class MarketApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        System.out.println(SQLiteConnection.connect());
        FXMLLoader fxmlLoader = new FXMLLoader(MarketApp.class.getResource("/com/mercadoapp/mercadoapp/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Market App!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}