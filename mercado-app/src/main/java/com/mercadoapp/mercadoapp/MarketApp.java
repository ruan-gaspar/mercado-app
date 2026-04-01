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

        CategoryDAO dao = new CategoryDAO();
        Category category = new Category(null,"Mercearia");
        Category category2 = new Category(null,"Higiene");
        Category category3 = new Category(null,"Limpeza");
        //dao.save(category);
        //dao.save(category2);
        //dao.save(category3);

        List<Category> categories = dao.findAll();
        categories.forEach(c -> {
            System.out.println("Id: " + c.getId() + ", Nome: " + c.getName());
        });
        FXMLLoader fxmlLoader = new FXMLLoader(MarketApp.class.getResource("/com/mercadoapp/mercadoapp/fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Market App!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}