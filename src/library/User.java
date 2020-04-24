/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

/**
 *
 * @author ercan
 */
public class User {

    private ID id;
    private String homeAddress;
    private String password;

    public User() {
        this.id = new ID();
        this.homeAddress = "----";
        this.password = "0000";
    }

    public User(String ID, String fullName, String dateOfBirth, String homeAddress, String password) {
        this.id = new ID(ID, fullName, dateOfBirth);
        this.homeAddress = homeAddress;
        this.password = password;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress, int save) {
        this.homeAddress = homeAddress;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "HomeAddress", this.homeAddress);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password, int save) {
        this.password = password;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Password", this.password);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void displayInfo() {
        System.out.println(toString() + "'s ID: " + id.getID());
        System.out.println(toString() + "'s Name: " + id.getFullName());
        System.out.println(toString() + "'s Birth Date: " + id.getDateOfBirth());
    }
}
