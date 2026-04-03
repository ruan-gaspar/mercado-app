package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.PlannedOrder;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlannedOrderDAO {
    public void create(PlannedOrder plannedOrder) {
        String sql = """
                    INSERT INTO planned_order_table (id, name, created_at)
                    VALUES (?, ?, ?)
                    """;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, plannedOrder.getId());
            stmt.setString(2, plannedOrder.getName());
            stmt.setString(3, plannedOrder.getCreatedAt());
            stmt.executeUpdate();

            System.out.println("Lista salva com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao criar lista", e);
        }
    }
    public List<PlannedOrder> findAll() {
        String sql = "SELECT * FROM planned_order_table";
        List<PlannedOrder> plannedOrders = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                String createdAt = rs.getString("created_at");

                PlannedOrder plannedOrder = new PlannedOrder(id, name, createdAt);
                plannedOrders.add(plannedOrder);
            }
        }catch(SQLException e){
            throw new RuntimeException("Erro ao buscar lista", e);
        }
        return plannedOrders;
    }
    public void delete(PlannedOrder plannedOrder) {
        String sql = "DELETE FROM planned_order_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, plannedOrder.getId());

            stmt.executeUpdate();

            System.out.println("Lista excluída com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir lista", e);
        }
    }
    public void update(PlannedOrder plannedOrder) {
        String sql = """
                UPDATE planned_order_table
                SET name=?, created_at=? 
                WHERE id=?
                """;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, plannedOrder.getName());
            stmt.setString(2, plannedOrder.getCreatedAt());
            stmt.setInt(3, plannedOrder.getId());
            stmt.executeUpdate();

            System.out.println("Lista atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar lista", e);
        }
    }
}
