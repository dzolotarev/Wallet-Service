package org.example.wallet.service.db.impl;

import org.example.wallet.service.db.DbManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DbManagerPostgresImpl implements DbManager {

    private final String username;
    private final String password;
    private final String url;

    private Connection connection;

    public DbManagerPostgresImpl() {
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/db.properties");
            Properties properties = new Properties();
            properties.load(fis);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            openConnection();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (connection == null) {
            throw new IllegalStateException();
        }
        return connection.prepareStatement(sql);
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
