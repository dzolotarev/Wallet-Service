package org.example.wallet.repository.impl;

import org.example.wallet.domain.User;
import org.example.wallet.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl INSTANCE;

    private final List<User> users = new LinkedList<>();
    private Long id = 0L;

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public boolean add(String login, String password, String name) {
        if (findUserByLogin(login) == null) {
            User user = User.Factory.create(id++, login, password);
            if (name != null) {
                user.setName(name);
            }
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public User findUserByLogin(String login) {
        for (User user : users) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }
        return null; //ToDO надо бы возвращать Optional везде, где возвращаю null (
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
