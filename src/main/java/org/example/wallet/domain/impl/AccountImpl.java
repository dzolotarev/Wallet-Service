package org.example.wallet.domain.impl;

import org.example.wallet.domain.Account;

public class AccountImpl implements Account {

    private long userId;
    private long balance;

    public AccountImpl(Long userId) {
        this.userId = userId;
        this.balance = 0L;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public long getBalance() {
        return balance;
    }

    @Override
    public void setBalance(long value) {
        this.balance = value;
    }
}
