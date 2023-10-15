package org.example.wallet.service;

import org.example.wallet.domain.Audit;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.infrastructure.in.Operation;
import org.example.wallet.repository.AuditRepository;
import org.example.wallet.repository.impl.AuditRepositoryImpl;

import java.util.Date;
import java.util.List;

public class AuditManager {
    public final AuditRepository auditRepository = AuditRepositoryImpl.getInstance();

    public void audit(Operation operation, long userId, AuditOption auditOption) {
        Audit audit = Audit.Factory.create();
        audit.setOperation(operation);
        audit.setUserId(userId);
        audit.setAuditOption(auditOption);
        audit.setCreatedAt(new Date());
        auditRepository.add(audit);
    }

    public List<Audit> getAll() {
        return auditRepository.findAll();
    }
}
