package com.mercadoapp.mercadoapp.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public void initDatabase() {
        createCategoryTable();
        createSectorTable();
        createProductTable();
        createOrderTable();
        createOrderItemTable();
        createPlannedOrderTable();
        createPlannedOrderItemTable();
    }
    private void createCategoryTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS category_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL
            )
        """;
        execute(sql, "category_table");
    }
    private void createSectorTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS sector_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL
            )
        """;
        execute(sql, "sector_table");
    }
    private void createProductTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS product_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            price REAL NOT NULL,
            brand TEXT NOT NULL,
            category_id INTEGER NOT NULL,
            sector_id INTEGER NOT NULL,
            FOREIGN KEY(category_id) REFERENCES category_table(id),
            FOREIGN KEY(sector_id) REFERENCES sector_table(id)
            )
        """;
        execute(sql, "product_table");
    }
    private void createOrderTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS order_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            created_at TEXT NOT NULL,
            total_amount REAL NOT NULL
            )
        """;
        execute(sql, "order_table");
    }
    private void createOrderItemTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS order_item_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            order_id INTEGER NOT NULL,
            product_id INTEGER NOT NULL,
            quantity INTEGER NOT NULL,
            unit_price REAL NOT NULL,
            subtotal REAL NOT NULL,
            FOREIGN KEY(product_id) REFERENCES product_table(id),
            FOREIGN KEY(order_id) REFERENCES order_table(id)
            )
        """;
        execute(sql, "order_item_table");
    }
    //---
    private void createPlannedOrderTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS planned_order_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            created_at TEXT NOT NULL
            )
        """;
        execute(sql, "planned_order_table");
    }
    private void createPlannedOrderItemTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS planned_order_item_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            planned_order_id INTEGER NOT NULL,
            product_id INTEGER NOT NULL, 
            planned_quantity INTEGER NOT NULL,
            expected_price REAL NOT NULL,
            FOREIGN KEY(planned_order_id) REFERENCES planned_order_table(id),
            FOREIGN KEY(product_id) REFERENCES product_table(id)
            )
        """;
        execute(sql, "planned_order_item_table");
    }

    private void execute(String sql, String tableName){
        try(Connection conn = SQLiteConnection.connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);

            System.out.println("Tabela: " + tableName + " criada com sucesso!");

        }catch(SQLException e){
            throw new RuntimeException("Erro ao criar Tabela: " + tableName, e);
        }
    }
}





