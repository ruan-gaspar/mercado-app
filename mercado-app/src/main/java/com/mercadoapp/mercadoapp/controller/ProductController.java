package com.mercadoapp.mercadoapp.controller;


import com.mercadoapp.mercadoapp.dao.CategoryDAO;
import com.mercadoapp.mercadoapp.dao.ProductDAO;
import com.mercadoapp.mercadoapp.dao.SectorDAO;
import com.mercadoapp.mercadoapp.model.Category;
import com.mercadoapp.mercadoapp.model.Product;
import com.mercadoapp.mercadoapp.model.Sector;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ProductController {
    @FXML
    private Button backButton;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, String> brandColumn;

    @FXML
    private TableColumn<Product, String> categoryIdColumn;

    @FXML
    private TableColumn<Product, String> sectorIdColumn;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productBrandField;

    private Map<Integer, String> categoryMap = new HashMap<>();

    private Map<Integer, String> sectorMap = new HashMap<>();

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private ComboBox<Sector> sectorComboBox;

    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {

        /** Aplicando método para formatar o texto do preço com vírgula,
         * sem necessidade de input da vírgula pelo user */
        applyCurrencyMask(productPriceField);
        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newSelection) -> {
            if (newSelection != null) {
                productNameField.setText(newSelection.getName());
                productPriceField.setText(String.valueOf(newSelection.getPrice()));
                productBrandField.setText(newSelection.getBrand());
                selectCategoryInComboBox(newSelection.getCategoryId());
                selectSectorInComboBox(newSelection.getSectorId());
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        loadCategories();
        loadSectors();

        categoryIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getCategoryId();
            String name = categoryMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
                });

        sectorIdColumn.setCellValueFactory(cellData -> {
            Integer id = cellData.getValue().getSectorId();
            String name = sectorMap.getOrDefault(id, "N/A");
            return new SimpleStringProperty(name);
        });

        loadProducts();
    }

    private void selectCategoryInComboBox(Integer categoryId) {
        for (Category category : categoryComboBox.getItems()) {
            if (category.getId().equals(categoryId)) {
                categoryComboBox.setValue(category);
                break;
            }
        }
    }
    private void selectSectorInComboBox(Integer sectorId) {
        for (Sector sector : sectorComboBox.getItems()) {
            if (sector.getId().equals(sectorId)) {
                sectorComboBox.setValue(sector);
                break;
            }
        }
    }
    private void loadCategories() {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.findAll();

        categoryComboBox.getItems().setAll(categories);

        categoryMap.clear();
        for (Category c : categories) {
            categoryMap.put(c.getId(), c.getName());
        }
    }

    private void loadSectors() {
        SectorDAO dao = new SectorDAO();
        List<Sector> sectors = dao.findAll();

        sectorComboBox.getItems().setAll(sectors);

        sectorMap.clear();
        for (Sector s : sectors) {
            sectorMap.put(s.getId(), s.getName());
        }
    }

    protected void loadProducts() {
        List<Product> products = productDAO.findAll();

        ObservableList<Product> list = FXCollections.observableArrayList();
        list.addAll(products);

        productTable.setItems(list);
    }
    private void clearFields() {
        productNameField.clear();
        productPriceField.clear();
        productBrandField.clear();
        categoryComboBox.setValue(null);
        sectorComboBox.setValue(null);
        productTable.getSelectionModel().clearSelection();
    }
    @FXML
    protected void onAddProduct() {
        String nameText = productNameField.getText();
        String priceText = productPriceField.getText();
        String brandText = productBrandField.getText();
        Category selectedCategory = categoryComboBox.getValue();
        Sector selectedSector = sectorComboBox.getValue();

        if(nameText == null || nameText.isBlank()
                || priceText == null || priceText.isBlank()
                || brandText == null || brandText.isBlank()
                || selectedCategory == null
                || selectedSector == null ){
            return;
        }
        try{
            Double price = Double.parseDouble(
                    productPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );

            Product product = new Product(null, nameText, price, brandText, selectedCategory.getId(), selectedSector.getId());

            productDAO.create(product);

            clearFields();
            loadProducts();
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido");
        }
    }

    @FXML
    protected void onDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        productDAO.delete(selected);


        loadProducts();
        clearFields();
    }
    @FXML
    protected void onUpdateProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        String newName = productNameField.getText();
        String newPrice = productPriceField.getText();
        String newBrand = productBrandField.getText();
        Category selectedCategory = categoryComboBox.getValue();
        Sector selectedSector = sectorComboBox.getValue();

        if(newName == null || newName.isBlank()
        || newPrice == null || newPrice.isBlank()
            || newBrand == null || newBrand.isBlank()
        || selectedCategory == null
        || selectedSector == null ) {
            return;
        }
        try{
            Double price = Double.parseDouble(
                    productPriceField.getText()
                            .replace(",", ".")
                            .trim()
            );
            Product updated = new Product(
                    selected.getId(),
                    newName,
                    price,
                    newBrand,
                    selectedCategory.getId(),
                    selectedSector.getId()
            );
            productDAO.update(updated);

            loadProducts();
            clearFields();
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido");
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
            throw new RuntimeException("Erro ao voltar para a tela principal", e);
        }

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
}
