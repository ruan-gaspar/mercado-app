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
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCategories();
    }
    protected void loadCategories() {
        CategoryDAO dao = new CategoryDAO();

        ObservableList<Category> list = FXCollections.observableArrayList();
        list.addAll(dao.findAll());

        categoryTable.setItems(list);
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

        loadCategories();
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
