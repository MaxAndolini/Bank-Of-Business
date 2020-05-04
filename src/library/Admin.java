/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Admin(BigDecimal adminSalary, String ID, String fullName, String dateOfBirth, String phoneNumber, String homeAddress, String password) {
        super(ID, fullName, dateOfBirth, phoneNumber, homeAddress, password);
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
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addAdminSalary(BigDecimal adminSalary, int save) {
        this.adminSalary = getAdminSalary().add(adminSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.adminSalary);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractAdminSalary(BigDecimal adminSalary, int save) {
        this.adminSalary = getAdminSalary().subtract(adminSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.adminSalary);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }
}
