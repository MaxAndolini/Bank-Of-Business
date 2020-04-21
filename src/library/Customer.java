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
public class Customer extends User {
    
    private String job;
    private String phoneNumber;
    private BigDecimal dollar;
    private BigDecimal euro;
    private BigDecimal pound;
    private BigDecimal turkishLira;
    
    public Customer() {
        super();
        this.job = "----";
        this.phoneNumber = "0000";
        this.dollar = BigDecimal.valueOf(0);
        this.euro = BigDecimal.valueOf(0);
        this.pound = BigDecimal.valueOf(0);
        this.turkishLira = BigDecimal.valueOf(0);
    }

    public Customer(String job, String phoneNumber, BigDecimal dollar, BigDecimal euro, BigDecimal pound, BigDecimal turkishLira, String ID, String name, String dateOfBirth, String homeAddress, String password) {
        super(ID, name, dateOfBirth, homeAddress, password);
        this.job = job;
        this.phoneNumber = phoneNumber;
        this.dollar = dollar;
        this.euro = euro;
        this.pound = pound;
        this.turkishLira = turkishLira;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getDollar() {
        return dollar;
    }

    public void setDollar(BigDecimal dollar) {
        this.dollar = dollar;
    }

    public BigDecimal getEuro() {
        return euro;
    }

    public void setEuro(BigDecimal euro) {
        this.euro = euro;
    }

    public BigDecimal getPound() {
        return pound;
    }

    public void setPound(BigDecimal pound) {
        this.pound = pound;
    }

    public BigDecimal getTurkishLira() {
        return turkishLira;
    }

    public void setTurkishLira(BigDecimal turkishLira) {
        this.turkishLira = turkishLira;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(toString() + "'s telephone number: " + getPhoneNumber());
        System.out.println(toString() + "'s home adress: " + getHomeAddress());
        System.out.println(toString() + "'s job: " + getJob());
        System.out.println(toString() + "'s dollar amount: " + getDollar());
        System.out.println(toString() + "'s euro amount: " + getEuro());
        System.out.println(toString() + "'s pound amount: " + getPound());
        System.out.println(toString() + "'s turkish lira amount: " + getTurkishLira());
    }
    
    public void deposit(int money) {
        //setMoneyAmount(moneyAmount + money);
    }
    
    public void withdraw(int money) {
        //setMoneyAmount(moneyAmount - money);
    }
    
    public void transferID(String ID) {
        
    }
    
    public void transferPhoneNumber(String telNo){
        
    } 
}
