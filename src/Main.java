import dao.UserDao;
import models.Consumption;
import models.User;
import services.ConsumptionService;
import services.UserService;
import view.ConsumptionView;
import view.UserView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        while (true){
        Scanner sc = new Scanner(System.in);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Pulse Green ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("1=> User Management ");
        System.out.println("2=> Consumptions Management ");
        System.out.println("0=> Exit ");
        System.out.print("Your Choice ? : ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                new UserView().menuManagemet();
                break;
            case 2:
                new ConsumptionView().menuManagement();
                break;
            case 0 :
                System.exit(0);
                break;
            }
        }
    }
}