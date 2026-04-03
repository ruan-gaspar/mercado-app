package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.PlannedOrderItem;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlannedOrderItemDAO {
    public void create(PlannedOrderItem plannedOrderItem) {
        String sql = """
                    INSERT INTO planned_order_item_table (id, product_id, planned_order_id, planned_quantity, expected_price)
                    VALUES (?, ?, ?, ?, ?)
                    """;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, plannedOrderItem.getId());
            stmt.setInt(2, plannedOrderItem.getProductId());
            stmt.setInt(3, plannedOrderItem.getPlannedOrderId());
            stmt.setInt(4, plannedOrderItem.getPlannedQuantity());
            stmt.setDouble(5, plannedOrderItem.getExpectedPrice());
            stmt.executeUpdate();

            System.out.println("Item salva com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao criar item", e);
        }
    }
    public List<PlannedOrderItem> findAll() {
        String sql = "SELECT * FROM planned_order_item_table";
        List<PlannedOrderItem> plannedOrderItems = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer productId = rs.getInt("product_id");
                Integer plannedOrderId = rs.getInt("planned_order_id");
                Integer plannedQuantity = rs.getInt("planned_quantity");
                Double expectedPrice = rs.getDouble("expected_price");


                PlannedOrderItem plannedOrderItem = new PlannedOrderItem(id, productId, plannedOrderId, plannedQuantity, expectedPrice);
                plannedOrderItems.add(plannedOrderItem);
            }
        }catch(SQLException e){
            throw new RuntimeException("Erro ao buscar item", e);
        }
        return plannedOrderItems;
    }
    public void delete(PlannedOrderItem plannedOrderItem) {
        String sql = "DELETE FROM planned_order_item_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, plannedOrderItem.getId());

            stmt.executeUpdate();

            System.out.println("Item excluído com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item", e);
        }
    }
    public void update(PlannedOrderItem plannedOrderItem) {
        String sql = """
                UPDATE planned_order_item_table
                SET product_id=?, planned_order_id=?, planned_quantity=?, expected_price=?  
                WHERE id=?
                """;
        // private Integer id;
        //    private Integer productId;
        //    private Integer plannedOrderId;
        //    private int plannedQuantity;
        //    private double expectedPrice;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, plannedOrderItem.getProductId());
            stmt.setInt(2, plannedOrderItem.getPlannedOrderId());
            stmt.setInt(3, plannedOrderItem.getPlannedQuantity());
            stmt.setDouble(4, plannedOrderItem.getExpectedPrice());
            stmt.executeUpdate();

            System.out.println("Item atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item", e);
        }
    }
}
