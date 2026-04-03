package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.CategoryDAO;
import com.mercadoapp.mercadoapp.dao.ProductDAO;
import com.mercadoapp.mercadoapp.dao.SectorDAO;
import com.mercadoapp.mercadoapp.model.Category;
import com.mercadoapp.mercadoapp.model.Product;
import com.mercadoapp.mercadoapp.model.Sector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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
    private TableColumn<Product, Integer> categoryIdColumn;

    @FXML
    private TableColumn<Product, Integer> sectorIdColumn;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productBrandField;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private ComboBox<Sector> sectorComboBox;

    private final ProductDAO productDAO = new ProductDAO();

    @FXML
    public void initialize() {
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
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        sectorIdColumn.setCellValueFactory(new PropertyValueFactory<>("sectorId"));

        loadCategories();
        loadSectors();
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
        categoryComboBox.getItems().setAll(dao.findAll());
    }

    private void loadSectors() {
        SectorDAO dao = new SectorDAO();
        sectorComboBox.getItems().setAll(dao.findAll());
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
            Double price = Double.parseDouble(priceText);

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
            Double price = Double.parseDouble(newPrice);

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
}
