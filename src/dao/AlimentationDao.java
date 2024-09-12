package dao;

import models.Alimentation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.ConsumptionType;

public class AlimentationDao {
    private final Connection connection;

    public AlimentationDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertAlimentation(Alimentation alimenType, String cin, ConsumptionType type) throws SQLException {

        String checkQuery = "SELECT 1 FROM \"user\" WHERE cin = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, cin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String query = "INSERT INTO \"alimentation\" " +
                            "(user_cin, start_date, end_date, consumption, consumption_type, name, impact, poids)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement ps = connection.prepareStatement(query)) {
                        ps.setString(1, cin);
                        ps.setDate(2, java.sql.Date.valueOf(alimenType.getStartDate()));
                        ps.setDate(3, java.sql.Date.valueOf(alimenType.getEndDate()));
                        ps.setFloat(4, alimenType.getConsumption());
                        ps.setObject(5, type.name(), Types.OTHER);
                        ps.setString(6, alimenType.getName());
                        ps.setFloat(7, alimenType.getImpact());
                        ps.setFloat(8, alimenType.getPoids());
                        ps.executeUpdate();
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
    }

    public boolean deleteAlimentation(int id) throws SQLException {
        String deleteQuery = "DELETE FROM \"typealiment\" WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected == 0)
        {
            return false;
        }
        return true;
    }

    public List<Alimentation> getAllAlimentation() throws SQLException {
        List<Alimentation> alimentationList = new ArrayList<>();

        String query = "SELECT * FROM \"alimentation\"";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alimentation alimentation = new Alimentation(
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getFloat("consumption"),
                        ConsumptionType.valueOf(rs.getString("consumption_type")),
                        rs.getString("name"),
                        rs.getFloat("impact"),
                        rs.getFloat("poids")
                );

                alimentationList.add(alimentation);
            }
        }

        return alimentationList;
    }

    public List<Alimentation> getAlimentationByCin(String cin) throws SQLException {
        List<Alimentation> alimentationList = new ArrayList<>();
        String query = "SELECT * FROM consumption LEFT JOIN public.alimentation l ON consumption.id = l.id WHERE l.user_cin = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alimentation alimentation = new Alimentation(
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("consumption"),
                            ConsumptionType.valueOf(rs.getString("consumption_type")),
                            rs.getString("name"),
                            rs.getFloat("impact"),
                            rs.getFloat("poids")
                    );
                    alimentationList.add(alimentation);
                }
            }
        }

        return alimentationList;
    }


}
