package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.Category;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public void create(Category category) {
            String sql = """
                    INSERT INTO category_table (name)
                    VALUES (?)
                    """;
        try (Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, category.getName());
            stmt.executeUpdate();

            System.out.println("Salvo com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir uma categoria", e);
        }
    }
    public List<Category> findAll() {
        String sql = "SELECT * FROM category_table";
        List<Category> categories = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");

                Category category = new Category(id, name);
                categories.add(category);
            }
        }catch(SQLException e){
            throw new RuntimeException("Erro ao buscar uma categoria", e);
        }
        return categories;
    }
    public void deleteById(Category category) {
        String sql = "DELETE FROM category_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, category.getId());
            stmt.executeUpdate();

            System.out.println("Excluído com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir categoria", e);
        }
    }
    public void update(Category category) {
        String sql = """
                UPDATE category_table
                SET name=?
                WHERE id=?
                """;

        try (Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();

            System.out.println("Atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria", e);
        }
    }
}
