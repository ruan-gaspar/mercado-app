package com.mercadoapp.mercadoapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private static final String URL = "jdbc:sqlite:mercado.db";
/**
 * Método para conectar ao SQLite se houver conexão. Se não houver, ele cria. */
    public static Connection connect() {
        try{
            /* a string de conexao é jdbc:sqlite:caminho-do-banco/nome-do-banco_sqlite.db */
            return DriverManager.getConnection(URL);
        }catch (SQLException e){
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}
