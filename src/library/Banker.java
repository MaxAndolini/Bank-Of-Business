/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.math.BigDecimal;

/**
 *
 * @author ercan
 */
public class Banker extends User {

    private BigDecimal bankerSalary;

    public Banker() {
        super();
        this.bankerSalary = BigDecimal.ZERO;
    }

    public Banker(BigDecimal bankerSalary, String ID, String fullName, String dateOfBirth, String homeAddress, String password) {
        super(ID, fullName, dateOfBirth, homeAddress, password);
        this.bankerSalary = bankerSalary;
    }

    public BigDecimal getBankerSalary() {
        BigDecimal getBankerSalary = Database.getBigDecimal("Accounts", "ID", getId().getID(), "Salary");
        if(getBankerSalary.compareTo(this.bankerSalary) != 0) setBankerSalary(getBankerSalary, 0);
        return bankerSalary;
    }

    public void setBankerSalary(BigDecimal bankerSalary, int save) {
        this.bankerSalary = bankerSalary;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.bankerSalary);
        }
    }

    public void addBankerSalary(BigDecimal bankerSalary, int save) {
        this.bankerSalary = getBankerSalary().add(bankerSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.bankerSalary);
        }
    }

    public void subtractBankerSalary(BigDecimal bankerSalary, int save) {
        this.bankerSalary = getBankerSalary().subtract(bankerSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.bankerSalary);
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(toString() + "'s salary: " + getBankerSalary());
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword, 1);
    }

    public void addCustomer() {
    }

    public void deleteCustomer(Customer customer) {
    }
}
