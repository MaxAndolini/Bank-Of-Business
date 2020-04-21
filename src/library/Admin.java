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
public class Admin extends User {
    
    private int adminSalary;
   
    public Admin() {
        super();
        this.adminSalary = 0;
    }

    public Admin(int adminSalary, String ID, String name, String dateOfBirth, String homeAddress, String password) {
        super(ID, name, dateOfBirth, homeAddress, password);
        this.adminSalary = adminSalary;
    }

    public int getAdminSalary() {
        return adminSalary;
    }

    public void setAdminSalary(int adminSalary) {
        this.adminSalary = adminSalary;
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
