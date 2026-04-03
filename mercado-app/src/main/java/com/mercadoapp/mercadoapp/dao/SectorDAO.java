package com.mercadoapp.mercadoapp.dao;

import com.mercadoapp.mercadoapp.model.Sector;
import com.mercadoapp.mercadoapp.util.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SectorDAO {
    public void create(Sector sector) {
        String sql = """
                INSERT INTO sector_table (name)
                VALUES (?)
                """;
        try(Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, sector.getName());
            stmt.executeUpdate();

            System.out.println("Setor criado com sucesso!");
        }catch(SQLException e){
            throw new RuntimeException("Erro ao criar setor", e);
        }
    }
    public List<Sector> findAll(){
        String sql = """
                SELECT * FROM sector_table
                """;
        List<Sector> sectors = new ArrayList<>();
        try(Connection conn = SQLiteConnection.connect();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Integer id = rs.getInt("id");
                String name = rs.getString("name");

                Sector sector = new Sector(id, name);
                sectors.add(sector);
            }
        }catch (SQLException e){
            throw new RuntimeException("Erro ao carregar lista de setores!", e);
        }
        return sectors;
    }
    public void delete(Sector sector) {

        String sql = "DELETE FROM sector_table WHERE id = ?";

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, sector.getId());
            stmt.executeUpdate();

            System.out.println("Setor excluído com sucesso!");

        }catch (SQLException e){
            throw new RuntimeException("Erro ao excluir setor", e);
        }
    }

    public void update(Sector sector) {
        String sql = """
                UPDATE sector_table
                SET name=?
                WHERE id=?
                """;
        try(Connection conn = SQLiteConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, sector.getName());
            stmt.setInt(2, sector.getId());
            stmt.executeUpdate();

            System.out.println("Setor atualizado com sucesso!");
        }catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar setor", e);
        }
    }
}
