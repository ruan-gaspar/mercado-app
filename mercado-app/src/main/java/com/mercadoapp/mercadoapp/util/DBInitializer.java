package com.mercadoapp.mercadoapp.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public void initDatabase() {
    String sql = """
            CREATE TABLE IF NOT EXISTS category (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL UNIQUE)
            """;
    try(Connection conn = SQLiteConnection.connect();
    Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
        System.out.println("Tabela categoria criada com sucesso!");

    }catch(SQLException e){
        throw new RuntimeException("Erro ao criar Tabela", e);
    }
    }

}
