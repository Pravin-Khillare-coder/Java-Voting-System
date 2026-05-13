package service;

import java.io.*;
import java.util.*;

public class VotingService {

    Scanner sc = new Scanner(System.in);

    public void vote(String[] d, String type) {

        if (d[2].contains(type + "=1")) {
            System.out.println("\n ====Already voted!====");
            return;
        }

        List<String[]> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("data/" + type + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null)
                list.add(line.split(","));
        } catch (IOException e) {
            System.out.println("Error reading candidates!");
        }

        if (list.isEmpty()) {
            System.out.println("No candidates!");
            return;
        }

        for (String[] c : list)
            System.out.println(c[0] + ". " + c[1]);

        int ch = sc.nextInt();
        boolean valid = false;

        for (String[] c : list) {
            if (Integer.parseInt(c[0]) == ch) {
                c[2] = String.valueOf(Integer.parseInt(c[2]) + 1);
                valid = true;
            }
        }

        if (!valid) {
            System.out.println("Invalid choice!");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/" + type + ".txt"))) {
            for (String[] c : list) {
                bw.write(c[0] + "," + c[1] + "," + c[2]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating votes!");
        }

        // update student voting status
        d[2] = d[2].replace(type + "=0", type + "=1");

        System.out.println("\n ====Vote Casted Successfully!====");
    }
}