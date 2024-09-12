package dao;

import models.Transport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.ConsumptionType;



public class TransportDao {
    private final Connection connection;

    public TransportDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertVhicleType(Transport vhicleType, String cin , ConsumptionType type) throws SQLException {
        String checkQuery = "SELECT 1 FROM \"user\" WHERE cin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
        preparedStatement.setString(1, cin);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String query = "INSERT INTO \"transport\" " +
                    "(user_cin ,start_date, end_date, consumption, consumption_type, name, impact, parcoure) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, cin);
                ps.setDate(2, java.sql.Date.valueOf(vhicleType.getStartDate()));
                ps.setDate(3,java.sql.Date.valueOf(vhicleType.getEndDate()));
                ps.setFloat(4, vhicleType.getConsumption());
                ps.setObject(5, type.name(), Types.OTHER);;
                ps.setString(6, vhicleType.getName());
                ps.setFloat(7, vhicleType.getImpact());
                ps.setFloat(8, vhicleType.getParcourue());
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }


    public boolean deleteVhicleType(int id) throws SQLException {
        String deleteQuery = "DELETE FROM \"typevhicle\" WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected == 0)
        {
            return false;
        }
        return true;
    }


    public List<Transport> getAllTransports() throws SQLException {
        List<Transport> transportsList = new ArrayList<>();

        String query = "SELECT * FROM \"alimentation\"";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Transport transport = new Transport(
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getFloat("consumption"),
                        ConsumptionType.valueOf(rs.getString("consumption_type")),
                        rs.getString("name"),
                        rs.getFloat("impact"),
                        rs.getFloat("parcoure")
                );

                transportsList.add(transport);
            }
        }

        return transportsList;
    }


    public List<Transport> geTransportByCin(String cin) throws SQLException {
        List<Transport> trasportList = new ArrayList<>();
        String query = "SELECT * FROM consumption LEFT JOIN public.\"transport \" l ON consumption.id = l.id WHERE l.user_cin = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transport transport = new Transport(
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("consumption"),
                            ConsumptionType.valueOf(rs.getString("consumption_type")),
                            rs.getString("name"),
                            rs.getFloat("impact"),
                            rs.getFloat("parcoure")
                    );
                    trasportList.add(transport);
                }
            }
        }

        return trasportList;
    }


}
