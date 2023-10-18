package org.example.wallet.repository.db;

import org.example.wallet.domain.Audit;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.infrastructure.in.Operation;
import org.example.wallet.infrastructure.in.OperationBasic;
import org.example.wallet.infrastructure.in.OperationUser;
import org.example.wallet.repository.AuditRepository;
import org.example.wallet.service.db.DbManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class AuditRepositoryDBImpl implements AuditRepository {
    private static final String INSERT_AUDIT = "INSERT INTO wallet.audit (userid, operation, auditoption) VALUES (?, ?, ?)";
    private static final String GET_ALL_AUDITS = "SELECT userid, operation, auditoption, createdat FROM wallet.audit";

    private static AuditRepositoryDBImpl INSTANCE;

    private DbManager dbManager;

    private AuditRepositoryDBImpl() {
    }

    private AuditRepositoryDBImpl(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public static AuditRepositoryDBImpl getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Repository not initialized");
        }
        return INSTANCE;
    }

    public static void init(DbManager dbManager) {
        if (INSTANCE == null) {
            INSTANCE = new AuditRepositoryDBImpl(dbManager);
        }
    }

    private static Operation getOperationFromValue(String name) {
        try {
            return OperationBasic.valueOf(name);
        } catch (IllegalArgumentException e) {
            try {
                return OperationUser.valueOf(name);
            } catch (IllegalArgumentException e1) {
                throw e1;
            }
        }
    }

    public boolean add(Audit audit) throws SQLException {
        try (PreparedStatement preparedStatement = dbManager.prepareStatement(INSERT_AUDIT)) {
            preparedStatement.setLong(1, audit.getUserId());
            preparedStatement.setString(2, audit.getOperation().toString());
            preparedStatement.setString(3, audit.getAuditOption().name());
            preparedStatement.executeUpdate();
        }
        return true;
    }

    public List<Audit> findAll() throws SQLException {
        List<Audit> auditList = new LinkedList<>();
        try (PreparedStatement preparedStatement = dbManager.prepareStatement(GET_ALL_AUDITS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long userId = resultSet.getLong("userid");
                String operation = resultSet.getString("operation");
                String auditoption = resultSet.getString("auditoption");
                Date date = new Date(resultSet.getTimestamp("createdat").getTime());// ToDO check for except
                Audit audit = Audit.Factory.create();
                audit.setUserId(userId);
                audit.setOperation(getOperationFromValue(operation));
                audit.setAuditOption(AuditOption.valueOf(auditoption));
                audit.setCreatedAt(date);

                auditList.add(audit);
            }
        }
        return auditList;
    }
}
