package com.ninyo.send.emails.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;

class DataSourceWithLoginDAOConnection implements DAOConnection {

    private DataSource dataSource;
    private String username;
    private String password;

    DataSourceWithLoginDAOConnection(DataSource dataSource, String username, String password) {
        this.dataSource = dataSource;
        this.username = username;
        this.password = password;
    }
    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection(username, password);
    }
}
