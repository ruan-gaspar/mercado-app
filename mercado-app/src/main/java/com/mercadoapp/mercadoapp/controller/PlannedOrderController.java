package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.PlannedOrderDAO;
import com.mercadoapp.mercadoapp.model.PlannedOrder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class PlannedOrderController {
    @FXML
    private Button backButton;

    @FXML
    private TableView<PlannedOrder> plannedOrderTable;

    @FXML
    private TableColumn<PlannedOrder, Integer> idColumn;

    @FXML
    private TableColumn<PlannedOrder, String> nameColumn;

    @FXML
    private TableColumn<PlannedOrder, String> createdAtColumn;

    @FXML
    private TextField plannedOrderNameField;

    @FXML
    private TextField plannedOrderCreatedAtField;

    private final PlannedOrderDAO plannedOrderDAO = new PlannedOrderDAO();

    @FXML
    public void initialize() {
        plannedOrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newSelection) -> {
            if (newSelection != null) {
                plannedOrderNameField.setText(newSelection.getName());
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadPlannedOrders();
    }

    private void loadPlannedOrders() {
        List<PlannedOrder> plannedOrders = plannedOrderDAO.findAll();
        plannedOrderTable.setItems(FXCollections.observableList(plannedOrders));
    }

    private void clearFields() {
        plannedOrderNameField.clear();
        plannedOrderTable.getSelectionModel().clearSelection();
    }
    @FXML
    protected void onAddPlannedOrder() {
        String name = plannedOrderNameField.getText();
        String createdAt = plannedOrderCreatedAtField.getText();
        if (name == null || name.isBlank()
        || createdAt == null || createdAt.isBlank()) {
            return;
        }
        plannedOrderDAO.create(new PlannedOrder(null , name, createdAt));

        clearFields();

        loadPlannedOrders();
    }
    @FXML
    protected void onDeletePlannedOrder() {
        PlannedOrder deleted = plannedOrderTable.getSelectionModel().getSelectedItem();

        if (deleted == null) {
            return;
        }
        plannedOrderDAO.delete(deleted);

        clearFields();

        loadPlannedOrders();
    }
    @FXML
    protected void onUpdatePlannedOrder() {
        PlannedOrder selected = plannedOrderTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        String newName = plannedOrderNameField.getText();
        String createdAt = plannedOrderCreatedAtField.getText();

        if (newName == null || newName.isBlank()
                || createdAt == null || createdAt.isBlank()) {
            return;
        }
        PlannedOrder updated = new PlannedOrder(selected.getId(), newName, createdAt);

        plannedOrderDAO.update(updated);

        clearFields();

        loadPlannedOrders();
    }
    @FXML
    protected void goBackToMain() {
        try{
            FXMLLoader loader = new FXMLLoader(
                    getClass()
                            .getResource("/com/mercadoapp/mercadoapp/fxml/main-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }catch(IOException e){
            throw new RuntimeException("Erro ao voltar para a tela principal",e);
        }
    }
}
