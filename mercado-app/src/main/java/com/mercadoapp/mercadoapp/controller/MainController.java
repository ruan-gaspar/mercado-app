package com.mercadoapp.mercadoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button categoryButton;
    @FXML
    private Button sectorButton;
    @FXML
    private Button productButton;
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
        changeScene("/com/mercadoapp/mercadoapp/fxml/product-view.fxml", productButton);
    }
    @FXML
    protected void openCategoryScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/category-view.fxml", categoryButton);
    }
    @FXML
    protected void openSectorScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/sector-view.fxml", sectorButton);
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
