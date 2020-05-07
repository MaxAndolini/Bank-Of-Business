/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ercan
 */
public abstract class User {

    private ID id;
    private String phoneNumber;
    private String homeAddress;
    private String password;

    public User() {
        this.id = new ID();
        this.phoneNumber = "0";
        this.homeAddress = "----";
        this.password = "0000";
    }

    public User(String ID, String fullName, String dateOfBirth, String phoneNumber, String homeAddress, String password) {
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
    
    public String getPhoneNumber() {
        String getPhoneNumber = Database.getString("Accounts", "ID", getId().getID(), "PhoneNumber");
        if(!getPhoneNumber.equals(this.phoneNumber)) setPhoneNumber(getPhoneNumber, 0);
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber, int save) {
        this.phoneNumber = phoneNumber;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "PhoneNumber", phoneNumber);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public String getHomeAddress() {
        String getHomeAddress = Database.getString("Accounts", "ID", getId().getID(), "HomeAddress");
        if(!getHomeAddress.equals(this.homeAddress)) setHomeAddress(getHomeAddress, 0);
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress, int save) {
        this.homeAddress = homeAddress;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "HomeAddress", this.homeAddress);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public String getPassword() {
        String getPassword = Database.getString("Accounts", "ID", getId().getID(), "Password");
        if(!getPassword.equals(this.password)) setPassword(getPassword, 0);
        return password;
    }

    public void setPassword(String password, int save) {
        this.password = password;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Password", this.password);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }
    
    public abstract String leavingMessage();
}
