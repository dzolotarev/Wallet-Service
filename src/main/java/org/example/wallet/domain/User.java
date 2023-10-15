package org.example.wallet.domain;

import org.example.wallet.domain.impl.UserImpl;

import java.util.Date;

public interface User {

    long getId();

    String getLogin();

    String getPassword();

    void setPassword(String password);

    String getName();

    void setName(String name);

    Date getCreatedAt();

    /**
     * Factory for creating User objects
     */
    interface Factory {
        static User create(long id, String login, String password) {
            return new UserImpl(id, login, password);
        }
    }
}
