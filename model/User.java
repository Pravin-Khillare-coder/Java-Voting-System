package model;

public abstract class User {
    protected String id;
    protected String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public abstract void menu();
}