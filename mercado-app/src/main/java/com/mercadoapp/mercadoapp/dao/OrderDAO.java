package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.Order;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public void create(Order order) {
        String sql = """
                INSERT INTO order_table (month_reference, created_at, total_amount) 
                VALUES (?, ?, ?)
                """;
        try (Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, order.getMonthReference());
            stmt.setString(2, order.getCreatedAt().toString());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.executeUpdate();

            System.out.println("Nova compra criada com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar compra", e);
        }
    }
    public List<Order> findAll() {
        String sql = "SELECT * FROM order_table";
        List<Order> orders = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
            while (rs.next()){
                Integer id = rs.getInt("id");
                String monthReference = rs.getString("month_reference");
                LocalDateTime createdAt = LocalDateTime.parse(rs.getString("created_at"));
                double totalAmount = rs.getDouble("total_amount");

                Order order = new Order(id, monthReference, createdAt, totalAmount);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar compra", e);
        }
        return orders;
    }
    public void delete(Order order) {
        String sql = "DELETE FROM order_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, order.getId());
            stmt.executeUpdate();

            System.out.println("Compra excluída com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir compra", e);
        }
    }
    public void update(Order order) {
        String sql = """
                UPDATE order_table
                SET month_reference=?, created_at=?, total_amount=?
                WHERE id=?
                """;

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, order.getMonthReference());
            stmt.setString(2, order.getCreatedAt().toString());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setInt(4, order.getId());
            stmt.executeUpdate();

            System.out.println("Compra atualizada com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar compra", e);
        }
    }
}
