package org.example.wallet.service;


import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.TransactionType;
import org.example.wallet.repository.TransactionRepository;
import org.example.wallet.repository.db.TransactionRepositoryDBImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {
    private final TransactionRepository transactionRepository = TransactionRepositoryDBImpl.getInstance();
    private static TransactionManager INSTANCE;

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TransactionManager();
        }
        return INSTANCE;
    }

    public boolean debit(long value, long userId) throws SQLException, IOException {
        return transactionRepository.add(TransactionType.DEBIT, value, userId);
    }

    public boolean credit(long value, long userId) throws SQLException, IOException {
        return transactionRepository.add(TransactionType.CREDIT, value, userId);
    }

    public List<Transaction> transactions(long userId) throws SQLException, IOException {
        return transactionRepository.findAll().values().stream().filter(transaction -> transaction.getUserId() == userId).sorted(Comparator.comparing(Transaction::getDate)).collect(Collectors.toList());
    }
}