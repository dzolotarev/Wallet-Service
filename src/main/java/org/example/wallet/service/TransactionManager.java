package org.example.wallet.service;


import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.TransactionType;
import org.example.wallet.repository.TransactionRepository;
import org.example.wallet.repository.impl.TransactionRepositoryImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class TransactionManager {

    private final TransactionRepository transactionRepository;

    public TransactionManager() {
        this.transactionRepository = new TransactionRepositoryImpl();
    }

    public boolean debit(UUID uuid, long value, long userId) {
        return transactionRepository.add(uuid, TransactionType.DEBIT, value, userId);
    }

    public boolean credit(UUID uuid, long value, long userId) {
        return transactionRepository.add(uuid, TransactionType.CREDIT, value, userId);
    }

    public List<Transaction> transactions(long userId) { //ToDO надо бы посортировать
        List<Transaction> userTransactions = new LinkedList<>();
        for (Transaction transaction : transactionRepository.findAll().values()) {
            if (transaction.getUserId() == userId) {
                userTransactions.add(transaction);
            }
        }
        return userTransactions;
    }
}