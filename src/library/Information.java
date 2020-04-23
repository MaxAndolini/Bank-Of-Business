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
public class Information {
    
    private static Admin admin;
    private static Banker banker;
    private static Customer customer;

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        Information.admin = admin;
    }

    public static Banker getBanker() {
        return banker;
    }

    public static void setBanker(Banker banker) {
        Information.banker = banker;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        Information.customer = customer;
    }
}
