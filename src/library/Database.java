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
        if(filename.length() == 0) return false;
        try {
            return Files.exists(Paths.get(filename + ".txt"));
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean fempty(String filename) {
        if(filename.length() == 0) return false;
        try {
            if(Files.size(Paths.get(filename + ".txt")) > 0) return false;
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean fremove(String filename) {
        if(filename.length() == 0) return false;
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
                    numbers.add(Integer.parseInt(tmpline[0]));
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
        if(name.length() == 0) return false;
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
    
    private static boolean existsPrivate(String filename, String column, String columnvalue) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return false;
        if(columnvalue.length() == 0) return false;
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if(tmpline[columnid].equals(columnvalue)) return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }
    
    public static boolean exists(String filename, String column, double columnvalue) {
        return existsPrivate(filename, column, Double.toString(columnvalue));
    }
    
    public static boolean exists(String filename, String column, float columnvalue) {
        return existsPrivate(filename, column, Float.toString(columnvalue));
    }
    
    public static boolean exists(String filename, String column, int columnvalue) {
        return existsPrivate(filename, column, Integer.toString(columnvalue));
    }
    
    public static boolean exists(String filename, String column, String columnvalue) {
        return existsPrivate(filename, column, columnvalue);
    }
    
    public static boolean exists(String filename, String column, long columnvalue) {
        return existsPrivate(filename, column, Long.toString(columnvalue));
    }
    
    public static boolean exists(String filename, String column, short columnvalue) {
        return existsPrivate(filename, column, Short.toString(columnvalue));
    }
    
    public static int create(String filename) {
        if(!fexists(filename)) return -1;
        if(fempty(filename)) return -1;
        int columnexists = columnExists(filename);
        if(columnexists == 0) return -1;
        int gID = generateID(filename);
        if(gID == -1) return -1;
        try {
            String line = gID + "|";
            for(int i = 0; i < columnexists-1; i++) line += "-|";
            Files.writeString(Paths.get(filename + ".txt"), (line + System.lineSeparator()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
        return gID;
    }
    
    private static boolean setPrivate(String filename, String column, String columnvalue, String data, String name) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return false;
        if(columnvalue.length() == 0) return false;
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
    
    public static boolean set(String filename, String column, double columnvalue, String data, double name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, double columnvalue, String data, float name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, double columnvalue, String data, int name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, double columnvalue, String data, String name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, name);
    }
    
    public static boolean set(String filename, String column, double columnvalue, String data, long name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, double columnvalue, String data, short name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, Short.toString(name));
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, double name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, float name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, int name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, String name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, name);
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, long name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, float columnvalue, String data, short name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, Short.toString(name));
    }

    public static boolean set(String filename, String column, int columnvalue, String data, double name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, int columnvalue, String data, float name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, int columnvalue, String data, int name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, int columnvalue, String data, String name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, name);
    }
    
    public static boolean set(String filename, String column, int columnvalue, String data, long name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, int columnvalue, String data, short name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, Short.toString(name));
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, double name) {
        return setPrivate(filename, column, columnvalue, data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, float name) {
        return setPrivate(filename, column, columnvalue, data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, int name) {
        return setPrivate(filename, column, columnvalue, data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, String name) {
        return setPrivate(filename, column, columnvalue, data, name);
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, long name) {
        return setPrivate(filename, column, columnvalue, data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, String columnvalue, String data, short name) {
        return setPrivate(filename, column, columnvalue, data, Short.toString(name));
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, double name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, float name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, int name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, String name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, name);
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, long name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, long columnvalue, String data, short name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, Short.toString(name));
    }

    public static boolean set(String filename, String column, short columnvalue, String data, double name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, Double.toString(name));
    }
    
    public static boolean set(String filename, String column, short columnvalue, String data, float name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, Float.toString(name));
    }
    
    public static boolean set(String filename, String column, short columnvalue, String data, int name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, Integer.toString(name));
    }
    
    public static boolean set(String filename, String column, short columnvalue, String data, String name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, name);
    }
    
    public static boolean set(String filename, String column, short columnvalue, String data, long name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, Long.toString(name));
    }
    
    public static boolean set(String filename, String column, short columnvalue, String data, short name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, Short.toString(name));
    }
    
    private static String get(String filename, String column, String columnvalue, String data) {
        if(!fexists(filename)) return null;
        if(fempty(filename)) return null;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return null;
        if(columnvalue.length() == 0) return null;
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
    
    public static double getDouble(String filename, String column, double columnvalue, String data) {
        return isDouble(get(filename, column, Double.toString(columnvalue), data));
    }
    
    public static double getDouble(String filename, String column, float columnvalue, String data) {
        return isDouble(get(filename, column, Float.toString(columnvalue), data));
    }
    
    public static double getDouble(String filename, String column, int columnvalue, String data) {
        return isDouble(get(filename, column, Integer.toString(columnvalue), data));
    }
    
    public static double getDouble(String filename, String column, String columnvalue, String data) {
        return isDouble(get(filename, column, columnvalue, data));
    }
    
    public static double getDouble(String filename, String column, long columnvalue, String data) {
        return isDouble(get(filename, column, Long.toString(columnvalue), data));
    }
    
    public static double getDouble(String filename, String column, short columnvalue, String data) {
        return isDouble(get(filename, column, Short.toString(columnvalue), data));
    }

    public static float getFloat(String filename, String column, double columnvalue, String data) {
        return isFloat(get(filename, column, Double.toString(columnvalue), data));
    }
    
    public static float getFloat(String filename, String column, float columnvalue, String data) {
        return isFloat(get(filename, column, Float.toString(columnvalue), data));
    }
    
    public static float getFloat(String filename, String column, int columnvalue, String data) {
        return isFloat(get(filename, column, Integer.toString(columnvalue), data));
    }
    
    public static float getFloat(String filename, String column, String columnvalue, String data) {
        return isFloat(get(filename, column, columnvalue, data));
    }
    
    public static float getFloat(String filename, String column, long columnvalue, String data) {
        return isFloat(get(filename, column, Long.toString(columnvalue), data));
    }
    
    public static float getFloat(String filename, String column, short columnvalue, String data) {
        return isFloat(get(filename, column, Short.toString(columnvalue), data));
    }
    
    public static int getInt(String filename, String column, double columnvalue, String data) {
        return isInteger(get(filename, column, Double.toString(columnvalue), data));
    }
    
    public static int getInt(String filename, String column, float columnvalue, String data) {
        return isInteger(get(filename, column, Float.toString(columnvalue), data));
    }
    
    public static int getInt(String filename, String column, int columnvalue, String data) {
        return isInteger(get(filename, column, Integer.toString(columnvalue), data));
    }
    
    public static int getInt(String filename, String column, String columnvalue, String data) {
        return isInteger(get(filename, column, columnvalue, data));
    }
    
    public static int getInt(String filename, String column, long columnvalue, String data) {
        return isInteger(get(filename, column, Long.toString(columnvalue), data));
    }
    
    public static int getInt(String filename, String column, short columnvalue, String data) {
        return isInteger(get(filename, column, Short.toString(columnvalue), data));
    }
   
    public static String getString(String filename, String column, double columnvalue, String data) {
        return get(filename, column, Double.toString(columnvalue), data);
    }
    
    public static String getString(String filename, String column, float columnvalue, String data) {
        return get(filename, column, Float.toString(columnvalue), data);
    }
    
    public static String getString(String filename, String column, int columnvalue, String data) {
        return get(filename, column, Integer.toString(columnvalue), data);
    }
    
    public static String getString(String filename, String column, String columnvalue, String data) {
        return get(filename, column, columnvalue, data);
    }
    
    public static String getString(String filename, String column, long columnvalue, String data) {
        return get(filename, column, Long.toString(columnvalue), data);
    }
    
    public static String getString(String filename, String column, short columnvalue, String data) {
        return get(filename, column, Short.toString(columnvalue), data);
    }
    
    public static long getLong(String filename, String column, double columnvalue, String data) {
        return isLong(get(filename, column, Double.toString(columnvalue), data));
    }
    
    public static long getLong(String filename, String column, float columnvalue, String data) {
        return isLong(get(filename, column, Float.toString(columnvalue), data));
    }
    
    public static long getLong(String filename, String column, int columnvalue, String data) {
        return isLong(get(filename, column, Integer.toString(columnvalue), data));
    }
    
    public static long getLong(String filename, String column, String columnvalue, String data) {
        return isLong(get(filename, column, columnvalue, data));
    }
    
    public static long getLong(String filename, String column, long columnvalue, String data) {
        return isLong(get(filename, column, Long.toString(columnvalue), data));
    }
    
    public static long getLong(String filename, String column, short columnvalue, String data) {
        return isLong(get(filename, column, Short.toString(columnvalue), data));
    }

    public static short getShort(String filename, String column, double columnvalue, String data) {
        return isShort(get(filename, column, Double.toString(columnvalue), data));
    }
    
    public static short getShort(String filename, String column, float columnvalue, String data) {
        return isShort(get(filename, column, Float.toString(columnvalue), data));
    }
    
    public static short getShort(String filename, String column, int columnvalue, String data) {
        return isShort(get(filename, column, Integer.toString(columnvalue), data));
    }
    
    public static short getShort(String filename, String column, String columnvalue, String data) {
        return isShort(get(filename, column, columnvalue, data));
    }
    
    public static short getShort(String filename, String column, long columnvalue, String data) {
        return isShort(get(filename, column, Long.toString(columnvalue), data));
    }
    
    public static short getShort(String filename, String column, short columnvalue, String data) {
        return isShort(get(filename, column, Short.toString(columnvalue), data));
    }
    
    public static double isDouble(String s) {
        if(s == null) return -1;
        try { 
            Double.parseDouble(s);
        } catch(NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1; 
        }
        return Double.parseDouble(s);
    }
    
    public static float isFloat(String s) {
        if(s == null) return -1;
        try { 
            Float.parseFloat(s);
        } catch(NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1; 
        }
        return Float.parseFloat(s);
    }

    public static int isInteger(String s) {
        if(s == null) return -1;
        try { 
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1; 
        }
        return Integer.parseInt(s);
    }
    
    public static long isLong(String s) {
        if(s == null) return -1;
        try { 
            Long.parseLong(s);
        } catch(NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1; 
        }
        return Long.parseLong(s);
    }
    
    public static short isShort(String s) {
        if(s == null) return -1;
        try { 
            Short.parseShort(s);
        } catch(NumberFormatException | NullPointerException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return -1; 
        }
        return Short.parseShort(s);
    }

    private static String[] getArrayPrivate(String filename, String column, String columnvalue, String data) {
        if(!fexists(filename)) return null;
        if(fempty(filename)) return null;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return null;
        if(columnvalue.length() == 0) return null;
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
    
    public static String[] getArray(String filename, String column, double columnvalue, String data) {
        return getArrayPrivate(filename, column, Double.toString(columnvalue), data);
    }
    
    public static String[] getArray(String filename, String column, float columnvalue, String data) {
        return getArrayPrivate(filename, column, Float.toString(columnvalue), data);
    }
    
    public static String[] getArray(String filename, String column, int columnvalue, String data) {
        return getArrayPrivate(filename, column, Integer.toString(columnvalue), data);
    }
    
    public static String[] getArray(String filename, String column, String columnvalue, String data) {
        return getArrayPrivate(filename, column, columnvalue, data);
    }
    
    public static String[] getArray(String filename, String column, long columnvalue, String data) {
        return getArrayPrivate(filename, column, Long.toString(columnvalue), data);
    }
    
    public static String[] getArray(String filename, String column, short columnvalue, String data) {
        return getArrayPrivate(filename, column, Short.toString(columnvalue), data);
    }
    
    private static boolean deletePrivate(String filename, String column, String columnvalue) {
        if(!fexists(filename)) return false;
        if(fempty(filename)) return false;
        int columnid = columnNametoID(filename, column);
        if(columnid == -1) return false;
        if(columnvalue.length() == 0) return false;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for(String line : Files.readAllLines(Paths.get(filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if(!tmpline[columnid].equals(columnvalue)) newlines.add(line);       
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
    
    public static boolean delete(String filename, String column, double columnvalue) {
        return deletePrivate(filename, column, Double.toString(columnvalue));
    }
    
    public static boolean delete(String filename, String column, float columnvalue) {
        return deletePrivate(filename, column, Float.toString(columnvalue));
    }
    
    public static boolean delete(String filename, String column, int columnvalue) {
        return deletePrivate(filename, column, Integer.toString(columnvalue));
    }
    
    public static boolean delete(String filename, String column, String columnvalue) {
        return deletePrivate(filename, column, columnvalue);
    }
    
    public static boolean delete(String filename, String column, long columnvalue) {
        return deletePrivate(filename, column, Long.toString(columnvalue));
    }
    
    public static boolean delete(String filename, String column, short columnvalue) {
        return deletePrivate(filename, column, Short.toString(columnvalue));
    }
}
