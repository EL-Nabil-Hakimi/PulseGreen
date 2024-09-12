package dao;

import models.Logement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import enums.ConsumptionType;



public class LogementDao {
    private final Connection connection;

    public LogementDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertEnergieType(Logement energieType, String cin, ConsumptionType type) throws SQLException {
        String checkQuery = "SELECT 1 FROM \"user\" WHERE cin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(checkQuery);
        preparedStatement.setString(1, cin);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            String query = "INSERT INTO \"logement\" " +
                    "(user_cin ,start_date, end_date, consumption, consumption_type, name, impact, consommationenergie) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, cin);
                ps.setDate(2, java.sql.Date.valueOf(energieType.getStartDate()));
                ps.setDate(3,java.sql.Date.valueOf(energieType.getEndDate()));
                ps.setFloat(4, energieType.getConsumption());
                ps.setObject(5, type.name(), Types.OTHER);;
                ps.setString(6, energieType.getName());
                ps.setFloat(7, energieType.getImpact());
                ps.setFloat(8, energieType.getconsumptionEnergie());
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return false;
        }
    }

    public boolean deleteEnergieType(int id) throws SQLException {
        String deleteQuery = "DELETE FROM \"typeenergie\" WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, id);
        int rowsAffected = preparedStatement.executeUpdate();
        if(rowsAffected == 0)
        {
            return false;
        }
        return true;
    }


    public List<Logement> getAllLogement() throws SQLException {
        List<Logement> logementList = new ArrayList<>();

        String query = "SELECT * FROM \"logement\"";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Logement logement = new Logement(
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getFloat("consumption"),
                        ConsumptionType.valueOf(rs.getString("consumption_type")),
                        rs.getString("name"),
                        rs.getFloat("impact"),
                        rs.getFloat("consommationenergie")
                );

                logementList.add(logement);
            }
        }

        return logementList;
    }



    public List<Logement> getLogementByCin(String cin) throws SQLException {
        List<Logement> logementList = new ArrayList<>();
        String query = "SELECT * FROM consumption LEFT JOIN public.logement l ON consumption.id = l.id WHERE l.user_cin = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Logement logement = new Logement(
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getFloat("consumption"),
                            ConsumptionType.valueOf(rs.getString("consumption_type")),
                            rs.getString("name"),
                            rs.getFloat("impact"),
                            rs.getFloat("consommationenergie")
                    );
                    logementList.add(logement);
                }
            }
        }

        return logementList;
    }

}
