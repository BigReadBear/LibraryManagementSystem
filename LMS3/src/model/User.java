package model;

import java.time.LocalDate;

public class User {


    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance=0.0;

    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
    }

    public User(Integer ID, String name, String email, String address, LocalDate dateOfBirth, boolean isStudent, double balance) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
        this.balance = balance;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public boolean getStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        this.isStudent = student;
    }

    @Override
    public String toString() {
        return "ID: " + ID + " Name: " + name;
    }
}