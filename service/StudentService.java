package service;

import java.io.*;
import java.time.LocalTime;
import java.util.*;
import util.VotingControl;
// import util.OTPUtil;

public class StudentService {

    static final String STUDENT_FILE = "data/students.txt";

    static LocalTime startTime = LocalTime.of(10, 0);
    static LocalTime endTime = LocalTime.of(17, 0);

    

    Scanner sc = new Scanner(System.in);
    VotingService votingService = new VotingService();

    // ================= LOGIN =================
    public void studentLogin() {

    System.out.print("\nEnter MIS: ");
    String mis = sc.nextLine();

    System.out.print("Enter Password: ");
    String pass = sc.nextLine();

    // 🔐 Check voting status
    if (!VotingControl.votingStarted) {
        System.out.println("\n ====Voting not started!====");
        return;
    }

    // ⏰ Time check
    LocalTime now = LocalTime.now();
    if (now.isBefore(startTime) || now.isAfter(endTime)) {
        System.out.println("\n⏰ Voting time over!");
        return;
    }

    File input = new File(STUDENT_FILE);
    File temp = new File("data/temp.txt");

    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader(input));
         BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",", 3);

            // 🔐 PASSWORD ENCRYPTION CHECK
            if (d[0].equals(mis) &&
                d[1].equals(util.PasswordUtil.encrypt(pass))) {

                found = true;

                // 🔐 OTP STEP
                String otp = util.OTPUtil.generateOTP();
                System.out.println("\n📲 OTP sent: " + otp);

                System.out.print("Enter OTP: ");
                String enteredOTP = sc.nextLine();

                if (!otp.equals(enteredOTP)) {
                    System.out.println("❌ Invalid OTP!");
                    bw.write(line);
                    bw.newLine();
                    continue;
                }

                System.out.println("\n ====Login Successful!====");

                menu(d);

                // write updated student data
                bw.write(d[0] + "," + d[1] + "," + d[2]);

            } else {
                bw.write(line);
            }

            bw.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error processing login!");
    }

    // replace file safely
    if (input.delete()) temp.renameTo(input);

    if (!found)
        System.out.println("\n ====Invalid Credentials!====");
}

    // ================= MENU =================
    public void menu(String[] d) {

        while (true) {
            System.out.println("\n1. Vote CR\n2. Vote Sports\n3. Vote Cultural\n4. Change Password\n5. Logout");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: votingService.vote(d, "CR"); break;
                case 2: votingService.vote(d, "Sports"); break;
                case 3: votingService.vote(d, "Cultural"); break;
                case 4: changePassword(d); break;
                case 5: return;
            }
        }
    }

    // ================= CHANGE PASSWORD =================
    void changePassword(String[] d) {

        sc.nextLine();
        System.out.print("Enter Current Password: ");
        String cur = sc.nextLine();

        if (!d[1].equals(cur)) {
            System.out.println("Wrong Password!");
            return;
        }

        System.out.print("Enter New Password: ");
        d[1] = sc.nextLine();

        System.out.println("Password Updated!");
    }
}