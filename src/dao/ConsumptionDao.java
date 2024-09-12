package dao;

import enums.ConsumptionType;
import models.Consumption;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumptionDao {

    private final Connection connection;

    public ConsumptionDao(Connection connection) {
        this.connection = connection;
    }

    public List<Map<String, Object>> getAllUsersWithConsumptions() throws SQLException {
        List<Map<String, Object>> userList = new ArrayList<>(); // To hold the final result

        String sql = "SELECT " +
                "\"user\".cin AS user_cin, " +
                "\"user\".name AS user_name, " +
                "\"user\".age AS user_age, " +
                "json_agg(json_build_object( " +
                "'consumption_id', c.id, " +
                "'start_date', c.start_date, " +
                "'end_date', c.end_date, " +
                "'consumption', c.consumption, " +
                "'consumption_type', c.consumption_type, " +
                "'name', COALESCE(t.name, l.name, a.name), " +
                "'impact', COALESCE(t.impact, l.impact, a.impact), " +
                "'additional_info', COALESCE(t.parcoure, l.consommationenergie, a.poids) " +
                ")) AS consumption_details " +
                "FROM \"user\" " +
                "LEFT JOIN consumption c ON c.user_cin = \"user\".cin " +
                "LEFT JOIN logement l ON c.id = l.id " +
                "LEFT JOIN alimentation a ON c.id = a.id " +
                "LEFT JOIN \"transport \"  t ON c.id = t.id " +
                "GROUP BY \"user\".cin, \"user\".name, \"user\".age " +
                "ORDER BY \"user\".cin;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Map<String, Object> user = new HashMap<>();
            user.put("cin", rs.getString("user_cin"));
            user.put("name", rs.getString("user_name"));
            user.put("age", rs.getInt("user_age"));
            user.put("consumption_details", rs.getString("consumption_details"));

            userList.add(user); // Add the user data to the list
        }

        rs.close();
        ps.close();

        return userList; // Return the list of users with their consumption details
    }










}
