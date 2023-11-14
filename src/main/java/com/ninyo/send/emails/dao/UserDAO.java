package com.ninyo.send.emails.dao;

import java.util.List;

public interface UserDAO {

    public User find(Long id) throws DAOException;

    public User find(String email, String password) throws DAOException;

    public List<User> list() throws DAOException;

    public void create(User user) throws IllegalArgumentException, DAOException;

    public void update(User user) throws IllegalArgumentException, DAOException;

    public void delete(User user) throws DAOException;

    public boolean existEmail(String email) throws DAOException;

    public void changePassword(User user) throws DAOException;

}
