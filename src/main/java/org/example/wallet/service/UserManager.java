package org.example.wallet.service;

import org.example.wallet.domain.User;
import org.example.wallet.repository.AccountRepository;
import org.example.wallet.repository.UserRepository;
import org.example.wallet.repository.db.AccountRepositoryDBImpl;
import org.example.wallet.repository.db.UserRepositoryDBImpl;

import java.io.IOException;
import java.sql.SQLException;

public class UserManager {
    private static User currentUser;
    private static UserManager INSTANCE;

    private final UserRepository userRepository = UserRepositoryDBImpl.getInstance();
    private final AccountRepository accountRepository = AccountRepositoryDBImpl.getInstance();

    private UserManager() {
    }

    public static UserManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserManager();
        }
        return INSTANCE;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean register(String login, String password, String name) throws SQLException, IOException {
        if (userRepository.add(login, password, name)) {
            User user = userRepository.findUserByLogin(login);
            accountRepository.createAccount(user.getId());
            return true;
        }
        return false;
    }

    public User login(String login, String password) throws IOException, SQLException {
        User user = userRepository.findUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean isLoginExist(String login) throws IOException, SQLException {
        return userRepository.findUserByLogin(login) != null;
    }
}
