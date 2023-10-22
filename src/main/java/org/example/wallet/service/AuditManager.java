package org.example.wallet.service;

import org.example.wallet.domain.Audit;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.infrastructure.in.Operation;
import org.example.wallet.repository.AuditRepository;
import org.example.wallet.repository.db.AuditRepositoryDBImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AuditManager {
    public final AuditRepository auditRepository = AuditRepositoryDBImpl.getInstance();
    private static AuditManager INSTANCE;

    private AuditManager() {
    }

    public static AuditManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuditManager();
        }
        return INSTANCE;
    }

    public void audit(Operation operation, long userId, AuditOption auditOption) throws SQLException, IOException {
        Audit audit = Audit.Factory.create();
        audit.setOperation(operation);
        audit.setUserId(userId);
        audit.setAuditOption(auditOption);
        audit.setCreatedAt(new Date());
        auditRepository.add(audit);
    }

    public List<Audit> getAll() throws SQLException, IOException {
        return auditRepository.findAll();
    }
}
