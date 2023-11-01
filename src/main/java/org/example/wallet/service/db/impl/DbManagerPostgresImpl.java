package org.example.wallet.service.db.impl;

import org.example.wallet.service.db.DbManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbManagerPostgresImpl implements DbManager {

    private final String username;
    private final String password;
    private final String url;
    private final String dbDriver;

    private Connection connection;

    public DbManagerPostgresImpl() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        url = resourceBundle.getString("url");
        username = resourceBundle.getString("username");
        password = resourceBundle.getString("password");
        dbDriver = resourceBundle.getString("dbDriver");
        try {
            openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void openConnection() throws SQLException {
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
