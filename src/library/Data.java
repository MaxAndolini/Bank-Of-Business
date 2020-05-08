/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    public static String generateCardNumber() {
        String result = "1";
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < 15; i++) {
            result += rand.nextInt(10);
        }
        if (!checkLuhn(result)) {
            return generateCardNumber();
        }
        if (Database.exists("Accounts", "CardNumber", result)) {
            return generateCardNumber();
        }
        return result;
    }

    public static boolean checkLuhn(String cardNumber) {
        int nDigits = cardNumber.length();
        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';
            if (isSecond == true) {
                d = d * 2;
            }
            nSum += d / 10;
            nSum += d % 10;
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public static boolean dateValidation(String date) {
        if (!date.matches("(0?[1-9]|[12][0-9]|3[01])\\/(0?[1-9]|1[0-2])\\/([0-9]{4})")) {
            return false;
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static String currencyFormat(int type, BigDecimal n) {
        switch (type) {
            case 0:
                return NumberFormat.getCurrencyInstance(Locale.US).format(n);
            case 1:
                return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(n);
            case 2:
                return NumberFormat.getCurrencyInstance(Locale.UK).format(n);
            case 3:
                return NumberFormat.getCurrencyInstance(new Locale("tr", "TR")).format(n);
        }
        return null;
    }
}
