package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.Order;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public void create(Order order) {
        String sql = """
                INSERT INTO order_table (name, created_at, total_amount) 
                VALUES (?, ?, ?)
                """;
        try (Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, order.getName());
            stmt.executeUpdate();

            System.out.println("Novo resumo criado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar resumo de compra", e);
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
                String name = rs.getString("name");
                String createdAt = rs.getString("created_at");
                double totalAmount = rs.getDouble("total_amount");

                Order order = new Order(id, name, createdAt, totalAmount);
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao retornar lista dos resumos de compras", e);
        }
        return orders;
    }
    public void delete(Order order) {
        String sql = "DELETE FROM order_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, order.getId());
            stmt.executeUpdate();

            System.out.println("Excluído com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categoria", e);
        }
    }
    public void update(Order order) {
        String sql = """
                UPDATE order_table
                SET name=?, created_at=?, total_amount=?
                WHERE id=?
                """;

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, order.getName());
            stmt.setString(2, order.getCreatedAt());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setInt(4, order.getId());
            stmt.executeUpdate();

            System.out.println("Atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar compra", e);
        }
    }
}
