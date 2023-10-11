package org.example.wallet.domain;

import org.example.wallet.domain.impl.AuditImpl;
import org.example.wallet.domain.impl.AuditOption;
import org.example.wallet.infrastructure.in.Operation;

import java.util.Date;

public interface Audit {
    Operation getOperation();

    void setOperation(Operation operation);

    long getUserId();

    void setUserId(long userId);

    AuditOption getAuditOption();

    void setAuditOption(AuditOption auditOption);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    /**
     * Factory for creating Audit objects
     */
    interface Factory {
        static Audit create() {
            return new AuditImpl();
        }
    }
}
