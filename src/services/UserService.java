package services;

import dataBase.ConnectionUtil;
import models.User;
import dao.UserDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
    Connection conn = connectionUtil.getConnection();

    public User getUserByCin(String cin) throws SQLException {
        User user =  new UserDao(conn).getUserByCin(cin);
        return  user;
    }

    public boolean addUser(String cin , String name , int age) throws SQLException {
        User user = new User(cin, name, age);
        return new UserDao(conn).insertUser(user);
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new UserDao(conn).getAllUsers();
        return users ;
    }

    public boolean deleteUser(String cin) throws SQLException {
        return new UserDao(conn).deleteUserByCin(cin);
    }

}
