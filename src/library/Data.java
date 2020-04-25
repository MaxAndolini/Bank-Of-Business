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
public class Data {

    private static Admin admin;
    private static Banker banker;
    private static Customer customer;
    private static String page1;
    private static String page2;
    private static BigDecimal money;
    private static int moneyType;
    private static String transfer;

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        Data.admin = admin;
    }

    public static Banker getBanker() {
        return banker;
    }

    public static void setBanker(Banker banker) {
        Data.banker = banker;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        Data.customer = customer;
    }

    public static String getPage1() {
        return page1;
    }

    public static void setPage1(String page1) {
        Data.page1 = page1;
    }

    public static String getPage2() {
        return page2;
    }

    public static void setPage2(String page2) {
        Data.page2 = page2;
    }

    public static BigDecimal getMoney() {
        return money;
    }

    public static void setMoney(BigDecimal money) {
        Data.money = money;
    }

    public static int getMoneyType() {
        return moneyType;
    }

    public static void setMoneyType(int moneyType) {
        Data.moneyType = moneyType;
    }

    public static String getTransfer() {
        return transfer;
    }

    public static void setTransfer(String transfer) {
        Data.transfer = transfer;
    }
}
