package org.example.wallet.domain.impl;

import org.example.wallet.domain.Audit;
import org.example.wallet.enums.AuditOption;
import org.example.wallet.enums.Operation;

import java.util.Date;

public class AuditImpl implements Audit {
    private Operation operation;
    private long userId;
    private AuditOption auditOption;
    private Date createdAt;

    public AuditOption getAuditOption() {
        return auditOption;
    }

    public void setAuditOption(AuditOption auditOption) {
        this.auditOption = auditOption;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
