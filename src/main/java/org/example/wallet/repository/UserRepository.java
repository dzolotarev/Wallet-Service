package org.example.wallet.repository;

import org.example.wallet.domain.User;

import java.util.List;

public interface UserRepository {
    /**
     * Method creates and adds User to UserRepository
     *
     * @param login    uniq user login, not null
     * @param password just a password for the user, not null
     * @param name     user's name, not null
     * @return true/false: depending on the result of the operation
     */
    boolean add(String login, String password, String name);

    /**
     * The method searches for User by userId
     *
     * @param login search criteria, not null
     * @return кeturns a User object or null
     */
    User findUserByLogin(String login);

    List<User> findAll();

}
