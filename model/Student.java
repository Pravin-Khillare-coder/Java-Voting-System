package model;

public class Student extends User {
    public String voteData;

    public Student(String id, String password, String voteData) {
        super(id, password);
        this.voteData = voteData;
    }

    @Override
    public void menu() {
        System.out.println("\n========== STUDENT PANEL ==========");
    }
}