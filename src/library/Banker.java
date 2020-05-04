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
public class Banker extends User {

    private BigDecimal bankerSalary;

    public Banker() {
        super();
        this.bankerSalary = BigDecimal.ZERO;
    }

    public Banker(BigDecimal bankerSalary, String ID, String fullName, String dateOfBirth, String phoneNumber, String homeAddress, String password) {
        super(ID, fullName, dateOfBirth, phoneNumber, homeAddress, password);
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
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addBankerSalary(BigDecimal bankerSalary, int save) {
        this.bankerSalary = getBankerSalary().add(bankerSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.bankerSalary);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractBankerSalary(BigDecimal bankerSalary, int save) {
        this.bankerSalary = getBankerSalary().subtract(bankerSalary);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Salary", this.bankerSalary);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }
}
