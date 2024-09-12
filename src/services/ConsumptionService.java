package services;

import dao.*;
import dataBase.ConnectionUtil;
import enums.ConsumptionType;
import models.*;
import utils.CheckDate;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumptionService {
    ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
    Connection conn = connectionUtil.getConnection();


    public boolean addAlimentation(LocalDate startDate , LocalDate endDate , float consumption
            ,ConsumptionType type , String name , float impact ,float poids ,  String cin) throws SQLException {
        Alimentation newAlimentation = new Alimentation(startDate , endDate , consumption , type , name , impact , poids);
        new AlimentationDao(conn).insertAlimentation(newAlimentation , cin , type);
        return true;
    }

    public boolean addTransport(LocalDate startDate , LocalDate endDate , float consumption
            ,ConsumptionType type , String name , float impact ,float poids ,  String cin) throws SQLException {
        Transport newTransport = new Transport(startDate , endDate , consumption , type , name , impact , poids);
        new TransportDao(conn).insertVhicleType(newTransport , cin , type);
        return true;
    }

    public boolean addLogement(LocalDate startDate , LocalDate endDate , float consumption
            ,ConsumptionType type , String name , float impact ,float poids ,  String cin) throws SQLException {
        Logement newLogement = new Logement(startDate , endDate , consumption , type , name , impact , poids);
        new LogementDao(conn).insertEnergieType(newLogement , cin , type);
        return true;
    }

    private List<Alimentation> listOfAlimentation() throws SQLException {
        return new AlimentationDao(conn).getAllAlimentation();
    }

    private List<Logement> listOfLogement() throws SQLException {

        return new LogementDao(conn).getAllLogement();
    }

    private List<Logement> listOfLogementByCin(User user) throws SQLException {
        return new LogementDao(conn).getLogementByCin(user.getCin());
    }

    private List<Transport> listOfTransport() throws SQLException {
        return new TransportDao(conn).getAllTransports();
    }

    public void getAlimentation() throws SQLException {
        listOfAlimentation().forEach(System.out::println);
    }

    public void getLogement() throws SQLException {
        listOfLogement().forEach(System.out::println);
    }

    public void getTransport() throws SQLException {
        listOfTransport().forEach(System.out::println);
    }

    public void getAllConsumption() throws SQLException {
        System.out.println("==================== List des Logement ====================");
        getLogement();
        System.out.println("==================== List des Alimentation ====================");
        getAlimentation();
        System.out.println("==================== List des Transport ====================");
        getTransport();
    }

    public List<Logement> getAllLogementByCin(User user) throws SQLException
    {
        List<Logement> listLogement = listOfLogementByCin(user);
        listLogement.forEach(System.out::println);
        return listLogement;
    }

    public void getAllUsersConsumptions() throws SQLException
    {
        UserDao userDao = new UserDao(conn);
        List<User> listUsers = userDao.getAllUsers();
        listUsers.forEach(e->
        {
            try {
                System.out.println(e);
                System.out.println("\n===================== User Info ============================================");
                List<Consumption> consumptions = getAllUsersConsumptionsByCin(e).getConsumptionList();
                if(consumptions.size() > 0 )
                {
                    consumptions.forEach(System.out::println);
                }
                else
                {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~ No Consumptios found ~~~~~~~~~~~~~~~~~~~");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


   public List<User> getAllUsersConsumptionsImpactSorted() throws SQLException
    {
        List<User> users = new  UserDao(conn).getAllUsers();
        users.forEach(e->{
            try {
                getAllUsersConsumptionsByCin(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

         users =  users.stream()
                .sorted((o1, o2) -> {
                    double im1 = o1.getConsumptionList().stream()
                            .mapToDouble(Consumption::calculerImpact).sum();
                    double im2 = o2.getConsumptionList().stream()
                            .mapToDouble(Consumption::calculerImpact).sum();
                    return Double.compare(im1, im2);
                }).collect(Collectors.toList());


        return users;
    }



    //charging all consumptions for user
    public User getAllUsersConsumptionsByCin(User user) throws SQLException
    {
        List<Logement> logementList = new LogementDao(conn).getLogementByCin(user.getCin());
        List<Transport> transportList = new TransportDao(conn).geTransportByCin(user.getCin());
        List<Alimentation> alimentationList = new AlimentationDao(conn).getAlimentationByCin(user.getCin());
       logementList.forEach(e -> {
           user.getConsumptionList().add(e);
       });

       transportList.forEach(e -> {
           user.getConsumptionList().add(e);
       });

       alimentationList.forEach(e -> {
           user.getConsumptionList().add(e);
       });
       return user;
    }


    public static List<Consumption> consumptionByPeriod(LocalDate startDate, LocalDate endDate , List<Consumption> consumptions)
    {
        List<Consumption> consumptionList = new ArrayList<>();
        consumptions.forEach(e-> {
            if(e.getStartDate().isAfter(startDate) && e.getEndDate().isBefore(endDate)){
                consumptionList.add(e);
            }
        });
        return consumptionList;
    }

    public static double getAveragePerPeriod(User user , LocalDate startDate, LocalDate endDate) throws SQLException{
        List<Consumption> consumptionByPeriod = consumptionByPeriod(startDate,endDate , user.getConsumptionList());
        double consumptionSum =  consumptionByPeriod.stream()
                .mapToDouble(Consumption::getConsumption).sum();
        double days = CheckDate.calculationDaysByPeriod(consumptionByPeriod);

        if(consumptionSum/days > 0){
        return consumptionSum/days;
        }
        else {
            return 0;
        }
    }

    public List<User> getAllUsersInactive() throws SQLException {
        List<User> users = new UserDao(conn).getAllUsers();
        List<User> inactiveUsers = new ArrayList<>();
        users.forEach(user -> {
            try {
                if(getAllUsersConsumptionsByCin(user).getConsumptionList().isEmpty()){
                    inactiveUsers.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return inactiveUsers;
        };

}