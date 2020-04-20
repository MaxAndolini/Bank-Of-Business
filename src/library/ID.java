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
public class ID {
    
    private String ID;
    private String name;
    private String dateOfBirth;
    
    public ID(){
        this.ID = "-1";
        this.name = "UNKNOWN";
        this.dateOfBirth = "00/00/0000";
    }
    
    public ID(String ID, String name, String dateOfBirth) {
        this.ID = ID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
