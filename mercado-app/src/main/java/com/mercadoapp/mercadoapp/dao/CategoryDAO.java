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
    public void save(Category category) {
            String sql = """
                    INSERT INTO category (name)
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
        String sql = "SELECT * FROM category";
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
}
