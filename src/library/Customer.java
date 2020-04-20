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
public class Customer extends User {
    
    private String phoneNumber;
    private String job;
    private double moneyAmount;
    
    public Customer() {
        super();
        this.phoneNumber = "0000";
        this.job = "----";
        this.moneyAmount = 0;
    }

    public Customer(String ID, String name, String dateOfBirth, String password, String homeAddress, String phoneNumber, String job, double moneyAmount) {
        super(ID, name, dateOfBirth, password, homeAddress);
        this.phoneNumber = phoneNumber;
        this.job = job;
        this.moneyAmount = moneyAmount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(double moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(toString() + "'s telephone number: " + getPhoneNumber());
        System.out.println(toString() + "'s home adress: " + getHomeAddress());
        System.out.println(toString() + "'s job: " + getJob());
        System.out.println(toString() + "'s money amount in the account: " + getMoneyAmount());
    }
    
    public void deposit(int money) {
        setMoneyAmount(moneyAmount + money);
    }
    
    public void withdraw(int money) {
        setMoneyAmount(moneyAmount - money);
    }
    
    public void transferID(String ID) {
        
    }
    
    public void transferPhoneNumber(String telNo){
        
    } 
}
