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
public class Customer extends User {

    private String cardNumber;
    private String job;
    private BigDecimal dollar;
    private BigDecimal euro;
    private BigDecimal pound;
    private BigDecimal turkishLira;

    public Customer() {
        super();
        this.cardNumber = "-";
        this.job = "-";
        this.dollar = BigDecimal.valueOf(0);
        this.euro = BigDecimal.valueOf(0);
        this.pound = BigDecimal.valueOf(0);
        this.turkishLira = BigDecimal.valueOf(0);
    }

    public Customer(String cardNumber, String job, BigDecimal dollar, BigDecimal euro, BigDecimal pound, BigDecimal turkishLira, String ID, String fullName, String dateOfBirth, String phoneNumber, String homeAddress, String password) {
        super(ID, fullName, dateOfBirth, phoneNumber, homeAddress, password);
        this.cardNumber = cardNumber;
        this.job = job;
        this.dollar = dollar;
        this.euro = euro;
        this.pound = pound;
        this.turkishLira = turkishLira;
    }

    public String getCardNumber() {
        String getCardNumber = Database.getString("Accounts", "ID", getId().getID(), "CardNumber");
        if (!getCardNumber.equals(this.cardNumber)) {
            setCardNumber(getCardNumber, 0);
        }
        return cardNumber;
    }

    public void setCardNumber(String cardNumber, int save) {
        this.cardNumber = cardNumber;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "CardNumber", cardNumber);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public String getJob() {
        String getJob = Database.getString("Accounts", "ID", getId().getID(), "Job");
        if (!getJob.equals(this.job)) {
            setJob(getJob, 0);
        }
        return job;
    }

    public void setJob(String job, int save) {
        this.job = job;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Job", job);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public BigDecimal getDollar() {
        BigDecimal getDollar = Database.getBigDecimal("Accounts", "ID", getId().getID(), "Dollar");
        if (getDollar.compareTo(this.dollar) != 0) {
            setDollar(getDollar, 0);
        }
        return dollar;
    }

    public void setDollar(BigDecimal dollar, int save) {
        this.dollar = dollar;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Dollar", this.dollar);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addDollar(BigDecimal dollar, int save) {
        this.dollar = getDollar().add(dollar);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Dollar", this.dollar);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractDollar(BigDecimal dollar, int save) {
        this.dollar = getDollar().subtract(dollar);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Dollar", this.dollar);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public BigDecimal getEuro() {
        BigDecimal getEuro = Database.getBigDecimal("Accounts", "ID", getId().getID(), "Euro");
        if (getEuro.compareTo(this.euro) != 0) {
            setEuro(getEuro, 0);
        }
        return euro;
    }

    public void setEuro(BigDecimal euro, int save) {
        this.euro = euro;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Euro", this.euro);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addEuro(BigDecimal euro, int save) {
        this.euro = getEuro().add(euro);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Euro", this.euro);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractEuro(BigDecimal euro, int save) {
        this.euro = getEuro().subtract(euro);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Euro", this.euro);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public BigDecimal getPound() {
        BigDecimal getPound = Database.getBigDecimal("Accounts", "ID", getId().getID(), "Pound");
        if (getPound.compareTo(this.pound) != 0) {
            setPound(getPound, 0);
        }
        return pound;
    }

    public void setPound(BigDecimal pound, int save) {
        this.pound = pound;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Pound", this.pound);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addPound(BigDecimal pound, int save) {
        this.pound = getPound().add(pound);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Pound", this.pound);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractPound(BigDecimal pound, int save) {
        this.pound = getPound().subtract(pound);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "Pound", this.pound);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public BigDecimal getTurkishLira() {
        BigDecimal getTurkishLira = Database.getBigDecimal("Accounts", "ID", getId().getID(), "TurkishLira");
        if (getTurkishLira.compareTo(this.turkishLira) != 0) {
            setTurkishLira(getTurkishLira, 0);
        }
        return turkishLira;
    }

    public void setTurkishLira(BigDecimal turkishLira, int save) {
        this.turkishLira = turkishLira;
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "TurkishLira", this.turkishLira);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void addTurkishLira(BigDecimal turkishLira, int save) {
        this.turkishLira = getTurkishLira().add(turkishLira);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "TurkishLira", this.turkishLira);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public void subtractTurkishLira(BigDecimal turkishLira, int save) {
        this.turkishLira = getTurkishLira().subtract(turkishLira);
        if (save == 1) {
            Database.set("Accounts", "ID", getId().getID(), "TurkishLira", this.turkishLira);
            Database.set("Accounts", "ID", getId().getID(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
        }
    }

    public String showCardNumber() {
        return this.cardNumber.replaceAll(".{4}(?!$)", "$0-");
    }

    @Override
    public String leavingMessage() {
        return "Thank you for choosing us!";
    }
}
