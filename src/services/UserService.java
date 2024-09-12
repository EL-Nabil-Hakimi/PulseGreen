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
    UserDao userDao = new UserDao();

    public User getUserByCin(String cin) throws SQLException {
        User user =  userDao.getUserByCin(cin);
        return  user;
    }

    public boolean addUser(String cin , String name , int age) throws SQLException {
        User user = new User(cin, name, age);
        return userDao.insertUser(user);
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = userDao.getAllUsers();
        return users ;
    }

    public boolean deleteUser(String cin) throws SQLException {
        return userDao.deleteUserByCin(cin);
    }

}
