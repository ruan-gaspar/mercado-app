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
        SectorDAO dao = new SectorDAO();
        List<Sector> sectors = dao.findAll();

        ObservableList<Sector> list = FXCollections.observableArrayList();
        list.addAll(sectors);

        sectorTable.setItems(list);
    }

    @FXML
    protected void onAddSector() {
        String name = sectorNameField.getText();
        if (name == null || name.isBlank()) {
            return;
        }
        SectorDAO dao = new SectorDAO();
        dao.create(new Sector(null , name));

        sectorNameField.clear();

        loadSectors();
    }

    @FXML
    protected void onDeleteSector() {
        Sector selected = sectorTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        SectorDAO dao = new SectorDAO();
        dao.delete(selected);

        sectorNameField.clear();

        loadSectors();
    }
    @FXML
    protected void onUpdateCategory() {
        Sector selected = sectorTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }
        String newName = sectorNameField.getText();

        if (newName == null || newName.isBlank()) {
            return;
        }
        selected.setName(newName);

        SectorDAO dao = new SectorDAO();
        dao.update(selected);

        sectorNameField.clear();

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
