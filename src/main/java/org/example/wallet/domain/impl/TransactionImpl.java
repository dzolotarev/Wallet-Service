package org.example.wallet.domain.impl;

import org.example.wallet.domain.Transaction;
import org.example.wallet.domain.TransactionType;

import java.util.Date;
import java.util.UUID;

public class TransactionImpl implements Transaction {

    private long userId;
    private UUID id;
    private TransactionType type;
    private long value;
    private Date date;

    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public long getValue() {
        return value;
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = new Date();
    }
}
