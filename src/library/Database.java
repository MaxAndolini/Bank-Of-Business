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

    public static boolean dExists(String directoryName) {
        if (directoryName == null || directoryName.isBlank()) {
            return false;
        }
        try {
            return Files.exists(Paths.get(directoryName));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public static boolean dRemove(String directoryName) {
        if (directoryName == null || directoryName.isBlank()) {
            return false;
        }
        try {
            Files.delete(Paths.get(directoryName));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean dCreate(String directoryName) {
        if (dExists(directoryName)) {
            return false;
        }
        try {
            Files.createDirectory(Paths.get(directoryName));
            if (!dExists(directoryName)) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean fExists(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return false;
        }
        try {
            return Files.exists(Paths.get("Database/" + fileName + ".txt"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public static boolean fEmpty(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return true;
        }
        try {
            if (Files.size(Paths.get("Database/" + fileName + ".txt")) > 0) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return true;
        }
        return true;
    }

    public static boolean fRemove(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return false;
        }
        try {
            Files.delete(Paths.get("Database/" + fileName + ".txt"));
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean fCreate(String fileName) {
        if (fExists(fileName)) {
            return false;
        }
        try {
            if (!dExists("Database")) {
                dCreate("Database");
            }
            Files.createFile(Paths.get("Database/" + fileName + ".txt"));
            if (!fExists(fileName)) {
                return false;
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static String generateID(String fileName, String column) {
        if (!fExists(fileName)) {
            return null;
        }
        if (fEmpty(fileName)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return null;
        }
        int lineCount = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                if (lineCount != 0) {
                    String[] tmpLine = line.split("[|]");
                    if (!tmpLine[columnID].equals("-")) {
                        int id = isInteger(tmpLine[columnID]);
                        if (id != -1) {
                            numbers.add(id);
                        }
                    }
                } else {
                    lineCount++;
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

    public static int columnExists(String fileName) {
        if (!fExists(fileName)) {
            return 0;
        }
        if (fEmpty(fileName)) {
            return 0;
        }
        int count = 0;
        try {
            String line = Files.lines(Paths.get("Database/" + fileName + ".txt")).findFirst().get();
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

    public static boolean createColumn(String fileName, int columnpos, int columnnum) {
        if (!fExists(fileName)) {
            return false;
        }
        if (columnpos < 0 || columnnum <= 0) {
            return false;
        }
        ArrayList<String> newLines = new ArrayList<>();
        if (fEmpty(fileName)) {
            String line = "";
            for (int i = 0; i < columnnum; i++) {
                line += "-|";
            }
            newLines.add(line);
        } else {
            try {
                for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                    String[] tmpLine = line.split("[|]");
                    tmpLine[columnpos] += "|";
                    for (int i = 0; i < columnnum - 1; i++) {
                        tmpLine[columnpos] += "-|";
                    }
                    tmpLine[columnpos] += "-";
                    newLines.add(String.join("|", tmpLine) + "|");
                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
                return false;
            }
        }
        try {
            Files.write(Paths.get("Database/" + fileName + ".txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean setColumn(String fileName, int column, String name) {
        if (!fExists(fileName)) {
            return false;
        }
        if (fEmpty(fileName)) {
            return false;
        }
        int columnExists = columnExists(fileName);
        if (columnExists == 0) {
            return false;
        }
        if (column < 0 || column >= columnExists) {
            return false;
        }
        if (name == null || name.isBlank()) {
            return false;
        }
        int count = 0;
        ArrayList<String> newLines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (count == 0) {
                    tmpLine[column] = name;
                    newLines.add(String.join("|", tmpLine) + "|");
                    count++;
                } else {
                    newLines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + fileName + ".txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean deleteColumn(String fileName, int column) {
        if (!fExists(fileName)) {
            return false;
        }
        if (fEmpty(fileName)) {
            return false;
        }
        int columnExists = columnExists(fileName);
        if (columnExists == 0) {
            return false;
        }
        if (column < 0 || column >= columnExists) {
            return false;
        }
        ArrayList<String> newLines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                tmpLine[column] = "";
                newLines.add(String.join("|", tmpLine) + "|");
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + fileName + ".txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static int columnNametoID(String fileName, String name) {
        if (!fExists(fileName)) {
            return -1;
        }
        if (fEmpty(fileName)) {
            return -1;
        }
        int columnExists = columnExists(fileName);
        if (columnExists == 0) {
            return -1;
        }
        if (name == null || name.isBlank()) {
            return -1;
        }
        int count = 0;
        try {
            String line = Files.lines(Paths.get("Database/" + fileName + ".txt")).findFirst().get();
            String[] tmpLine = line.split("[|]");
            for (String tmpLine2 : tmpLine) {
                if (tmpLine2.equals(name)) {
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

    private static boolean existsPrivate(String fileName, String column, String columnValue) {
        if (!fExists(fileName)) {
            return false;
        }
        if (fEmpty(fileName)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return false;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return false;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (tmpLine[columnID].equals(columnValue)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return false;
    }

    public static boolean exists(String fileName, String column, double columnValue) {
        return existsPrivate(fileName, column, Double.toString(columnValue));
    }

    public static boolean exists(String fileName, String column, float columnValue) {
        return existsPrivate(fileName, column, Float.toString(columnValue));
    }

    public static boolean exists(String fileName, String column, int columnValue) {
        return existsPrivate(fileName, column, Integer.toString(columnValue));
    }

    public static boolean exists(String fileName, String column, String columnValue) {
        return existsPrivate(fileName, column, columnValue);
    }

    public static boolean exists(String fileName, String column, long columnValue) {
        return existsPrivate(fileName, column, Long.toString(columnValue));
    }

    public static boolean exists(String fileName, String column, short columnValue) {
        return existsPrivate(fileName, column, Short.toString(columnValue));
    }

    public static boolean exists(String fileName, String column, BigDecimal columnValue) {
        return existsPrivate(fileName, column, columnValue.toString());
    }

    private static String createPrivate(String fileName, String column, String columnValue) {
        if (!fExists(fileName)) {
            return null;
        }
        if (fEmpty(fileName)) {
            return null;
        }
        int columnExists = columnExists(fileName);
        if (columnExists == 0) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return null;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return null;
        }
        try {
            String line = "";
            for (int i = 0; i < columnExists; i++) {
                if (i == columnID) {
                    line += columnValue + "|";
                } else {
                    line += "-|";
                }
            }
            Files.writeString(Paths.get("Database/" + fileName + ".txt"), (line + System.lineSeparator()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return columnValue;
    }

    public static String create(String fileName) {
        String ID = generateID(fileName, "ID");
        if (ID == null || ID.isBlank()) {
            return null;
        }
        return createPrivate(fileName, "ID", ID);
    }

    public static double create(String fileName, String column, double columnValue) {
        return isDouble(createPrivate(fileName, column, Double.toString(columnValue)));
    }

    public static float create(String fileName, String column, float columnValue) {
        return isFloat(createPrivate(fileName, column, Float.toString(columnValue)));
    }

    public static int create(String fileName, String column, int columnValue) {
        return isInteger(createPrivate(fileName, column, Integer.toString(columnValue)));
    }

    public static String create(String fileName, String column, String columnValue) {
        return createPrivate(fileName, column, columnValue);
    }

    public static long create(String fileName, String column, long columnValue) {
        return isLong(createPrivate(fileName, column, Long.toString(columnValue)));
    }

    public static short create(String fileName, String column, short columnValue) {
        return isShort(createPrivate(fileName, column, Short.toString(columnValue)));
    }

    public static BigDecimal create(String fileName, String column, BigDecimal columnValue) {
        return isBigDecimal(createPrivate(fileName, column, columnValue.toString()));
    }

    private static boolean setPrivate(String fileName, String column, String columnValue, String data, String name) {
        if (!fExists(fileName)) {
            return false;
        }
        if (fEmpty(fileName)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return false;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return false;
        }
        if (data == null || data.isBlank()) {
            return false;
        }
        int dataID = columnNametoID(fileName, data);
        if (dataID == -1) {
            return false;
        }
        if (name == null || name.isBlank()) {
            return false;
        }
        ArrayList<String> newLines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (tmpLine[columnID].equals(columnValue)) {
                    tmpLine[dataID] = name;
                    newLines.add(String.join("|", tmpLine) + "|");
                } else {
                    newLines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + fileName + ".txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean set(String fileName, String column, double columnValue, String data, double name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, double columnValue, String data, float name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, double columnValue, String data, int name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, double columnValue, String data, String name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, name);
    }

    public static boolean set(String fileName, String column, double columnValue, String data, long name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, double columnValue, String data, short name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, double columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, Double.toString(columnValue), data, name.toString());
    }

    public static boolean set(String fileName, String column, float columnValue, String data, double name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, float columnValue, String data, float name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, float columnValue, String data, int name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, float columnValue, String data, String name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, name);
    }

    public static boolean set(String fileName, String column, float columnValue, String data, long name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, float columnValue, String data, short name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, float columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, Float.toString(columnValue), data, name.toString());
    }

    public static boolean set(String fileName, String column, int columnValue, String data, double name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, int columnValue, String data, float name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, int columnValue, String data, int name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, int columnValue, String data, String name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, name);
    }

    public static boolean set(String fileName, String column, int columnValue, String data, long name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, int columnValue, String data, short name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, int columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, Integer.toString(columnValue), data, name.toString());
    }

    public static boolean set(String fileName, String column, String columnValue, String data, double name) {
        return setPrivate(fileName, column, columnValue, data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, String columnValue, String data, float name) {
        return setPrivate(fileName, column, columnValue, data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, String columnValue, String data, int name) {
        return setPrivate(fileName, column, columnValue, data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, String columnValue, String data, String name) {
        return setPrivate(fileName, column, columnValue, data, name);
    }

    public static boolean set(String fileName, String column, String columnValue, String data, long name) {
        return setPrivate(fileName, column, columnValue, data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, String columnValue, String data, short name) {
        return setPrivate(fileName, column, columnValue, data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, String columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, columnValue, data, name.toString());
    }

    public static boolean set(String fileName, String column, long columnValue, String data, double name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, long columnValue, String data, float name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, long columnValue, String data, int name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, long columnValue, String data, String name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, name);
    }

    public static boolean set(String fileName, String column, long columnValue, String data, long name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, long columnValue, String data, short name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, long columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, Long.toString(columnValue), data, name.toString());
    }

    public static boolean set(String fileName, String column, short columnValue, String data, double name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, short columnValue, String data, float name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, short columnValue, String data, int name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, short columnValue, String data, String name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, name);
    }

    public static boolean set(String fileName, String column, short columnValue, String data, long name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, short columnValue, String data, short name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, short columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, Short.toString(columnValue), data, name.toString());
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, double name) {
        return setPrivate(fileName, column, columnValue.toString(), data, Double.toString(name));
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, float name) {
        return setPrivate(fileName, column, columnValue.toString(), data, Float.toString(name));
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, int name) {
        return setPrivate(fileName, column, columnValue.toString(), data, Integer.toString(name));
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, String name) {
        return setPrivate(fileName, column, columnValue.toString(), data, name);
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, long name) {
        return setPrivate(fileName, column, columnValue.toString(), data, Long.toString(name));
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, short name) {
        return setPrivate(fileName, column, columnValue.toString(), data, Short.toString(name));
    }

    public static boolean set(String fileName, String column, BigDecimal columnValue, String data, BigDecimal name) {
        return setPrivate(fileName, column, columnValue.toString(), data, name.toString());
    }

    private static String get(String fileName, String column, String columnValue, String data) {
        if (!fExists(fileName)) {
            return null;
        }
        if (fEmpty(fileName)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return null;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return null;
        }
        if (data == null || data.isBlank()) {
            return null;
        }
        int dataID = columnNametoID(fileName, data);
        if (dataID == -1) {
            return null;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (tmpLine[columnID].equals(columnValue)) {
                    if (tmpLine[dataID].equals("-")) {
                        return null;
                    } else {
                        return tmpLine[dataID];
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return null;
    }

    public static double getDouble(String fileName, String column, double columnValue, String data) {
        return isDouble(get(fileName, column, Double.toString(columnValue), data));
    }

    public static double getDouble(String fileName, String column, float columnValue, String data) {
        return isDouble(get(fileName, column, Float.toString(columnValue), data));
    }

    public static double getDouble(String fileName, String column, int columnValue, String data) {
        return isDouble(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static double getDouble(String fileName, String column, String columnValue, String data) {
        return isDouble(get(fileName, column, columnValue, data));
    }

    public static double getDouble(String fileName, String column, long columnValue, String data) {
        return isDouble(get(fileName, column, Long.toString(columnValue), data));
    }

    public static double getDouble(String fileName, String column, short columnValue, String data) {
        return isDouble(get(fileName, column, Short.toString(columnValue), data));
    }

    public static double getDouble(String fileName, String column, BigDecimal columnValue, String data) {
        return isDouble(get(fileName, column, columnValue.toString(), data));
    }

    public static float getFloat(String fileName, String column, double columnValue, String data) {
        return isFloat(get(fileName, column, Double.toString(columnValue), data));
    }

    public static float getFloat(String fileName, String column, float columnValue, String data) {
        return isFloat(get(fileName, column, Float.toString(columnValue), data));
    }

    public static float getFloat(String fileName, String column, int columnValue, String data) {
        return isFloat(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static float getFloat(String fileName, String column, String columnValue, String data) {
        return isFloat(get(fileName, column, columnValue, data));
    }

    public static float getFloat(String fileName, String column, long columnValue, String data) {
        return isFloat(get(fileName, column, Long.toString(columnValue), data));
    }

    public static float getFloat(String fileName, String column, short columnValue, String data) {
        return isFloat(get(fileName, column, Short.toString(columnValue), data));
    }

    public static float getFloat(String fileName, String column, BigDecimal columnValue, String data) {
        return isFloat(get(fileName, column, columnValue.toString(), data));
    }

    public static int getInt(String fileName, String column, double columnValue, String data) {
        return isInteger(get(fileName, column, Double.toString(columnValue), data));
    }

    public static int getInt(String fileName, String column, float columnValue, String data) {
        return isInteger(get(fileName, column, Float.toString(columnValue), data));
    }

    public static int getInt(String fileName, String column, int columnValue, String data) {
        return isInteger(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static int getInt(String fileName, String column, String columnValue, String data) {
        return isInteger(get(fileName, column, columnValue, data));
    }

    public static int getInt(String fileName, String column, long columnValue, String data) {
        return isInteger(get(fileName, column, Long.toString(columnValue), data));
    }

    public static int getInt(String fileName, String column, short columnValue, String data) {
        return isInteger(get(fileName, column, Short.toString(columnValue), data));
    }

    public static int getInt(String fileName, String column, BigDecimal columnValue, String data) {
        return isInteger(get(fileName, column, columnValue.toString(), data));
    }

    public static String getString(String fileName, String column, double columnValue, String data) {
        return get(fileName, column, Double.toString(columnValue), data);
    }

    public static String getString(String fileName, String column, float columnValue, String data) {
        return get(fileName, column, Float.toString(columnValue), data);
    }

    public static String getString(String fileName, String column, int columnValue, String data) {
        return get(fileName, column, Integer.toString(columnValue), data);
    }

    public static String getString(String fileName, String column, String columnValue, String data) {
        return get(fileName, column, columnValue, data);
    }

    public static String getString(String fileName, String column, long columnValue, String data) {
        return get(fileName, column, Long.toString(columnValue), data);
    }

    public static String getString(String fileName, String column, short columnValue, String data) {
        return get(fileName, column, Short.toString(columnValue), data);
    }

    public static String getString(String fileName, String column, BigDecimal columnValue, String data) {
        return get(fileName, column, columnValue.toString(), data);
    }

    public static long getLong(String fileName, String column, double columnValue, String data) {
        return isLong(get(fileName, column, Double.toString(columnValue), data));
    }

    public static long getLong(String fileName, String column, float columnValue, String data) {
        return isLong(get(fileName, column, Float.toString(columnValue), data));
    }

    public static long getLong(String fileName, String column, int columnValue, String data) {
        return isLong(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static long getLong(String fileName, String column, String columnValue, String data) {
        return isLong(get(fileName, column, columnValue, data));
    }

    public static long getLong(String fileName, String column, long columnValue, String data) {
        return isLong(get(fileName, column, Long.toString(columnValue), data));
    }

    public static long getLong(String fileName, String column, short columnValue, String data) {
        return isLong(get(fileName, column, Short.toString(columnValue), data));
    }

    public static long getLong(String fileName, String column, BigDecimal columnValue, String data) {
        return isLong(get(fileName, column, columnValue.toString(), data));
    }

    public static short getShort(String fileName, String column, double columnValue, String data) {
        return isShort(get(fileName, column, Double.toString(columnValue), data));
    }

    public static short getShort(String fileName, String column, float columnValue, String data) {
        return isShort(get(fileName, column, Float.toString(columnValue), data));
    }

    public static short getShort(String fileName, String column, int columnValue, String data) {
        return isShort(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static short getShort(String fileName, String column, String columnValue, String data) {
        return isShort(get(fileName, column, columnValue, data));
    }

    public static short getShort(String fileName, String column, long columnValue, String data) {
        return isShort(get(fileName, column, Long.toString(columnValue), data));
    }

    public static short getShort(String fileName, String column, short columnValue, String data) {
        return isShort(get(fileName, column, Short.toString(columnValue), data));
    }

    public static short getShort(String fileName, String column, BigDecimal columnValue, String data) {
        return isShort(get(fileName, column, columnValue.toString(), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, double columnValue, String data) {
        return isBigDecimal(get(fileName, column, Double.toString(columnValue), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, float columnValue, String data) {
        return isBigDecimal(get(fileName, column, Float.toString(columnValue), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, int columnValue, String data) {
        return isBigDecimal(get(fileName, column, Integer.toString(columnValue), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, String columnValue, String data) {
        return isBigDecimal(get(fileName, column, columnValue, data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, long columnValue, String data) {
        return isBigDecimal(get(fileName, column, Long.toString(columnValue), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, short columnValue, String data) {
        return isBigDecimal(get(fileName, column, Short.toString(columnValue), data));
    }

    public static BigDecimal getBigDecimal(String fileName, String column, BigDecimal columnValue, String data) {
        return isBigDecimal(get(fileName, column, columnValue.toString(), data));
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

    private static String[] getArrayPrivate(String fileName, String column, String columnValue) {
        if (!fExists(fileName)) {
            return null;
        }
        if (fEmpty(fileName)) {
            return null;
        }
        if (column == null || column.isBlank()) {
            return null;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return null;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return null;
        }
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (tmpLine[columnID].equals(columnValue)) {
                    return tmpLine;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return null;
    }

    public static String[] getArray(String fileName, String column, double columnValue) {
        return getArrayPrivate(fileName, column, Double.toString(columnValue));
    }

    public static String[] getArray(String fileName, String column, float columnValue) {
        return getArrayPrivate(fileName, column, Float.toString(columnValue));
    }

    public static String[] getArray(String fileName, String column, int columnValue) {
        return getArrayPrivate(fileName, column, Integer.toString(columnValue));
    }

    public static String[] getArray(String fileName, String column, String columnValue) {
        return getArrayPrivate(fileName, column, columnValue);
    }

    public static String[] getArray(String fileName, String column, long columnValue) {
        return getArrayPrivate(fileName, column, Long.toString(columnValue));
    }

    public static String[] getArray(String fileName, String column, short columnValue) {
        return getArrayPrivate(fileName, column, Short.toString(columnValue));
    }

    public static String[] getArray(String fileName, String column, BigDecimal columnValue) {
        return getArrayPrivate(fileName, column, columnValue.toString());
    }

    private static ArrayList<ArrayList<String>> getArrayListPrivate(String fileName, String column, String columnValue, String column2, String columnValue2) {
        if (!fExists(fileName)) {
            return null;
        }
        if (fEmpty(fileName)) {
            return null;
        }
        int columnID = -1;
        if (column != null && !column.isBlank()) {
            columnID = columnNametoID(fileName, column);
        }
        int columnID2 = -1;
        if (column2 != null && !column2.isBlank()) {
            columnID2 = columnNametoID(fileName, column2);
        }
        int lineCount = 0;
        ArrayList<ArrayList<String>> newLines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (lineCount != 0) {
                    ArrayList<String> newRows = new ArrayList<>();
                    if ((columnID != -1 && columnValue != null && !columnValue.isBlank()) && (columnID2 == -1 || columnValue2 == null || columnValue2.isBlank())) {
                        if (tmpLine[columnID].equals(columnValue)) {
                            newRows.addAll(Arrays.asList(tmpLine));
                            newLines.add(newRows);
                        }
                    } else if ((columnID == -1 || columnValue == null || columnValue.isBlank()) && (columnID2 != -1 && columnValue2 != null && !columnValue2.isBlank())) {
                        if (tmpLine[columnID2].equals(columnValue2)) {
                            newRows.addAll(Arrays.asList(tmpLine));
                            newLines.add(newRows);
                        }
                    } else if ((columnID != -1 && columnValue != null && !columnValue.isBlank() && columnID2 != -1) && (columnValue2 != null && !columnValue2.isBlank())) {
                        if (tmpLine[columnID].equals(columnValue) && tmpLine[columnID2].equals(columnValue2)) {
                            newRows.addAll(Arrays.asList(tmpLine));
                            newLines.add(newRows);
                        }
                    } else {
                        newRows.addAll(Arrays.asList(tmpLine));
                        newLines.add(newRows);
                    }
                } else {
                    lineCount++;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return newLines;
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName) {
        return getArrayListPrivate(fileName, null, null, null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue) {
        return getArrayListPrivate(fileName, column, columnValue, null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), null, null);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, double columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, Double.toString(columnValue), column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, float columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, Float.toString(columnValue), column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, int columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, Integer.toString(columnValue), column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, String columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue, column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, long columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, Long.toString(columnValue), column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, short columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, Short.toString(columnValue), column2, columnValue2.toString());
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, double columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, Double.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, float columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, Float.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, int columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, Integer.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, String columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, columnValue2);
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, long columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, Long.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, short columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, Short.toString(columnValue2));
    }

    public static ArrayList<ArrayList<String>> getArrayList(String fileName, String column, BigDecimal columnValue, String column2, BigDecimal columnValue2) {
        return getArrayListPrivate(fileName, column, columnValue.toString(), column2, columnValue2.toString());
    }

    private static boolean deletePrivate(String fileName, String column, String columnValue) {
        if (!fExists(fileName)) {
            return false;
        }
        if (fEmpty(fileName)) {
            return false;
        }
        if (column == null || column.isBlank()) {
            return false;
        }
        int columnID = columnNametoID(fileName, column);
        if (columnID == -1) {
            return false;
        }
        if (columnValue == null || columnValue.isBlank()) {
            return false;
        }
        ArrayList<String> newLines = new ArrayList<>();
        try {
            for (String line : Files.readAllLines(Paths.get("Database/" + fileName + ".txt"), StandardCharsets.UTF_8)) {
                String[] tmpLine = line.split("[|]");
                if (!tmpLine[columnID].equals(columnValue)) {
                    newLines.add(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        try {
            Files.write(Paths.get("Database/" + fileName + ".txt"), newLines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public static boolean delete(String fileName, String column, double columnValue) {
        return deletePrivate(fileName, column, Double.toString(columnValue));
    }

    public static boolean delete(String fileName, String column, float columnValue) {
        return deletePrivate(fileName, column, Float.toString(columnValue));
    }

    public static boolean delete(String fileName, String column, int columnValue) {
        return deletePrivate(fileName, column, Integer.toString(columnValue));
    }

    public static boolean delete(String fileName, String column, String columnValue) {
        return deletePrivate(fileName, column, columnValue);
    }

    public static boolean delete(String fileName, String column, long columnValue) {
        return deletePrivate(fileName, column, Long.toString(columnValue));
    }

    public static boolean delete(String fileName, String column, short columnValue) {
        return deletePrivate(fileName, column, Short.toString(columnValue));
    }

    public static boolean delete(String fileName, String column, BigDecimal columnValue) {
        return deletePrivate(fileName, column, columnValue.toString());
    }
}
