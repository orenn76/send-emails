package com.ninyo.send.emails.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAOConnection {

    Connection getConnection() throws SQLException;

}
