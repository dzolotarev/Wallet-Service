package org.example.wallet.repository.db;

import org.example.wallet.domain.User;
import org.example.wallet.repository.UserRepository;
import org.example.wallet.service.db.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserRepositoryDBImpl implements UserRepository {

    private static final String FIND_LOGIN = "SELECT id, password, name, createdat FROM wallet.users WHERE login=?";
    private static final String INSERT_USER = "INSERT INTO wallet.users (login, password, name) VALUES (?, ?, ?)";

    private static UserRepositoryDBImpl INSTANCE;

    private DbManager dbManager;

    private UserRepositoryDBImpl() {
    }

    private UserRepositoryDBImpl(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Repository not initialized");
        }
        return INSTANCE;
    }

    public static void init(DbManager dbManager) {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryDBImpl(dbManager);
        }
    }

    @Override
    public boolean add(String login, String password, String name) throws SQLException {
        if (findUserByLogin(login) == null) {
            try (PreparedStatement preparedStatement = dbManager.prepareStatement(INSERT_USER)) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, name); //ToDo check
                preparedStatement.executeUpdate();
            }
            return true;
        }
        return false;
    }

    @Override
    public User findUserByLogin(String login) throws SQLException {
        User user = null;
        try (PreparedStatement preparedStatement = dbManager.prepareStatement(FIND_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                Date createdAt = new Date(resultSet.getTimestamp("createdat").getTime());
                user = User.Factory.create(id, login, password); // ToDO check for null

                user.setName(name);
                user.setPassword(password);
                user.setCreatedAt(createdAt);
            }
        }
        return user;
    }
}
