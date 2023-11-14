package com.ninyo.send.emails.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DriverManagerDAOConnection implements DAOConnection {

    private String url;
    private String username;
    private String password;

    DriverManagerDAOConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

