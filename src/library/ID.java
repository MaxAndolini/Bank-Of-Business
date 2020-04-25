/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author ercan
 */
public class ID {

    private String ID;
    private String fullName;
    private String dateOfBirth;

    public ID() {
        this.ID = "-1";
        this.fullName = "UNKNOWN";
        this.dateOfBirth = "00/00/0000";
    }

    public ID(String ID, String fullName, String dateOfBirth) {
        this.ID = ID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullName() {
        String getFullName = Database.getString("Accounts", "ID", getID(), "FullName");
        if(!getFullName.equals(this.fullName)) setFullName(getFullName, 0);
        return fullName;
    }

    public void setFullName(String fullName, int save) {
        this.fullName = fullName;
        if (save == 1) {
            Database.set("Accounts", "ID", getID(), "FullName", this.fullName);
        }
    }

    public String getDateOfBirth() {
        String getDateOfBirth = Database.getString("Accounts", "ID", getID(), "DateOfBirth");
        if(!getDateOfBirth.equals(this.dateOfBirth)) setDateOfBirth(getDateOfBirth, 0);
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth, int save) {
        this.dateOfBirth = dateOfBirth;
        if (save == 1) {
            Database.set("Accounts", "ID", getID(), "DateofBirth", this.dateOfBirth);
        }
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
}
