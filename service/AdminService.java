package service;

import java.io.*;
import java.util.*;

public class AdminService {

    Scanner sc = new Scanner(System.in);

    public String chooseElection() {
        sc.nextLine();
        System.out.print("\nEnter Election (CR / Sports / Cultural): ");
        return sc.nextLine();
    }
    public void menu() {

    while (true) {

        System.out.println("\n========== ADMIN MENU ==========");
        System.out.println("1. Add Candidate");
        System.out.println("2. View Candidates");
        System.out.println("3. View Results");
        System.out.println("4. Start Voting");
        System.out.println("5. Stop Voting");
        System.out.println("6. Delete Candidate");
        System.out.println("7. Logout");

        System.out.print("Enter choice: ");
        int ch = sc.nextInt();

        switch (ch) {
            case 1: addCandidate(); break;
            case 2: viewCandidates(); break;
            case 3: viewResults(); break;
            case 4: startVoting(); break;
            case 5: stopVoting(); break;
            case 6: deleteCandidate(); break;
            case 7: return;
        }
    }
}

public void viewResults() {

    String[] elections = {"CR", "Sports", "Cultural"};

    for (String type : elections) {

        System.out.println("\n--- " + type + " Results ---");

        try (BufferedReader br = new BufferedReader(new FileReader("data/" + type + ".txt"))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println(d[1] + " - " + d[2]);
            }

        } catch (IOException e) {
            System.out.println("Error reading file!");
        }
    }
}

public void deleteCandidate() {

    String type = chooseElection();

    File input = new File("data/" + type + ".txt");
    File temp = new File("data/temp.txt");

    
    System.out.print("Enter Candidate ID: ");
    String id = sc.nextLine();

    boolean found = false;

    try (BufferedReader br = new BufferedReader(new FileReader(input));
         BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {

        String line;

        while ((line = br.readLine()) != null) {

            String[] d = line.split(",");

            if (d[0].equals(id)) {
                found = true;
                continue;
            }

            bw.write(line);
            bw.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error!");
    }

    if (input.delete()) temp.renameTo(input);

    if (found)
        System.out.println("Deleted!");
    else
        System.out.println("Not Found!");
}


public void startVoting() {
    util.VotingControl.votingStarted = true;
    System.out.println("\n ====Voting Started!====");
}

public void stopVoting() {
    util.VotingControl.votingStarted = false;
    System.out.println("\n ====Voting Stopped!====");
}


    public void addCandidate() {
        String type = chooseElection();
        File file = new File("data/" + type + ".txt");

        if (!file.exists()) {
            System.out.println("File not found!");
            return;
        }

        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(id + ",")) {
                    System.out.println("ID exists!");
                    return;
                }
            }
        } catch (IOException e) {}

        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(id + "," + name + ",0");
            fw.write(System.lineSeparator());
        } catch (IOException e) {}

        System.out.println("Candidate Added!");
    }

    public void viewCandidates() {
        String type = chooseElection();
        File file = new File("data/" + type + ".txt");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println(d[0] + ". " + d[1]);
            }
        } catch (IOException e) {}
    }
}