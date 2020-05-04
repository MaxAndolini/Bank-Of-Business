/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ercan
 */
public class Database {

    public static boolean dexists(String directoryname) {
        if (directoryname == null || directoryname.isBlank()) {
            return false;
        }
        try {
            return Files.exists(Paths.get(directoryname));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public static boolean dremove(String directoryname) {
        if (directoryname == null || directoryname.isBlank()) {
            return false;
        }
        try {
            Files.delete(Paths.get(directoryname));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean dcreate(String directoryname) {
        if (dexists(directoryname)) {
            return false;
        }
        try {
            Files.createDirectory(Paths.get(directoryname));
            if (!dexists(directoryname)) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean fexists(String filename) {
        if (filename == null || filename.isBlank()) {
            return false;
        }
        try {
            return Files.exists(Paths.get("Database/" + filename + ".txt"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public static boolean fempty(String filename) {
        if (filename == null || filename.isBlank()) {
            return true;
        }
        try {
            if (Files.size(Paths.get("Database/" + filename + ".txt")) > 0) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return true;
        }
        return true;
    }

    public static boolean fremove(String filename) {
        if (filename == null || filename.isBlank()) {
            return false;
        }
        try {
            Files.delete(Paths.get("Database/" + filename + ".txt"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean fcreate(String filename) {
        if (fexists(filename)) {
            return false;
        }
        try {
            if (!dexists("Database")) {
                dcreate("Database");
            }
            Files.createFile(Paths.get("Database/" + filename + ".txt"));
            if (!fexists(filename)) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static String generateID(String filename, String column) {
        if (!fexists(filename)) {
            return null;
        }
        if (fempty(filename)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return null;
        }
        int linecount = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                if (linecount != 0) {
                    String[] tmpline = line.split("[|]");
                    if (!tmpline[columnid].equals("-")) {
                        int id = isInteger(tmpline[columnid]);
                        if (id != -1) {
                            numbers.add(id);
                        }
                    }
                } else {
                    linecount++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return getMissingNumber(numbers.stream().mapToInt(i -> i).toArray());
    }

    public static String getMissingNumber(int[] A) {
        int n = A.length;
        boolean[] numbersUsed = new boolean[n + 1];
        for (int k = 0; k < n; k++) {
            if (A[k] <= n && A[k] >= 0) {
                numbersUsed[A[k]] = true;
            }
        }
        for (int k = 0; k <= n; k++) {
            if (numbersUsed[k] == false) {
                return Integer.toString(k);
            }
        }
        return null;
    }

    public static int columnExists(String filename) {
        if (!fexists(filename)) {
            return 0;
        }
        if (fempty(filename)) {
            return 0;
        }
        int count = 0;
        try {
            String line = Files.lines(Paths.get("Database/" + filename + ".txt")).findFirst().get();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '|') {
                    count++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return 0;
        }
        return count;
    }

    public static boolean createColumn(String filename, int columnpos, int columnnum) {
        if (!fexists(filename)) {
            return false;
        }
        if (columnpos < 0 || columnnum <= 0) {
            return false;
        }
        ArrayList<String> newlines = new ArrayList<>();
        if (fempty(filename)) {
            String line = "";
            for (int i = 0; i < columnnum; i++) {
                line += "-|";
            }
            newlines.add(line);
        } else {
            try {
                for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                    String[] tmpline = line.split("[|]");
                    tmpline[columnpos] += "|";
                    for (int i = 0; i < columnnum - 1; i++) {
                        tmpline[columnpos] += "-|";
                    }
                    tmpline[columnpos] += "-";
                    newlines.add(String.join("|", tmpline) + "|");
                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
                return false;
            }
        }
        try {
            Files.write(Paths.get("Database/" + filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean setColumn(String filename, int column, String name) {
        if (!fexists(filename)) {
            return false;
        }
        if (fempty(filename)) {
            return false;
        }
        int columnexists = columnExists(filename);
        if (columnexists == 0) {
            return false;
        }
        if (column < 0 || column >= columnexists) {
            return false;
        }
        if (name == null || name.isBlank()) {
            return false;
        }
        int count = 0;
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (count == 0) {
                    tmpline[column] = name;
                    newlines.add(String.join("|", tmpline) + "|");
                    count++;
                } else {
                    newlines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean deleteColumn(String filename, int column) {
        if (!fexists(filename)) {
            return false;
        }
        if (fempty(filename)) {
            return false;
        }
        int columnexists = columnExists(filename);
        if (columnexists == 0) {
            return false;
        }
        if (column < 0 || column >= columnexists) {
            return false;
        }
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                tmpline[column] = "";
                newlines.add(String.join("|", tmpline) + "|");
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static int columnNametoID(String filename, String name) {
        if (!fexists(filename)) {
            return -1;
        }
        if (fempty(filename)) {
            return -1;
        }
        int columnexists = columnExists(filename);
        if (columnexists == 0) {
            return -1;
        }
        if (name == null || name.isBlank()) {
            return -1;
        }
        int count = 0;
        try {
            String line = Files.lines(Paths.get("Database/" + filename + ".txt")).findFirst().get();
            String[] tmpline = line.split("[|]");
            for (String tmpline2 : tmpline) {
                if (tmpline2.equals(name)) {
                    return count;
                } else {
                    count++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return -1;
    }

    private static boolean existsPrivate(String filename, String column, String columnvalue) {
        if (!fexists(filename)) {
            return false;
        }
        if (fempty(filename)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return false;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return false;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (tmpline[columnid].equals(columnvalue)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
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

    public static boolean exists(String filename, String column, BigDecimal columnvalue) {
        return existsPrivate(filename, column, columnvalue.toString());
    }

    private static String createPrivate(String filename, String column, String columnvalue) {
        if (!fexists(filename)) {
            return null;
        }
        if (fempty(filename)) {
            return null;
        }
        int columnexists = columnExists(filename);
        if (columnexists == 0) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return null;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return null;
        }
        try {
            String line = "";
            for (int i = 0; i < columnexists; i++) {
                if (i == columnid) {
                    line += columnvalue + "|";
                } else {
                    line += "-|";
                }
            }
            Files.writeString(Paths.get("Database/" + filename + ".txt"), (line + System.lineSeparator()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return columnvalue;
    }

    public static String create(String filename) {
        String ID = generateID(filename, "ID");
        if (ID == null || ID.isBlank()) {
            return null;
        }
        return createPrivate(filename, "ID", ID);
    }

    public static double create(String filename, String column, double columnvalue) {
        return isDouble(createPrivate(filename, column, Double.toString(columnvalue)));
    }

    public static float create(String filename, String column, float columnvalue) {
        return isFloat(createPrivate(filename, column, Float.toString(columnvalue)));
    }

    public static int create(String filename, String column, int columnvalue) {
        return isInteger(createPrivate(filename, column, Integer.toString(columnvalue)));
    }

    public static String create(String filename, String column, String columnvalue) {
        return createPrivate(filename, column, columnvalue);
    }

    public static long create(String filename, String column, long columnvalue) {
        return isLong(createPrivate(filename, column, Long.toString(columnvalue)));
    }

    public static short create(String filename, String column, short columnvalue) {
        return isShort(createPrivate(filename, column, Short.toString(columnvalue)));
    }

    public static BigDecimal create(String filename, String column, BigDecimal columnvalue) {
        return isBigDecimal(createPrivate(filename, column, columnvalue.toString()));
    }

    private static boolean setPrivate(String filename, String column, String columnvalue, String data, String name) {
        if (!fexists(filename)) {
            return false;
        }
        if (fempty(filename)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return false;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return false;
        }
        if (data == null || data.isBlank()) {
            return false;
        }
        int dataid = columnNametoID(filename, data);
        if (dataid == -1) {
            return false;
        }
        if (name == null || name.isBlank()) {
            return false;
        }
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (tmpline[columnid].equals(columnvalue)) {
                    tmpline[dataid] = name;
                    newlines.add(String.join("|", tmpline) + "|");
                } else {
                    newlines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
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

    public static boolean set(String filename, String column, double columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, Double.toString(columnvalue), data, name.toString());
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

    public static boolean set(String filename, String column, float columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, Float.toString(columnvalue), data, name.toString());
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

    public static boolean set(String filename, String column, int columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, Integer.toString(columnvalue), data, name.toString());
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

    public static boolean set(String filename, String column, String columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, columnvalue, data, name.toString());
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

    public static boolean set(String filename, String column, long columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, Long.toString(columnvalue), data, name.toString());
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

    public static boolean set(String filename, String column, short columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, Short.toString(columnvalue), data, name.toString());
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, double name) {
        return setPrivate(filename, column, columnvalue.toString(), data, Double.toString(name));
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, float name) {
        return setPrivate(filename, column, columnvalue.toString(), data, Float.toString(name));
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, int name) {
        return setPrivate(filename, column, columnvalue.toString(), data, Integer.toString(name));
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, String name) {
        return setPrivate(filename, column, columnvalue.toString(), data, name);
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, long name) {
        return setPrivate(filename, column, columnvalue.toString(), data, Long.toString(name));
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, short name) {
        return setPrivate(filename, column, columnvalue.toString(), data, Short.toString(name));
    }

    public static boolean set(String filename, String column, BigDecimal columnvalue, String data, BigDecimal name) {
        return setPrivate(filename, column, columnvalue.toString(), data, name.toString());
    }

    private static String get(String filename, String column, String columnvalue, String data) {
        if (!fexists(filename)) {
            return null;
        }
        if (fempty(filename)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return null;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return null;
        }
        if (data == null || data.isBlank()) {
            return null;
        }
        int dataid = columnNametoID(filename, data);
        if (dataid == -1) {
            return null;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (tmpline[columnid].equals(columnvalue)) {
                    if (tmpline[dataid].equals("-")) {
                        return null;
                    } else {
                        return tmpline[dataid];
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
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

    public static double getDouble(String filename, String column, BigDecimal columnvalue, String data) {
        return isDouble(get(filename, column, columnvalue.toString(), data));
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

    public static float getFloat(String filename, String column, BigDecimal columnvalue, String data) {
        return isFloat(get(filename, column, columnvalue.toString(), data));
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

    public static int getInt(String filename, String column, BigDecimal columnvalue, String data) {
        return isInteger(get(filename, column, columnvalue.toString(), data));
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

    public static String getString(String filename, String column, BigDecimal columnvalue, String data) {
        return get(filename, column, columnvalue.toString(), data);
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

    public static long getLong(String filename, String column, BigDecimal columnvalue, String data) {
        return isLong(get(filename, column, columnvalue.toString(), data));
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

    public static short getShort(String filename, String column, BigDecimal columnvalue, String data) {
        return isShort(get(filename, column, columnvalue.toString(), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, double columnvalue, String data) {
        return isBigDecimal(get(filename, column, Double.toString(columnvalue), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, float columnvalue, String data) {
        return isBigDecimal(get(filename, column, Float.toString(columnvalue), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, int columnvalue, String data) {
        return isBigDecimal(get(filename, column, Integer.toString(columnvalue), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, String columnvalue, String data) {
        return isBigDecimal(get(filename, column, columnvalue, data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, long columnvalue, String data) {
        return isBigDecimal(get(filename, column, Long.toString(columnvalue), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, short columnvalue, String data) {
        return isBigDecimal(get(filename, column, Short.toString(columnvalue), data));
    }

    public static BigDecimal getBigDecimal(String filename, String column, BigDecimal columnvalue, String data) {
        return isBigDecimal(get(filename, column, columnvalue.toString(), data));
    }

    public static double isDouble(String s) {
        if (s == null || s.isBlank()) {
            return -1;
        }
        double num;
        try {
            num = Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return num;
    }

    public static float isFloat(String s) {
        if (s == null || s.isBlank()) {
            return -1;
        }
        float num;
        try {
            num = Float.parseFloat(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return num;
    }

    public static int isInteger(String s) {
        if (s == null || s.isBlank()) {
            return -1;
        }
        int num;
        try {
            num = Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return num;
    }

    public static long isLong(String s) {
        if (s == null || s.isBlank()) {
            return -1;
        }
        long num;
        try {
            num = Long.parseLong(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return num;
    }

    public static short isShort(String s) {
        if (s == null || s.isBlank()) {
            return -1;
        }
        short num;
        try {
            num = Short.parseShort(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return -1;
        }
        return num;
    }

    public static BigDecimal isBigDecimal(String s) {
        if (s == null || s.isBlank()) {
            return new BigDecimal("-1");
        }
        BigDecimal num;
        try {
            num = new BigDecimal(s);
        } catch (NumberFormatException | NullPointerException ex) {
            System.out.println(ex.toString());
            return new BigDecimal("-1");
        }
        return num;
    }

    private static String[] getArrayPrivate(String filename, String column, String columnvalue) {
        if (!fexists(filename)) {
            return null;
        }
        if (fempty(filename)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return null;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return null;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (tmpline[columnid].equals(columnvalue)) {
                    return tmpline;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return null;
    }

    public static String[] getArray(String filename, String column, double columnvalue) {
        return getArrayPrivate(filename, column, Double.toString(columnvalue));
    }

    public static String[] getArray(String filename, String column, float columnvalue) {
        return getArrayPrivate(filename, column, Float.toString(columnvalue));
    }

    public static String[] getArray(String filename, String column, int columnvalue) {
        return getArrayPrivate(filename, column, Integer.toString(columnvalue));
    }

    public static String[] getArray(String filename, String column, String columnvalue) {
        return getArrayPrivate(filename, column, columnvalue);
    }

    public static String[] getArray(String filename, String column, long columnvalue) {
        return getArrayPrivate(filename, column, Long.toString(columnvalue));
    }

    public static String[] getArray(String filename, String column, short columnvalue) {
        return getArrayPrivate(filename, column, Short.toString(columnvalue));
    }

    public static String[] getArray(String filename, String column, BigDecimal columnvalue) {
        return getArrayPrivate(filename, column, columnvalue.toString());
    }

    private static ArrayList<ArrayList<String>> getArrayListPrivate(String filename, String column, String columnvalue, String column2, String columnvalue2) {
        if (!fexists(filename)) {
            return null;
        }
        if (fempty(filename)) {
            return null;
        }
        int columnid = -1;
        if (column != null && !column.isBlank()) {
            columnid = columnNametoID(filename, column);
        }
        int columnid2 = -1;
        if (column2 != null && !column2.isBlank()) {
            columnid2 = columnNametoID(filename, column2);
        }
        int linecount = 0;
        ArrayList<ArrayList<String>> newlines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (linecount != 0) {
                    ArrayList<String> newrows = new ArrayList<>();
                    if ((columnid != -1 && columnvalue != null && !columnvalue.isBlank()) && (columnid2 == -1 || columnvalue2 == null || columnvalue2.isBlank())) {
                        if (tmpline[columnid].equals(columnvalue)) {
                            newrows.addAll(Arrays.asList(tmpline));
                            newlines.add(newrows);
                        }
                    } else if ((columnid == -1 || columnvalue == null || columnvalue.isBlank()) && (columnid2 != -1 && columnvalue2 != null && !columnvalue2.isBlank())) {
                        if (tmpline[columnid2].equals(columnvalue2)) {
                            newrows.addAll(Arrays.asList(tmpline));
                            newlines.add(newrows);
                        }
                    } else if ((columnid != -1 && columnvalue != null && !columnvalue.isBlank() && columnid2 != -1) && (columnvalue2 != null && !columnvalue2.isBlank())) {
                        if (tmpline[columnid].equals(columnvalue) && tmpline[columnid2].equals(columnvalue2)) {
                            newrows.addAll(Arrays.asList(tmpline));
                            newlines.add(newrows);
                        }
                    } else {
                        newrows.addAll(Arrays.asList(tmpline));
                        newlines.add(newrows);
                    }
                } else {
                    linecount++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return newlines;
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename) {
        return getArrayListPrivate(filename, null, null, null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue) {
        return getArrayListPrivate(filename, column, columnvalue, null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, double columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, Double.toString(columnvalue), column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, float columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, Float.toString(columnvalue), column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, int columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, Integer.toString(columnvalue), column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, String columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue, column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, long columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, Long.toString(columnvalue), column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, short columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, Short.toString(columnvalue), column2, columnvalue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, double columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, Double.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, float columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, Float.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, int columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, Integer.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, String columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, columnvalue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, long columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, Long.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, short columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, Short.toString(columnvalue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String filename, String column, BigDecimal columnvalue, String column2, BigDecimal columnvalue2) {
        return getArrayListPrivate(filename, column, columnvalue.toString(), column2, columnvalue2.toString());
    }

    private static boolean deletePrivate(String filename, String column, String columnvalue) {
        if (!fexists(filename)) {
            return false;
        }
        if (fempty(filename)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnid = columnNametoID(filename, column);
        if (columnid == -1) {
            return false;
        }
        if (columnvalue == null || columnvalue.isBlank()) {
            return false;
        }
        ArrayList<String> newlines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + filename + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpline = line.split("[|]");
                if (!tmpline[columnid].equals(columnvalue)) {
                    newlines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + filename + ".txt"), newlines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
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

    public static boolean delete(String filename, String column, BigDecimal columnvalue) {
        return deletePrivate(filename, column, columnvalue.toString());
    }
}
