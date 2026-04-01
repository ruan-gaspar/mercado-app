package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.CategoryDAO;
import com.mercadoapp.mercadoapp.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController {
    protected void loadCategories() {
        CategoryDAO dao = new CategoryDAO();

        ObservableList<Category> list = FXCollections.observableArrayList();
        list.addAll(dao.findAll());

        categoryTable.setItems(list);
    }
    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> idColumn;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCategories();
    }
    @FXML
    private TextField categoryNameField;
    @FXML
    protected void onAddCategory() {
        String name = categoryNameField.getText();
        if (name == null || name.isBlank()) {
            return;
        }
        CategoryDAO dao = new CategoryDAO();
        dao.save(new Category(null , name));

        categoryNameField.clear();

    }
    @FXML
    protected void onDeleteCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        CategoryDAO dao = new CategoryDAO();
        dao.delete(selected);

        categoryNameField.clear();
    }
}
