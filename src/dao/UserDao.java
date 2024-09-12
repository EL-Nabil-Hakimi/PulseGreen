package dao;


import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class UserDao {

        private final Connection connection;

        public UserDao(Connection connection) {
            this.connection = connection;
        }

        public boolean  insertUser(User user) throws SQLException {
        String sql = "INSERT INTO \"user\" (cin, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getCin());
            ps.setString(2, user.getName());
            ps.setInt(3, user.getAge());
            ps.executeUpdate();
            return true;

        }
        catch (SQLException e) {
            return false;
        }
    }

    public User getUserByCin(String cin) throws SQLException {
        String query = "SELECT * FROM \"user\" WHERE cin = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, cin);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("cin"),
                            rs.getString("name"),
                            rs.getInt("age")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM \"user\"";
        PreparedStatement pstmt = connection.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User(
                    rs.getString("cin"),
                    rs.getString("name"),
                    rs.getInt("age")
            );
            users.add(user);
        }

        return users;
    }


    public boolean updateUser(User user) throws SQLException
    {
        String query = "UPDATE \"user\" SET name = ?, age = ? WHERE cin = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, user.getName());
        pstmt.setInt(2, user.getAge());
        pstmt.setString(3, user.getCin());
        if(pstmt.executeUpdate() == 1)
        {
            return true;
        }
        return false;
    }

    public boolean deleteUserByCin(String cin) throws SQLException
    {
        String query = "DELETE FROM \"user\" WHERE cin = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cin);
        if(pstmt.executeUpdate() == 1)
        {
            return true;
        }
        else return false;
    }

}
