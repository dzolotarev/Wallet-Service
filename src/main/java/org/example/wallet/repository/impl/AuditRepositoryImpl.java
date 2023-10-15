package org.example.wallet.repository.impl;

import org.example.wallet.domain.Audit;
import org.example.wallet.repository.AuditRepository;

import java.util.LinkedList;
import java.util.List;

public class AuditRepositoryImpl implements AuditRepository {
    private static AuditRepository INSTANCE = null;

    private final List<Audit> auditList = new LinkedList<>();

    private AuditRepositoryImpl() {
    }

    public static AuditRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuditRepositoryImpl();
        }
        return INSTANCE;
    }

    public boolean add(Audit audit) {
        return auditList.add(audit);
    }

    public List<Audit> findAll() {
        return auditList;
    }

}
