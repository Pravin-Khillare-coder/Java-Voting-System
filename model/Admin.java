package model;

public class Admin extends User {

    public Admin(String id, String password) {
        super(id, password);
    }

    @Override
    public void menu() {
        System.out.println("\n========== ADMIN PANEL ==========");
    }
}