package org.example.wallet.domain.impl;

import org.example.wallet.domain.User;

import java.util.Date;

public class UserImpl implements User {

    private final long id;
    private final String login;
    private String password;
    private String name;
    private Date createdAt;


    public UserImpl(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdAt = new Date();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date date) {
        this.createdAt = date;
    }

}
