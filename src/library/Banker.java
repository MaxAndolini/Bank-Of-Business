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
public class Banker extends User {
    
    private int bankerSalary;

    public Banker() {
        super();
        this.bankerSalary = 0;
    }
    
    public Banker(int bankerSalary, String ID, String name, String dateOfBirth, String homeAddress, String password) {
        super(ID, name, dateOfBirth, homeAddress, password);
        this.bankerSalary = bankerSalary;
    }

    public int getBankerSalary() {
        return bankerSalary;
    }

    public void setBankerSalary(int bankerSalary) {
        this.bankerSalary = bankerSalary;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(toString() + "'s salary: " + getBankerSalary());
    }

    public void changePassword(String newPassword) {
        setPassword(newPassword);
    }
    
    public void addCustomer() {
    }
    
    public void deleteCustomer(Customer customer) {
    }
}
