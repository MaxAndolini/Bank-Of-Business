/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ercan
 */
public class Database {
    
    public static boolean fexists(String filename) {
        try {
            return Files.exists(Paths.get(filename + ".txt"));
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean fempty(String filename) {
        try {
            if(Files.size(Paths.get(filename + ".txt")) > 0) return false;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean fremove(String filename) {
        try {
            Files.delete(Paths.get(filename + ".txt"));
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean fcreate(String filename) {
        if(fexists(filename)) return false;
        try {
            Files.createFile(Paths.get(filename + ".txt"));
            if(!fexists(filename)) return false;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static int generateID(String filename) {
        if(!fexists(filename)) return -1;
        if(fempty(filename)) return -1;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return -1;
        int linecount = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                if(linecount != 0) {
                    String[] tmpline = line.split("[|]");
                    numbers.add(Integer.valueOf(tmpline[0]));
                } else linecount++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return getMissingNumber(numbers.stream().mapToInt(i -> i).toArray());
    }

    public static int getMissingNumber(int[] A) {  
        int n = A.length;  
        boolean[] numbersUsed = new boolean[n + 1];
        for(int k = 0; k < n; k++) if(A[k] <= n && A[k] >= 0) numbersUsed[A[k]] = true;
        for(int k = 0; k <= n; k++) if(numbersUsed[k] == false) return k;
        return -1;
    }   
    
    public static int columnExists(String filename) {
        if(!fexists(filename)) return 0;
        if(fempty(filename)) return 0;
        int count = 0;
        try {
            String line = Files.lines(Paths.get(filename + ".txt")).findFirst().get();
            for(int i = 0; i < line.length(); i++) {
                if(line.charAt(i) == '|') count++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return count;
    }
    
    public static boolean createColumn(String filename, int columnpos, int columnnum) {
        if(!fexists(filename)) return false;
        if(columnnum <= 0) return false;
        ArrayList<String> newlines = new ArrayList<>();
        if(fempty(filename)) {
            String line = "";
            for(int i = 0; i < columnnum; i++) line += "-|";
            newlines.add(line);
        } else {
            try {
                for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                    String newline = "";
                    String[] tmpline = line.split("[|]");
                    tmpline[columnpos] += "|";
                    for(int i = 0; i < columnnum - 1; i++) tmpline[columnpos] += "-|";
                    tmpline[columnpos] += "-";
                    for(String tmpline2 : tmpline) newline += (!tmpline2.equals("") ? (tmpline2 + "|") : "");
                    newlines.add(newline);
                }
            } catch (IOException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        try {
            Files.write(Paths.get(filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean setColumn(String filename, int column, String name) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return false;
        if(column < 0 || column >= columnexists) return false;
        int count = 0;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String newline = "";
                String[] tmpline = line.split("[|]");
                if(count == 0) {
                    tmpline[column] = name;
                    for(String tmpline2 : tmpline) newline += (!tmpline2.equals("") ? (tmpline2 + "|") : "-|");
                    count++;
                    newlines.add(newline);
                } else newlines.add(line);       
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            Files.write(Paths.get(filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean deleteColumn(String filename, int column) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return false;
        if(column < 0 || column >= columnexists) return false;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String newline = "";
                String[] tmpline = line.split("[|]");
                tmpline[column] = "";
                for(String tmpline2 : tmpline) newline += (!tmpline2.equals("") ? (tmpline2 + "|") : "");
                newlines.add(newline);
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            Files.write(Paths.get(filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static int columnNametoID(String filename, String name) {
        if(!fexists(filename)) return -1;
        if(fempty(filename)) return -1;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return -1;
        int count = 0;
        try {
            String line = Files.lines(Paths.get(filename + ".txt")).findFirst().get();
            String[] tmpline = line.split("[|]");
            for(String tmpline2 : tmpline) {
                if(tmpline2.equals(name)) return count;
                else count++;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return -1;
    }   
    
    public static boolean create(String filename) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return false;
        try {
            String line = generateID(filename) + "|";
            for(int i = 0; i < columnexists-1; i++) line += "-|";
            Files.writeString(Paths.get(filename + ".txt"), line, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, String name) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return false;
        int dataid = columnNametoID(filename, data);
        if(dataid == -1) return false;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String newline = "";
                String[] tmpline = line.split("[|]");
                if(tmpline[columnid].equals(columnvalue)) {
                    tmpline[dataid] = name;
                    for(String tmpline2 : tmpline) newline += (!tmpline2.equals("") ? (tmpline2 + "|") : "-|");
                    newlines.add(newline);
                } else newlines.add(line);       
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            Files.write(Paths.get(filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static String get(String filename, String column, String columnvalue, String data) {
        if(!fexists(filename)) return null;
        if(fempty(filename)) return null;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return null;
        int dataid = columnNametoID(filename, data);
        if(dataid == -1) return null;
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if(tmpline[columnid].equals(columnvalue)) {
                    if(tmpline[columnid].equals("-")) return null;
                    else return tmpline[columnid];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    
    public static String[] getArray(String filename, String column, String columnvalue, String data) {
        if(!fexists(filename)) return null;
        if(fempty(filename)) return null;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return null;
        int dataid = columnNametoID(filename, data);
        if(dataid == -1) return null;
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if(tmpline[columnid].equals(columnvalue)) return tmpline;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }
    
    public static boolean delete(String filename, String column, String columnvalue) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return false;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if(tmpline[columnid].equals(columnvalue)) continue;
                else newlines.add(line);       
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        try {
            Files.write(Paths.get(filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
