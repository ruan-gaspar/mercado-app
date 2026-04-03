package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.Product;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public void create(Product product) {
        String sql = """
    INSERT INTO product_table (name, price, brand, category_id, sector_id) 
    VALUES (?, ?, ?, ?, ?)
    """;
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
              stmt.setString(1, product.getName());
              stmt.setDouble(2, product.getPrice());
              stmt.setString(3, product.getBrand());
              stmt.setInt(4, product.getCategoryId());
              stmt.setInt(5, product.getSectorId());

              stmt.executeUpdate();

             System.out.println("Produto salvo com sucesso!");
          }catch (SQLException e) {
              throw new RuntimeException("Erro ao salvar produto", e);
          }
    }
    public List<Product> findAll() {
        String sql = "SELECT * FROM product_table";
        List<Product> products = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String brand = rs.getString("brand");
                Integer categoryId = rs.getInt("category_id");
                Integer sectorId = rs.getInt("sector_id");

                Product product = new Product(id, name, price, brand, categoryId, sectorId);
                products.add(product);
            }
        }catch(SQLException e){
            throw new RuntimeException("Erro ao buscar um produto", e);
        }
        return products;
    }
    public void delete(Product product) {
        String sql = "DELETE FROM product_table WHERE id = ?";
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, product.getId());
            stmt.executeUpdate();

            System.out.println("Excluído com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto", e);
        }
    }
    public void update(Product product) {
        String sql = """
                UPDATE product_table
                SET name=?, price=?, brand=?, category_id=?, sector_id=?
                WHERE id=?
                
                """;

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getBrand());
            stmt.setInt(4, product.getCategoryId());
            stmt.setInt(5, product.getSectorId());
            stmt.setInt(6, product.getId());

            stmt.executeUpdate();

            System.out.println("Produto atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto", e);
        }
    }
}
