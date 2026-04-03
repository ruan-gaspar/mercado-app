package com.mercadoapp.mercadoapp.controller;

import com.mercadoapp.mercadoapp.dao.SectorDAO;
import com.mercadoapp.mercadoapp.model.Sector;
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

public class SectorController {
    @FXML
    private Button backButton;

    @FXML
    private TableView<Sector> sectorTable;

    @FXML
    private TableColumn<Sector, Integer> idColumn;

    @FXML
    private TableColumn<Sector, String> nameColumn;

    @FXML
    private TextField sectorNameField;

    private final SectorDAO sectorDAO = new SectorDAO();

    @FXML
    public void initialize() {
        sectorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newSelection) -> {
            if (newSelection != null) {
                sectorNameField.setText(newSelection.getName());
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadSectors();
    }
    protected void loadSectors() {
        List<Sector> sectors = sectorDAO.findAll();
        sectorTable.setItems(FXCollections.observableList(sectors));
    }
    private void clearFields() {
        sectorNameField.clear();
        sectorTable.getSelectionModel().clearSelection();
    }

    @FXML
    protected void onAddSector() {
        String name = sectorNameField.getText();
        if (name == null || name.isBlank()) {
            return;
        }
        sectorDAO.create(new Sector(null , name));

        clearFields();

        loadSectors();
    }

    @FXML
    protected void onDeleteSector() {
        Sector selected = sectorTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        sectorDAO.delete(selected);

        clearFields();
        loadSectors();
    }
    @FXML
    protected void onUpdateSector() {
        Sector selected = sectorTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        String newName = sectorNameField.getText();

        if (newName == null || newName.isBlank()) {
            return;
        }
        Sector updated = new Sector(selected.getId(), newName);

        sectorDAO.update(updated);
        clearFields();

        loadSectors();
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
