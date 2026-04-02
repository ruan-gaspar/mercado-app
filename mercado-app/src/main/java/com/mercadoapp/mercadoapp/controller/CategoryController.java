package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.CategoryDAO;
import com.mercadoapp.mercadoapp.model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;

public class CategoryController {
    @FXML
    private Button backButton;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> idColumn;

    @FXML
    private TableColumn<Category, String> nameColumn;

    @FXML
    private TextField categoryNameField;

    @FXML
    public void initialize() {
        categoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newSelection) -> {
           if (newSelection != null) {
               categoryNameField.setText(newSelection.getName());
           }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCategories();
    }
    protected void loadCategories() {
        CategoryDAO dao = new CategoryDAO();
        List<Category> categories = dao.findAll();

        ObservableList<Category> list = FXCollections.observableArrayList();
        list.addAll(categories);

        categoryTable.setItems(list);
    }
    @FXML
    protected void onAddCategory() {
        String name = categoryNameField.getText();
        if (name == null || name.isBlank()) {
            return;
        }
        CategoryDAO dao = new CategoryDAO();
        dao.create(new Category(null , name));

        categoryNameField.clear();

        loadCategories();
    }
    @FXML
    protected void onDeleteCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        CategoryDAO dao = new CategoryDAO();
        dao.deleteById(selected);

        categoryNameField.clear();

        loadCategories();
    }
    @FXML
    protected void onUpdateCategory() {
        Category selected = categoryTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        String newName = categoryNameField.getText();

        if (newName == null || newName.isBlank()) {
            return;
        }
        selected.setName(newName);

        CategoryDAO dao = new CategoryDAO();
        dao.update(selected);

        categoryNameField.clear();

        loadCategories();
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
