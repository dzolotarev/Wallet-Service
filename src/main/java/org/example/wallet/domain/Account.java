package org.example.wallet.domain;

import org.example.wallet.domain.impl.AccountImpl;

public interface Account {

    long getUserId();

    long getBalance();

    void setBalance(long value);

    /**
     * Factory for creating Account objects
     */
    interface Factory {

        static Account create(long userId) {
            return new AccountImpl(userId);
        }
    }
}
