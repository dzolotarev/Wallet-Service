package org.example.wallet.repository.impl;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.TransactionType;
import org.example.wallet.repository.AccountRepository;
import org.example.wallet.repository.TransactionRepository;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class TransactionRepositoryImpl implements TransactionRepository {

    private static final Map<UUID, Transaction> transactions = new TreeMap<>();
    private final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();

    @Override
    public boolean add(UUID uuid, TransactionType type, long value, long userId) {

        if (transactions.containsKey(uuid)) {
            return false;
        }

        Account account = accountRepository.findUserAccount(userId);
        if (account == null) {
            return false;
        }

        switch (type) {
            case DEBIT: {
                if (account.getBalance() < value) {
                    return false;
                }
                accountRepository.debit(userId, value);
                break;
            }
            case CREDIT: {
                accountRepository.credit(userId, value);
                break;
            }
        }

        Transaction transaction = Transaction.Factory.create();
        transaction.setId(uuid);
        transaction.setType(type);
        transaction.setValue(value);
        transaction.setUserId(userId);
        transaction.setDate(new Date());

        transactions.put(uuid, transaction);
        return true;
    }

    @Override
    public Map<UUID, Transaction> findAll() {
        return transactions;
    }
}
