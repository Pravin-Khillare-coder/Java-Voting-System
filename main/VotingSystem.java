package main;

import java.util.*;
// import model.*;
import service.*;

public class VotingSystem {

    static Scanner sc = new Scanner(System.in);
    

    public static void main(String[] args) {

        while (true) {
            // System.out.println(util.PasswordUtil.encrypt("abc123"));
            System.out.println("\n1.Admin\n2.Student\n3.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Username: ");
                    sc.nextLine();
                    String u = sc.nextLine();

                    System.out.print("Password: ");
                    String p = sc.nextLine();

                    if (u.equals("admin") && p.equals("admin123")) {

                        System.out.println("\n ====Login Successful!====");

                        AdminService as = new AdminService();
                        as.menu();  

                    } else {
                        System.out.println("Invalid!");
                    }
                    break;

                case 2:
                    StudentService ss = new StudentService();
                    ss.studentLogin();
                    break;

                case 3:
                    return;
            }
        }
    }
}