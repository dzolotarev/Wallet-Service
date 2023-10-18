package org.example.wallet.domain;

import org.example.wallet.domain.impl.UserImpl;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface describing the User
 */
public interface User extends Serializable {
    long getId();

    String getLogin();

    String getPassword();

    void setPassword(String password);

    String getName();

    void setName(String name);

    Date getCreatedAt();

    void setCreatedAt(Date date);

    /**
     * Factory for creating User objects
     */
    interface Factory {
        static User create(long id, String login, String password) {
            return new UserImpl(id, login, password);
        }
    }
}
