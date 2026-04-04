package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.*;
import com.mercadoapp.mercadoapp.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlannedOrderItemController {

    @FXML
    private Button backButton;

    @FXML
    private TableView<PlannedOrderItem> plannedOrderItemTable;

    @FXML
    private TableColumn<PlannedOrderItem, Integer> idColumn;

    @FXML
    private TableColumn<PlannedOrderItem, String> productIdColumn;

    @FXML
    private TableColumn<PlannedOrderItem, String> plannedOrderIdColumn;

    @FXML
    private TableColumn<PlannedOrderItem, Integer> plannedQuantityColumn;

    @FXML
    private TableColumn<PlannedOrderItem, String> expectedPriceColumn;

    @FXML
    private TextField plannedOrderItemQuantityField;

    @FXML
    private TextField plannedOrderItemExpectedPriceField;

    private Map<Integer, String> productMap = new HashMap<>();

    private Map<Integer, String> plannedOrderMap = new HashMap<>();

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private ComboBox<PlannedOrder> plannedOrderComboBox;

    private final PlannedOrderItemDAO plannedOrderItemDAO = new PlannedOrderItemDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final PlannedOrderDAO plannedOrderDAO = new PlannedOrderDAO();

    @FXML
    public void initialize() {

        /** Aplicando método para formatar o texto do preço com vírgula,
         * sem necessidade de input da vírgula pelo user
         * */
        applyCurrencyMask(plannedOrderItemExpectedPriceField);
        plannedOrderItemTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs,
                              oldValue,
                              newSelection) -> {
            if (newSelection != null) {
                plannedOrderItemExpectedPriceField
                        .setText(String.valueOf(newSelection.getExpectedPrice()));
                plannedOrderItemQuantityField
                        .setText(String.valueOf(newSelection.getPlannedQuantity()));
                selectProductInComboBox(newSelection.getProductId());
                selectPlannedOrderInComboBox(newSelection.getPlannedOrderId());
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        /** productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        * plannedOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("plannedOrderId"));
        */
        plannedQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("plannedQuantity"));
        expectedPriceColumn.setCellValueFactory(
                cellData -> {
         Double price = cellData.getValue().getExpectedPrice();
         String formatted = String.format("%.2f", price).replace(".", ",");
         return new SimpleStringProperty(formatted);
        });
        loadProducts();
        loadPlannedOrders();

        productIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getProductId();
            String name = productMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
        });

        plannedOrderIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getPlannedOrderId();
            String name = plannedOrderMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
        });

        loadPlannedOrderItems();
    }
    private void applyCurrencyMask(TextField field) {

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                return;
            }
            String digits = newValue.replaceAll("[^\\d]", "");

            if (digits.isEmpty()) {
                field.setText("");
                return;
            }
            double value = Double.parseDouble(digits) / 100;
            String formatted = String.format("%.2f", value).replace(".", ",");
            if (formatted.equals(newValue)) {
                return;
            }

            field.setText(formatted);

            field.positionCaret(field.getText().length());
        });
    }
    private void selectProductInComboBox(Integer productId) {
        for (Product product : productComboBox.getItems()) {
            if (product.getId().equals(productId)) {
                productComboBox.setValue(product);
                break;
            }
        }
    }
    private void selectPlannedOrderInComboBox(Integer plannedOrderId) {
        for (PlannedOrder plannedOrder : plannedOrderComboBox.getItems()) {
            if (plannedOrder.getId().equals(plannedOrderId)) {
                plannedOrderComboBox.setValue(plannedOrder);
                break;
            }
        }
    }
    private void loadPlannedOrders() {
        List<PlannedOrder> plannedOrders = plannedOrderDAO.findAll();

        plannedOrderComboBox.getItems().setAll(plannedOrders);

        plannedOrderMap.clear();
        for (PlannedOrder p : plannedOrders) {
            plannedOrderMap.put(p.getId(), p.getName());
        }
    }

    private void loadProducts() {
        List<Product> products = productDAO.findAll();

        productComboBox.getItems().setAll(products);

        productMap.clear();
        for (Product p : products) {
            productMap.put(p.getId(), p.getName());
        }
    }
    protected void loadPlannedOrderItems() {
        List<PlannedOrderItem> plannedOrderItems = plannedOrderItemDAO.findAll();

        plannedOrderItemTable.setItems(
                FXCollections.observableArrayList(plannedOrderItems)
        );
    }
    private void clearFields() {
        plannedOrderItemQuantityField.clear();
        plannedOrderItemExpectedPriceField.clear();
        productComboBox.setValue(null);
        plannedOrderComboBox.setValue(null);
        plannedOrderItemTable.getSelectionModel().clearSelection();
    }
    @FXML
    protected void onAddPlannedOrderItem() {
        String quantityText = plannedOrderItemQuantityField.getText();
        String priceText = plannedOrderItemExpectedPriceField.getText();
        Product selectedProduct = productComboBox.getValue();
        PlannedOrder selectedPlannedOrder = plannedOrderComboBox.getValue();

        if(quantityText == null || quantityText.isBlank()
                || priceText == null || priceText.isBlank()
                || selectedProduct == null
                || selectedPlannedOrder == null ){
            return;
        }
        try{
            Integer quantity = Integer.parseInt(quantityText.trim());
            Double price = Double.parseDouble(
                    plannedOrderItemExpectedPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );

            PlannedOrderItem plannedOrderItem = new PlannedOrderItem(
                    null,
                    selectedProduct.getId(),
                    selectedPlannedOrder.getId(),
                    quantity,
                    price
            );

            plannedOrderItemDAO.create(plannedOrderItem);

            clearFields();
            loadPlannedOrderItems();

        } catch (NumberFormatException e) {
            System.out.println("Quantidade ou preço inválidos!");
        }
    }

    @FXML
    protected void onDeletePlannedOrderItem() {
        PlannedOrderItem selected = plannedOrderItemTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        plannedOrderItemDAO.delete(selected);


        loadPlannedOrderItems();
        clearFields();
    }
    @FXML
    protected void onUpdatePlannedOrderItem() {
        PlannedOrderItem selected = plannedOrderItemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String newQuantity = plannedOrderItemQuantityField.getText();
        String newPrice = plannedOrderItemExpectedPriceField.getText();
        Product selectedProduct = productComboBox.getValue();
        PlannedOrder selectedPlannedOrder = plannedOrderComboBox.getValue();

        if(newQuantity == null || newQuantity.isBlank()
                || newPrice == null || newPrice.isBlank()
                || selectedProduct == null
                || selectedPlannedOrder == null ) {
            return;
        }
        try{
            Integer quantity = Integer.parseInt(newQuantity.trim());
            Double price = Double.parseDouble(
                    plannedOrderItemExpectedPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );
            PlannedOrderItem updated = new PlannedOrderItem(
                    selected.getId(),
                    selectedProduct.getId(),
                    selectedPlannedOrder.getId(),
                    quantity,
                    price
            );
            plannedOrderItemDAO.update(updated);

            loadPlannedOrderItems();
            clearFields();

        } catch (NumberFormatException e) {
            System.out.println("Quantidade ou preço inválidos!");
        }
    }
    @FXML
    protected void goBackToMain() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass()
                            .getResource("/com/mercadoapp/mercadoapp/fxml/main-view.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao voltar para a tela principal!", e);
        }

    }
}
