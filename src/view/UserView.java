package view;

import dao.UserDao;
import models.User;
import services.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserView {
    Scanner scanner = new Scanner(System.in);

    public void addUser() {
        System.out.print("CIN: ");
        String cin = scanner.nextLine();
        System.out.print("Name: ");
        String nom = scanner.nextLine();
        System.out.print("Age: ");
        int age = scanner.nextInt();

        try {
            new UserService().addUser(cin, nom, age);
            System.out.println("User Added With Successfully !");
        } catch (SQLException e) {
            System.err.println("Error . Try again ...............");
        }

        endLine();

    }

    public void getUserByCin(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ User Info ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String cin = getCin();
        try {
            User user = new UserService().getUserByCin(cin);
            if(user != null){
                System.out.println(user.toString());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                endLine();
            }
            else {
                System.err.println("User Not Found !");
            }

        } catch (SQLException e) {
            System.err.println("Error . Try again ...............");
        }



    }

    public void getAllUsers(){
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Users Info ~~~~~~~~~~~~~~~~~~~~~~~~~~");
        try {
            new UserService().getAllUsers().forEach(System.out::println);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

            endLine();
        }
        catch (SQLException e) {
            System.err.println("Error . Try again ...............");
        }
    }

    public void deleteUserByCin() {

        String cin = getCin();
        try {
            if(new UserService().deleteUser(cin)){
                System.out.println("User Deleted With Successfully !");
                endLine();
            }
            else {
                System.err.println("User Not Found !");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void  endLine(){
        System.err.println("Enter for continue ! ");
        scanner.nextLine();
    }

    public String getCin(){
        System.out.print("User CIN : ");
        return scanner.nextLine();
    }

    public void menuUser(){
        System.out.println("1. Add user  ");
        System.out.println("2. Search user  ");
        System.out.println("3. All users  ");
        System.out.println("4. delete user  ");
        System.out.println("90. Back To Menu ");

    }

    public void menuManagemet(){
        boolean x = true ;
        while (x){
        menuUser();
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                addUser();
                break;
            case 2:
                getUserByCin();
                break;
            case 3:
                getAllUsers();
                break;
            case 4 :
                deleteUserByCin();
                break;
            case 90 :
                x = false;
                break;
        }
    }
    }

}


