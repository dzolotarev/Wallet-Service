package org.example.wallet.domain;

import org.example.wallet.domain.impl.TransactionImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Interface describing the Transaction
 */
public interface Transaction extends Serializable {
    long getUserId();

    void setUserId(long userId);

    UUID getId();

    void setId(UUID id);

    TransactionType getType();

    void setType(TransactionType type);

    long getValue();

    void setValue(long value);

    Date getDate();

    void setDate(Date date);

    /**
     * Factory for creating Transaction objects
     */
    interface Factory {
        static Transaction create() {
            return new TransactionImpl();
        }
    }
}
