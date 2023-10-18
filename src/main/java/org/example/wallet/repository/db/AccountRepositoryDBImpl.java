package org.example.wallet.repository.db;

import org.example.wallet.domain.Account;
import org.example.wallet.repository.AccountRepository;
import org.example.wallet.service.db.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepositoryDBImpl implements AccountRepository {
    private static final String FIND_USER_ID = "SELECT balance FROM wallet.accounts WHERE userid=?";
    private static final String CREATE_ACCOUNT = "INSERT INTO wallet.accounts (userid, balance) VALUES (?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE wallet.accounts SET balance=? WHERE userid=?";

    private static AccountRepositoryDBImpl INSTANCE;

    private DbManager dbManager;

    private AccountRepositoryDBImpl() {
    }

    private AccountRepositoryDBImpl(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public static AccountRepositoryDBImpl getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Repository not initialized");
        }
        return INSTANCE;
    }

    public static void init(DbManager dbManager) {
        if (INSTANCE == null) {
            INSTANCE = new AccountRepositoryDBImpl(dbManager);
        }
    }

    @Override
    public boolean createAccount(long userId) throws SQLException {
        Account account = findUserAccount(userId);
        if (account == null) {
            try (PreparedStatement preparedStatement = dbManager.prepareStatement(CREATE_ACCOUNT)) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, 0);
                preparedStatement.executeUpdate();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean credit(long userId, long value) throws SQLException {
        Account account = findUserAccount(userId);
        if (account != null) {
            try (PreparedStatement preparedStatement = dbManager.prepareStatement(UPDATE_ACCOUNT)) {
                preparedStatement.setLong(1, account.getBalance() + value);
                preparedStatement.setLong(2, userId);
                preparedStatement.executeUpdate();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean debit(long userId, long value) throws SQLException {
        Account account = findUserAccount(userId);
        if (account != null) {
            try (PreparedStatement preparedStatement = dbManager.prepareStatement(UPDATE_ACCOUNT)) {
                preparedStatement.setLong(1, account.getBalance() - value);
                preparedStatement.setLong(2, userId);
                preparedStatement.executeUpdate();
            }
            return true;
        }
        return false;
    }

    @Override
    public Account findUserAccount(long userId) throws SQLException {
        Account account = null;
        try (PreparedStatement preparedStatement = dbManager.prepareStatement(FIND_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long balance = resultSet.getLong("balance");
                account = Account.Factory.create(userId); // ToDO check
                account.setBalance(balance);
            }
        }
        return account;
    }
}
