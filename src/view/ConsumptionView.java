package view;

import enums.ConsumptionType;
import services.ConsumptionService;
import services.UserService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsumptionView {
    Scanner sc = new Scanner(System.in);

    public void addConsumption() throws SQLException {
        System.out.println("Enter Consumption Type");
        System.out.println("1. TRANSPORT");
        System.out.println("2. LOGEMENT");
        System.out.println("3. ALIMENTATION");
        int consumptionType = sc.nextInt();
        switch (consumptionType) {
            case 1:
                addTransport();
                break;
            case 2:
                addLogement();
                break;
            case 3:
                addAlimentation();
                break;
        }

    }



    public void addAlimentation() throws SQLException {
                System.out.println("Consumption For : ");
                System.out.println("1. Meat");
                System.out.println("2. Vegetable");
                int consumptionType = sc.nextInt();
                String name = "";
                float impact = 0.0F;

                switch (consumptionType) {
                    case 1:
                        name = "Meat        " ;
                        impact = 5.0F;
                        break;
                    case 2:
                        name = "Vegetable" ;
                        impact = 0.5F;
                        break;
                }
                System.out.print("Enter cin for user : ");
                String cin = sc.next();
                System.out.print("Start Date : ");
                LocalDate startDate = LocalDate.parse(sc.next());
                System.out.print("End Date : ");
                LocalDate endDate = LocalDate.parse(sc.next());
                System.out.print("Consumption : ");
                float consumption = Float.parseFloat(sc.next());
                System.out.print("Enter Weight Km : ");
                float weight = (float) sc.nextDouble();

                ConsumptionType type = ConsumptionType.ALIMENTATION;
                boolean insert = new ConsumptionService().addAlimentation(startDate , endDate , consumption ,type ,name ,impact , weight , cin);
                if (insert) {
                    System.out.println("Consumption added successfully");
                }
                else {
                    System.out.println("Consumption not added");
                }
    }


    public void addTransport() throws SQLException {
        System.out.println("Consumption For : ");
        System.out.println("1. Vehicle");
        System.out.println("2. Train");
        int consumptionType = sc.nextInt();
        String name = "";
        float impact = 0.0F;

        switch (consumptionType) {
            case 1:
                name = "Vehicle        " ;
                impact = 0.5F;
                break;
            case 2:
                name = "Train" ;
                impact = 0.1F;
                break;
        }
        System.out.print("Enter cin for user : ");
        String cin = sc.next();
        System.out.print("Start Date : ");
        LocalDate startDate = LocalDate.parse(sc.next());
        System.out.print("End Date : ");
        LocalDate endDate = LocalDate.parse(sc.next());
        System.out.print("Consumption : ");
        float consumption = Float.parseFloat(sc.next());
        System.out.print("Enter Distance Km : ");
        float weight = (float) sc.nextDouble();

        ConsumptionType type = ConsumptionType.TRANSPORT;
        boolean insert = new ConsumptionService().addTransport(startDate , endDate , consumption ,type ,name ,impact , weight , cin);
        if (insert) {
            System.out.println("Consumption added successfully");
        }
        else {
            System.out.println("Consumption not added");
        }
    }


    public void addLogement () throws SQLException {
        System.out.println("Consumption For : ");
        System.out.println("1. electricity");
        System.out.println("2. Gaz");
        int consumptionType = sc.nextInt();
        String name = "";
        float impact = 0.0F;

        switch (consumptionType) {
            case 1:
                name = "electricity" ;
                impact = 1.5F;
                break;
            case 2:
                name = "Gaz" ;
                impact = 2.0F;
                break;
        }
        System.out.print("Enter cin for user : ");
        String cin = sc.next();
        System.out.print("Start Date : ");
        LocalDate startDate = LocalDate.parse(sc.next());
        System.out.print("End Date : ");
        LocalDate endDate = LocalDate.parse(sc.next());
        System.out.print("Consumption : ");
        float consumption = Float.parseFloat(sc.next());
        System.out.print("Enter The energy : ");
        float weight = (float) sc.nextDouble();

        ConsumptionType type = ConsumptionType.LOGEMENT;
        boolean insert = new ConsumptionService().addLogement(startDate , endDate , consumption ,type ,name ,impact , weight , cin);
        if (insert) {
            System.out.println("Consumption added successfully");
        }
        else {
            System.out.println("Consumption not added");
        }
    }


    public void getAllUsersWithConsumptions() throws SQLException {
            new ConsumptionService().getAllUsersConsumptions();
            endLine();
    }

    public void getAllConsumptions() throws SQLException {
                new ConsumptionService().getAllConsumption();
                endLine();
        }
    public void getAllConsumptionsImpact() throws SQLException {
                new ConsumptionService().getAllUsersConsumptionsImpact().forEach(System.out::println);
                endLine();
        }

    public void getLogement() throws SQLException {
        new ConsumptionService().getLogement();
        endLine();

    }
    public void getTransport() throws SQLException {
        new ConsumptionService().getTransport();
        endLine();

    }
    public void getAlimentation() throws SQLException {
        new ConsumptionService().getAlimentation();
        endLine();
    }

    public void getAllUsersInactive(){
        try {
            new ConsumptionService().getAllUsersInactive().forEach(System.out::println);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void menuConsumption(){
        System.out.println("~~~~~~~~~~~~~~~ Consumption Menu ~~~~~~~~~~~~~~~");
        System.out.println("1. Add Consumption");
        System.out.println("2. Show All Users With Consumptions");
        System.out.println("3. Show All Consumptions");
        System.out.println("4. Show All Consumptions Impact");
        System.out.println("5. Show All Users Inactive");
        System.out.println("6: Show All Consumptions By Type : Logement ");
        System.out.println("7. Show All Consumptions By Type : Transport ");
        System.out.println("8. Show All Consumptions By Type : Alimentation ");
        System.out.println("90. Back to Menu ");

    }

    public void menuManagement() throws SQLException {
        boolean x = true;
        while (x){
        menuConsumption();
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice){
            case 1:
                addConsumption();
                break;
            case 2:
                getAllUsersWithConsumptions();
                break;
            case 3:
                getAllConsumptions();
                break;
            case 4 :
                getAllConsumptionsImpact();
                break;
            case 5 :
                getLogement();
                break;
            case 6 :
                getTransport();
                break;
            case 7:
                getAlimentation();
                break;
            case 8:
                getAllUsersInactive();
                break;
            case 90:
                x = false;
                break;
        }
        }
    }


    private void  endLine(){
        System.out.println("Enter for continue ! ");
        sc.nextLine();
    }


}
