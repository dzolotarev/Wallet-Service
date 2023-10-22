package org.example.wallet.repository;

import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.TransactionType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public interface TransactionRepository {
    /**
     * The method creates and adds a transaction to the TransactionRepository
     *
     * @param uuid   unique operation identifier (UUID), not null
     * @param type   transaction type from TransactionType ENUM
     * @param value  the amount of funds in the transaction, not null
     * @param userId user ID from User, not null
     * @return true/false: depending on the result of the operation
     */
    boolean add(UUID uuid, TransactionType type, long value, long userId) throws SQLException, IOException;

    boolean add(TransactionType type, long value, long userId) throws SQLException, IOException;

    /**
     * The method for getting a map of all transactions
     *
     * @return Map of UUIDs' and Transaction's objects or empty map
     */
    Map<UUID, Transaction> findAll() throws SQLException, IOException;
}
