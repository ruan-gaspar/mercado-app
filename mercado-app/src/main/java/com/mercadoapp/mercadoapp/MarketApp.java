package com.mercadoapp.mercadoapp;

import com.mercadoapp.mercadoapp.dao.CategoryDAO;
import com.mercadoapp.mercadoapp.model.Category;
import com.mercadoapp.mercadoapp.util.DBInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MarketApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        DBInitializer dbInitializer = new DBInitializer();
        dbInitializer.initDatabase();
        Category category = new Category(null,"Mercearia");

        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.findAll();
        dao.findAll().forEach(c -> {
            System.out.println(c.getName());
            System.out.println(c.getName());
        });
        System.out.println(dao.findAll());
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