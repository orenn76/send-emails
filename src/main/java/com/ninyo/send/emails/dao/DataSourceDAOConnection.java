package com.ninyo.send.emails.dao;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException;

class DataSourceDAOConnection implements DAOConnection {

    private DataSource dataSource;

    DataSourceDAOConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
