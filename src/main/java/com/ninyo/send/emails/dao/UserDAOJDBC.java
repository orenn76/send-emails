package com.ninyo.send.emails.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.ninyo.send.emails.dao.DAOUtil.prepareStatement;
import static com.ninyo.send.emails.dao.DAOUtil.toSqlDate;

public class UserDAOJDBC implements UserDAO {

    private static final String SQL_FIND_BY_ID = "SELECT id, email, firstname, lastname, birthdate FROM User WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT id, email, firstname, lastname, birthdate FROM User WHERE email = ? AND password = MD5(?)";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, email, firstname, lastname, birthdate FROM User ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO User (email, password, firstname, lastname, birthdate) VALUES (?, MD5(?), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE User SET email = ?, firstname = ?, lastname = ?, birthdate = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM User WHERE id = ?";
    private static final String SQL_EXIST_EMAIL = "SELECT id FROM User WHERE email = ?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE User SET password = MD5(?) WHERE id = ?";

    private Connection connection;

    public UserDAOJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    @Override
    public User find(String email, String password) throws DAOException {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, email, password);
    }

    private User find(String sql, Object... values) throws DAOException {
        User user = null;

        try (
                PreparedStatement statement = prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public List<User> list() throws DAOException {
        List<User> users = new ArrayList<>();

        try (
                PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return users;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
                user.getEmail(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                toSqlDate(user.getBirthdate())
        };

        try (
                PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                toSqlDate(user.getBirthdate()),
                user.getId()
        };

        try (
                PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        Object[] values = {
                user.getId()
        };

        try (
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                user.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean existEmail(String email) throws DAOException {
        Object[] values = {
                email
        };

        boolean exist = false;

        try (
                PreparedStatement statement = prepareStatement(connection, SQL_EXIST_EMAIL, false, values);
                ResultSet resultSet = statement.executeQuery();
        ) {
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return exist;
    }

    @Override
    public void changePassword(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getPassword(),
                user.getId()
        };

        try (
                PreparedStatement statement = prepareStatement(connection, SQL_CHANGE_PASSWORD, false, values);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Changing password failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstname(resultSet.getString("firstname"));
        user.setLastname(resultSet.getString("lastname"));
        user.setBirthdate(resultSet.getDate("birthdate"));
        return user;
    }

}