package org.example.wallet.service;

import org.example.wallet.domain.User;
import org.example.wallet.repository.UserRepository;
import org.example.wallet.repository.impl.AccountRepositoryImpl;
import org.example.wallet.repository.impl.UserRepositoryImpl;

import java.util.List;

public class UserManager {
    private static User currentUser = null;
    private final UserRepository userRepository = UserRepositoryImpl.getInstance();

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public boolean register(String login, String password, String name) {
        if (userRepository.add(login, password, name)) {
            User user = userRepository.findUserByLogin(login);
            AccountRepositoryImpl.getInstance().createAccount(user.getId());
            return true;
        }
        return false;
    }

    public User login(String login, String password) {
        User user = userRepository.findUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean isLoginExist(String login) {
        return userRepository.findUserByLogin(login) != null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
