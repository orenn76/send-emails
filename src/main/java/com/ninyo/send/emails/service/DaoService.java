package com.ninyo.send.emails.service;

import com.ninyo.send.emails.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class DaoService {

    @Autowired
    private DAOFactory daoFactory;

    public void testDAO() throws SQLException, ParseException {
        DAOConnection mysqlDOConnection = daoFactory.createConnection("mysql");

        UserDAO userDAO = new UserDAOJDBC(mysqlDOConnection.getConnection());
        System.out.println("UserDAO successfully obtained: " + userDAO);

        // Create user.
        com.ninyo.send.emails.dao.User user = new com.ninyo.send.emails.dao.User();
        user.setEmail("foo@bar.com");
        user.setPassword("password");
        userDAO.create(user);
        System.out.println("User successfully created: " + user);

        // Create another user.
        com.ninyo.send.emails.dao.User anotherUser = new com.ninyo.send.emails.dao.User();
        anotherUser.setEmail("bar@foo.com");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setFirstname("Bar");
        anotherUser.setLastname("Foo");
        anotherUser.setBirthdate(new SimpleDateFormat("yyyy-MM-dd").parse("1978-03-26"));
        userDAO.create(anotherUser);
        System.out.println("Another user successfully created: " + anotherUser);

        // Update user.
        user.setFirstname("Foo");
        user.setLastname("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // Update user.
        user.setFirstname("Foo");
        user.setLastname("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // List all users.
        List<com.ninyo.send.emails.dao.User> users = userDAO.list();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());

        // Delete user.
        userDAO.delete(user);
        System.out.println("User successfully deleted: " + user);

        // Check if email exists.
        boolean exist = userDAO.existEmail("foo@bar.com");
        System.out.println("This email should not exist anymore, so this should print false: " + exist);

        // Change password.
        anotherUser.setPassword("newAnotherPassword");
        userDAO.changePassword(anotherUser);
        System.out.println("Another user's password successfully changed: " + anotherUser);

        // Get another user by email and password.
        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);

        // Delete another user.
        userDAO.delete(foundAnotherUser);
        System.out.println("Another user successfully deleted: " + foundAnotherUser);

        // List all users again.
        users = userDAO.list();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());
    }

}