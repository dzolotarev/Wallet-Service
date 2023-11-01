package org.example.wallet.repository.db;

import org.example.wallet.domain.Account;
import org.example.wallet.domain.Transaction;
import org.example.wallet.enums.TransactionType;
import org.example.wallet.repository.AccountRepository;
import org.example.wallet.repository.TransactionRepository;
import org.example.wallet.service.db.DbManager;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionRepositoryDBImpl implements TransactionRepository {
    private static final String GET_ALL_TRANSACTIONS = "SELECT userid, id, type, value, date FROM wallet.transactions";
    private static final String INSERT_TRANSACTION = "INSERT INTO wallet.transactions (userid, type, value) VALUES (?, ?, ?)";
    private static TransactionRepositoryDBImpl INSTANCE;
    private final AccountRepository accountRepository = AccountRepositoryDBImpl.getInstance();
    private DbManager dbManager;

    private TransactionRepositoryDBImpl() {
    }

    private TransactionRepositoryDBImpl(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public static TransactionRepository getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Repository not initialized");
        }
        return INSTANCE;
    }

    public static void init(DbManager dbManager) {
        if (INSTANCE == null) {
            INSTANCE = new TransactionRepositoryDBImpl(dbManager);
        }
    }

    @Override
    public boolean add(UUID uuid, TransactionType type, long value, long userId) throws SQLException {
        return false;
    }

    @Override
    public boolean add(TransactionType type, long value, long userId) throws IOException, SQLException {

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
                try (PreparedStatement preparedStatement = dbManager.prepareStatement(INSERT_TRANSACTION)) {
                    preparedStatement.setLong(1, userId);
                    preparedStatement.setString(2, TransactionType.DEBIT.name());
                    preparedStatement.setLong(3, value);
                    preparedStatement.executeUpdate();
                }
                break;
            }
            case CREDIT: {
                accountRepository.credit(userId, value);
                try (PreparedStatement preparedStatement = dbManager.prepareStatement(INSERT_TRANSACTION)) {
                    preparedStatement.setLong(1, userId);
                    preparedStatement.setString(2, TransactionType.CREDIT.name());
                    preparedStatement.setLong(3, value);
                    preparedStatement.executeUpdate();
                }
                break;
            }
        }
        return true;
    }

    @Override
    public Map<UUID, Transaction> findAll() throws SQLException {
        Map<UUID, Transaction> transactions = new HashMap<>();
        try (PreparedStatement preparedStatement = dbManager.prepareStatement(GET_ALL_TRANSACTIONS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long userId = resultSet.getLong("userid");
                UUID id = (UUID) resultSet.getObject("id");
                String type = resultSet.getString("type");
                long value = resultSet.getLong("value");
                Date date = new Date(resultSet.getTimestamp("date").getTime());
                Transaction transaction = Transaction.Factory.create(); // ToDO check for null
                transaction.setUserId(userId);
                transaction.setId(id);
                transaction.setType(TransactionType.valueOf(type));
                transaction.setValue(value);
                transaction.setDate(date);
                transactions.put(id, transaction);
            }
        }
        return transactions;
    }
}
