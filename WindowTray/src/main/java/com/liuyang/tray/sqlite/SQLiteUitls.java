package com.liuyang.tray.sqlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * SQLite数据库工具
 *
 * @author liuyang
 * @since 2025/06/25
 */
public class SQLiteUitls {

    public static final Logger logger = LoggerFactory.getLogger(SQLiteUitls.class);


    // 数据库文件路径 (如果不存在会自动创建)
    private static final String DB_URL = "jdbc:sqlite:machine-tray.db";

    static {
        // 创建数据库连接
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            logger.info("init sqlite db");

            // 创建表
            createTables(conn);


        } catch (SQLException e) {
            logger.error("init db error: " + e.getMessage());
        }
    }

    /**
     * 创建数据表
     */
    private static void createTables(Connection conn) throws SQLException {
        String createUserTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                email TEXT NOT NULL,
                age INTEGER,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;

        String createProductTableSQL = """
            CREATE TABLE IF NOT EXISTS products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                stock INTEGER DEFAULT 0,
                description TEXT
            );
            """;

        String createTranslateTableSQL = """
               CREATE TABLE IF NOT EXISTS translate (
                   id INTEGER PRIMARY KEY AUTOINCREMENT,
                   key TEXT,
                   value TEXT,
                   create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               );
                """;

        try (Statement stmt = conn.createStatement()) {
            // 执行创建表的SQL语句
            stmt.execute(createUserTableSQL);
            stmt.execute(createProductTableSQL);
            stmt.execute(createTranslateTableSQL);
            logger.info("create tables success");
        }
    }


    /**
     * 插入翻译数据
     * @Param key key
     * @Param value value
     *
     * @return true/false
     */
    public static boolean insertTranslateData(String key, String value){
        String insertUserSQL = """
            INSERT INTO translate (key, value)
            VALUES (?, ?);
            """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {

            pstmt.setString(1, key);
            pstmt.setString(2, value);

            logger.info("SQL: {}", pstmt.toString());
            int i = pstmt.executeUpdate();
            logger.info("result: {}", i);
        }catch (SQLException e) {
            logger.error("insert translate data error: " + e.getMessage());
            logger.error("INSERT INTO translate (key, value) VALUES ({}, );", key, value);
            return false;
        }

        return true;
    }

    /**
     * 插入数据
     */
    private static void insertData(Connection conn) throws SQLException {
        // 插入用户数据
        String insertUserSQL = """
            INSERT INTO users (username, email, age)
            VALUES (?, ?, ?);
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
            // 批量插入用户
            pstmt.setString(1, "john_doe");
            pstmt.setString(2, "john@example.com");
            pstmt.setInt(3, 30);
            pstmt.executeUpdate();

            pstmt.setString(1, "jane_smith");
            pstmt.setString(2, "jane@example.com");
            pstmt.setInt(3, 25);
            pstmt.executeUpdate();

            System.out.println("用户数据插入成功");
        }

        // 插入产品数据
        String insertProductSQL = """
            INSERT INTO products (name, price, stock, description)
            VALUES (?, ?, ?, ?);
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(insertProductSQL)) {
            // 批量插入产品
            Object[][] products = {
                    {"Laptop", 999.99, 10, "High performance laptop"},
                    {"Smartphone", 699.99, 15, "Latest model smartphone"},
                    {"Headphones", 99.99, 30, "Noise cancelling headphones"}
            };

            for (Object[] product : products) {
                pstmt.setString(1, (String) product[0]);
                pstmt.setDouble(2, (Double) product[1]);
                pstmt.setInt(3, (Integer) product[2]);
                pstmt.setString(4, (String) product[3]);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("产品数据插入成功");
        }
    }

    /**
     * 查询并显示数据
     */
    private static void queryData(Connection conn) throws SQLException {
        // 查询用户数据
        String queryUsersSQL = "SELECT * FROM users;";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryUsersSQL)) {

            System.out.println("\n用户列表:");
            System.out.println("ID\tUsername\tEmail\t\t\tAge\tCreated At");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "\t" +
                                rs.getString("username") + "\t" +
                                rs.getString("email") + "\t" +
                                rs.getInt("age") + "\t" +
                                rs.getString("created_at")
                );
            }
        }

        // 查询产品数据
        String queryProductsSQL = "SELECT * FROM products;";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(queryProductsSQL)) {

            System.out.println("\n产品列表:");
            System.out.println("ID\tName\t\tPrice\tStock\tDescription");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + "\t" +
                                rs.getString("name") + "\t" +
                                rs.getDouble("price") + "\t" +
                                rs.getInt("stock") + "\t" +
                                rs.getString("description")
                );
            }
        }
    }
}
