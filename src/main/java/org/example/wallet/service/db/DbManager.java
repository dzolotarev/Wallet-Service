package org.example.wallet.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DbManager {

    void openConnection() throws SQLException;

    PreparedStatement prepareStatement(String sql) throws SQLException;

    void closeConnection();

    Connection getConnection();
}
