package com.mercadoapp.mercadoapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button plannedOrderButton;

    @FXML
    private Button plannedOrderItemButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button orderItemButton;

    @FXML
    private Button productButton;

    @FXML
    private Button categoryButton;

    @FXML
    private Button sectorButton;

    @FXML
    protected void openPlannedOrderScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/planned-order-view.fxml", plannedOrderButton);
    }

    @FXML
    protected void openPlannedOrderItemScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/planned-order-item-view.fxml", plannedOrderItemButton);
    }

    @FXML
    protected void openOrderScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/order-view.fxml", orderButton);
    }

    @FXML
    protected void openOrderItemScreen() {
        changeScene("/com/mercadoapp/mercadoapp/fxml/order-item-view.fxml", orderItemButton);
    }

    @FXML
    protected void openProductScreen() {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir a tela: " + fxmlPath, e);
        }
    }
}