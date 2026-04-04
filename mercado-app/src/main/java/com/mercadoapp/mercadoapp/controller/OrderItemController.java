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

public class OrderItemController {
    /**  private Integer id;
     private Integer orderId;
     private Integer productId;

     private int quantity;
     private Double unitPrice;
     private Double subtotal;*/
    @FXML
    private Button backButton;

    @FXML
    private TableView<OrderItem> orderItemTable;

    @FXML
    private TableColumn< OrderItem, Integer> idColumn;

    @FXML
    private TableColumn<OrderItem, String> productIdColumn;

    @FXML
    private TableColumn<OrderItem, String> orderIdColumn;

    @FXML
    private TableColumn<OrderItem, Integer> quantityColumn;
    @FXML
    private TableColumn<OrderItem, String> unitPriceColumn;
    @FXML
    private TableColumn<OrderItem, String> subtotalColumn;

    @FXML
    private TextField orderItemQuantityField;

    @FXML
    private TextField orderItemUnitPriceField;

    @FXML
    private TextField orderItemSubtotalField;

    private Map<Integer, String> orderMap = new HashMap<>();

    private Map<Integer, String> productMap = new HashMap<>();

    @FXML
    private ComboBox<Order> orderComboBox;

    @FXML
    private ComboBox<Product> productComboBox;

    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {

        /** Aplicando método para formatar o texto do preço com vírgula,
         * sem necessidade de input da vírgula pelo user
         * */
        applyCurrencyMask(orderItemUnitPriceField);
        orderItemSubtotalField.setEditable(false);
        orderItemTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs,
                              oldValue,
                              newSelection) -> {
                    if (newSelection != null) {
                        orderItemUnitPriceField.setText(String.valueOf(newSelection.getUnitPrice()));
                        orderItemSubtotalField.setText(String.valueOf(newSelection.getSubtotal()));
                        orderItemQuantityField.setText(String.valueOf(newSelection.getQuantity()));
                        selectOrderInComboBox(newSelection.getOrderId());
                        selectProductInComboBox(newSelection.getProductId());
                    }
                });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        unitPriceColumn.setCellValueFactory(
                cellData -> {
                    Double unitPrice = cellData.getValue().getUnitPrice();
                    String formatted = String.format("%.2f", unitPrice).replace(".", ",");
                    return new SimpleStringProperty(formatted);
                });
        subtotalColumn.setCellValueFactory(cellData -> {
            Double subtotal = cellData.getValue().getSubtotal();
            String formatted = String.format("%.2f", subtotal).replace(".", ",");
            return new SimpleStringProperty(formatted);
        });

        loadProducts();
        loadOrders();

        productIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getProductId();
            String name = productMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
        });
        orderIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getOrderId();
            String name = orderMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
        });

        loadOrderItems();
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
    private void selectOrderInComboBox(Integer orderId) {
        for (Order order : orderComboBox.getItems()) {
            if (order.getId().equals(orderId)) {
                orderComboBox.setValue(order);
                break;
            }
        }
    }
    private void loadOrders() {
        List<Order> orders = orderDAO.findAll();

        orderComboBox.getItems().setAll(orders);

        orderMap.clear();
        for (Order o : orders) {
            orderMap.put(o.getId(), o.getMonthReference());
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
    protected void loadOrderItems() {
        List<OrderItem> orderItems = orderItemDAO.findAll();

        orderItemTable.setItems(
                FXCollections.observableArrayList(orderItems)
        );
    }
    private void clearFields() {
        orderItemQuantityField.clear();
        orderItemUnitPriceField.clear();
        orderItemSubtotalField.clear();
        productComboBox.setValue(null);
        orderComboBox.setValue(null);
        orderItemTable.getSelectionModel().clearSelection();
    }
    @FXML
    protected void onAddOrderItem() {
        String quantityText = orderItemQuantityField.getText();
        String priceText = orderItemUnitPriceField.getText();
        Product selectedProduct = productComboBox.getValue();
        Order selectedOrder = orderComboBox.getValue();

        if(quantityText == null || quantityText.isBlank()
                || priceText == null || priceText.isBlank()
                || selectedProduct == null
                || selectedOrder == null ){
            return;
        }
        try{
            Integer quantity = Integer.parseInt(quantityText.trim());
            Double unitPrice = Double.parseDouble(
                    orderItemUnitPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );
            Double subtotal = quantity * unitPrice;
            orderItemSubtotalField.setText(String.format("%.2f", subtotal).replace(".", ","));

            OrderItem orderItem = new OrderItem(
                    null,
                    selectedOrder.getId(),
                    selectedProduct.getId(),
                    quantity,
                    unitPrice,
                    subtotal
            );
            orderItemDAO.create(orderItem);

            clearFields();
            loadOrderItems();

        } catch (NumberFormatException e) {
            System.out.println("Quantidade ou preço unitário inválidos!");
        }
    }

    @FXML
    protected void onDeleteOrderItem() {
        OrderItem selected = orderItemTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        orderItemDAO.delete(selected);


        loadOrderItems();
        clearFields();
    }
    @FXML
    protected void onUpdateOrderItem() {
        OrderItem selected = orderItemTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String newQuantity = orderItemQuantityField.getText();
        String newPrice = orderItemUnitPriceField.getText();
        Product selectedProduct = productComboBox.getValue();
        Order selectedOrder = orderComboBox.getValue();

        if(newQuantity == null || newQuantity.isBlank()
                || newPrice == null || newPrice.isBlank()
                || selectedProduct == null
                || selectedOrder == null ) {
            return;
        }
        try{
            Integer quantity = Integer.parseInt(newQuantity.trim());
            Double unitPrice = Double.parseDouble(
                    orderItemUnitPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );
            Double subtotal = quantity * unitPrice;
            orderItemSubtotalField.setText(String.format("%.2f", subtotal).replace(".", ","));

            OrderItem updated = new OrderItem(
                    selected.getId(),
                    selectedOrder.getId(),
                    selectedProduct.getId(),
                    quantity,
                    unitPrice,
                    subtotal
            );
            orderItemDAO.update(updated);

            loadOrderItems();
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
