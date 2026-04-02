package com.mercadoapp.mercadoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button categoryButton;
    @FXML
    protected void openOrderScreen() {
        System.out.println("Compras");
    }
    @FXML
    protected void openInventoryScreen() {
        System.out.println("Inventário");
    }
    @FXML
    protected void openProductDetailScreen() {
        System.out.println("Produtos");
    }
    @FXML
    protected void openCategoryScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/category-view.fxml", categoryButton);
    }
    @FXML
    protected void openSectorScreen() {
        System.out.println("Setores");
    }
    private void changeScene(String fxmlPath, Button button) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
