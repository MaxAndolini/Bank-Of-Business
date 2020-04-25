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
public class Admin extends User {

    private BigDecimal adminSalary;

    public Admin() {
        super();
        this.adminSalary = BigDecimal.ZERO;
    }

    public Admin(BigDecimal adminSalary, String ID, String fullName, String dateOfBirth, String homeAddress, String password) {
        super(ID, fullName, dateOfBirth, homeAddress, password);
        this.adminSalary = adminSalary;
    }

    public BigDecimal getAdminSalary() {
        BigDecimal getAdminSalary = Database.getBigDecimal("Accounts", "ID", getId().getID(), "Salary");
        if(getAdminSalary.compareTo(this.adminSalary) != 0) setAdminSalary(getAdminSalary, 0);
        return adminSalary;
    }

    public void setAdminSalary(BigDecimal adminSalary, int save) {
        this.adminSalary = adminSalary;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.adminSalary);
        }
    }

    public void addAdminSalary(BigDecimal adminSalary, int save) {
        this.adminSalary = this.adminSalary.add(adminSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.adminSalary);
        }
    }

    public void subtractAdminSalary(BigDecimal adminSalary, int save) {
        this.adminSalary = this.adminSalary.subtract(adminSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.adminSalary);
        }
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(toString() + "'s salary: " + getAdminSalary());
    }

    /*public void increaseSalary(String ID, int number) {
    }
    
    public void giveCredence(Banker banker, int amount) {
        banker.setBankerSalary(banker.getBankerSalary() + amount);
    }*/
    public void addBanker(Banker banker) {
    }

    public void deleteBanker() {
    }
}
