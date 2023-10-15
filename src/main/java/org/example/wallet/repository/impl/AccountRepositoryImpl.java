package org.example.wallet.repository.impl;

import org.example.wallet.domain.Account;
import org.example.wallet.repository.AccountRepository;

import java.util.LinkedList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private static AccountRepository INSTANCE = null;

    private final List<Account> accounts = new LinkedList<>();

    private AccountRepositoryImpl() {
    }

    public static AccountRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AccountRepositoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public boolean createAccount(long userId) {
        Account account = findUserAccount(userId);
        if (account == null) {
            accounts.add(Account.Factory.create(userId));
            return true;
        }
        return false;
    }

    @Override
    public boolean credit(long userId, long value) {
        Account account = findUserAccount(userId);
        if (account != null) {
            account.setBalance(account.getBalance() + value);
            return true;
        }
        return false;
    }

    @Override
    public boolean debit(long userId, long value) {
        Account account = findUserAccount(userId);
        if (account != null) {
            account.setBalance(account.getBalance() - value);
            return true;
        }
        return false;
    }

    @Override
    public Account findUserAccount(long userId) {
        for (Account account : accounts) {
            if (account.getUserId() == (userId)) {
                return account;
            }
        }
        return null;
    }
}
