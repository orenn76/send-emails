package com.ninyo.send.emails.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Component
public class DAOFactory {

    @Autowired
    private DAOProperties daoProperties;

    public DAOConnection createConnection(String dbName) throws DAOConfigurationException {
        if (dbName == null) {
            throw new DAOConfigurationException("Database name is null.");
        }

        DAOProperties.JDBCProperties jdbcProperties = daoProperties.getJdbcProperties(dbName);
        String url = jdbcProperties.getUrl();
        String driverClassName = jdbcProperties.getDriver();
        String username = jdbcProperties.getUsername();
        String password = jdbcProperties.getPassword();

        DAOConnection instance;

        // If driver is specified, then load it to let it register itself with DriverManager.
        if (driverClassName != null) {
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                throw new DAOConfigurationException("Driver class '" + driverClassName + "' is missing in classpath.", e);
            }
            instance = new DriverManagerDAOConnection(url, username, password);
        }

        // Else assume URL as DataSource URL and lookup it in the JNDI.
        else {
            DataSource dataSource;
            try {
                dataSource = (DataSource) new InitialContext().lookup(url);
            } catch (NamingException e) {
                throw new DAOConfigurationException("DataSource '" + url + "' is missing in JNDI.", e);
            }
            if (username != null) {
                instance = new DataSourceWithLoginDAOConnection(dataSource, username, password);
            } else {
                instance = new DataSourceDAOConnection(dataSource);
            }
        }

        return instance;
    }

}

