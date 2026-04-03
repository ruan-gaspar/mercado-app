package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.OrderItem;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    public void create(OrderItem orderItem) {
        String sql = """
                    INSERT INTO order_item_table (order_id, product_id, quantity, unit_price, subtotal)
                    VALUES (?, ?, ?, ?, ?)
                    """;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getUnitPrice());
            stmt.setDouble(5, orderItem.getSubtotal());
            stmt.executeUpdate();

            System.out.println("Salvo com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item", e);
        }
    }
    public List<OrderItem> findAll() {
        String sql = "SELECT * FROM order_item_table";
        List<OrderItem> orderItems = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer orderId = rs.getInt("order_id");
                Integer productId = rs.getInt("product_id");
                Integer quantity = rs.getInt("quantity");
                Double unitPrice = rs.getDouble("unit_price");
                Double subtotal = rs.getDouble("subtotal");
                OrderItem orderItem = new OrderItem(id, orderId, productId, quantity, unitPrice, subtotal);
                orderItems.add(orderItem);
            }
        }catch(SQLException e){
            throw new RuntimeException("Erro ao buscar itens", e);
        }
        return orderItems;
    }
    public void delete(OrderItem orderItem) {
        String sql = "DELETE FROM order_item_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderItem.getId());
            stmt.executeUpdate();

            System.out.println("Excluído com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item", e);
        }
    }
    public void update(OrderItem orderItem) {
        String sql = """
                UPDATE order_item_table
                SET order_id=?, product_id=?, quantity=? , unit_price=?, subtotal=?
                WHERE id=?
                """;

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getUnitPrice());
            stmt.setDouble(5, orderItem.getSubtotal());
            stmt.setInt(6, orderItem.getId());
            stmt.executeUpdate();

            System.out.println("Item atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item", e);
        }
    }
}
