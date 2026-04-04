package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.OrderDAO;
import com.mercadoapp.mercadoapp.model.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, String> monthReferenceColumn;

    @FXML
    private TableColumn<Order, String> createdAtColumn;

    @FXML
    private TableColumn<Order, String> totalAmountColumn;

    @FXML
    private TextField orderMonthReferenceField;

    @FXML
    private TextField orderTotalAmountField;

    private final OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        orderTotalAmountField.setEditable(false);

        orderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newSelection) -> {
            if (newSelection != null) {
                orderMonthReferenceField.setText(newSelection.getMonthReference());
                orderTotalAmountField.setText(
                        String.format("%.2f", newSelection.getTotalAmount()).replace(".", ",")
                );
            }
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        monthReferenceColumn.setCellValueFactory(new PropertyValueFactory<>("monthReference"));

        createdAtColumn.setCellValueFactory(cellData -> {
            LocalDateTime createdAt = cellData.getValue().getCreatedAt();
            return new SimpleStringProperty(createdAt.toString());
        });

        totalAmountColumn.setCellValueFactory(cellData -> {
            double total = cellData.getValue().getTotalAmount();
            String formatted = String.format("%.2f", total).replace(".", ",");
            return new SimpleStringProperty(formatted);
        });

        loadOrders();
    }

    private void loadOrders() {
        List<Order> orders = orderDAO.findAll();
        orderTable.setItems(FXCollections.observableArrayList(orders));
    }

    private void clearFields() {
        orderMonthReferenceField.clear();
        orderTotalAmountField.clear();
        orderTable.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onAddOrder() {
        String monthReference = orderMonthReferenceField.getText();

        if (monthReference == null || monthReference.isBlank()) {
            return;
        }

        Order order = new Order(
                null,
                monthReference.trim(),
                LocalDateTime.now(),
                0.0
        );

        orderDAO.create(order);

        loadOrders();
        clearFields();
    }

    @FXML
    protected void onDeleteOrder() {
        Order selected = orderTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        orderDAO.delete(selected);

        loadOrders();
        clearFields();
    }

    @FXML
    protected void onUpdateOrder() {
        Order selected = orderTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        String newMonthReference = orderMonthReferenceField.getText();

        if (newMonthReference == null || newMonthReference.isBlank()) {
            return;
        }

        Order updated = new Order(
                selected.getId(),
                newMonthReference.trim(),
                selected.getCreatedAt(),
                selected.getTotalAmount()
        );

        orderDAO.update(updated);

        loadOrders();
        clearFields();
    }

    @FXML
    protected void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mercadoapp/mercadoapp/fxml/main-view.fxml")
            );
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao voltar para a tela principal!", e);
        }
    }
}