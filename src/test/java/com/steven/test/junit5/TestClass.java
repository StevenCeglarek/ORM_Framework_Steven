package com.steven.test.junit5;

import com.steven.test.models.VideoGame;
import com.steven.util.DataSource;
import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class TestClass {

    @Test
    @DisplayName("Should display field types of given class")
    void shouldCheckAllTypesOfAGivenClass() {
        ArrayList<String> values = new ArrayList<>();
        values.add("Integer");
        values.add("String");
        values.add("Double");
        values.add("String");
        values.add("String");
        Class clazz = new VideoGame().getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        ArrayList<String> fieldTypes = new ArrayList<>();
        for(Field f : fields) {
            String fieldType = f.getGenericType().getTypeName();
            if (fieldType.equals("int") || fieldType.equals("boolean") || fieldType.equals("double") || fieldType.equals("long")
                    || fieldType.equals("char") || fieldType.equals("float") || fieldType.equals("short") || fieldType.equals("byte")) {
                fieldTypes.add(fieldType);
            } else {
                String[] splits = fieldType.split("\\.");
                String endName = splits[2];
                fieldTypes.add(endName);
            }
        }
        Assertions.assertAll(() -> Assertions.assertEquals(values.get(0), fieldTypes.get(0)),
                () -> Assertions.assertEquals(values.get(1), fieldTypes.get(1)),
                () -> Assertions.assertEquals(values.get(2), fieldTypes.get(2)),
                () -> Assertions.assertEquals(values.get(3), fieldTypes.get(3)),
                () -> Assertions.assertEquals(values.get(4), fieldTypes.get(4)));
    }

    @Test
    @DisplayName("Should display field names of given class")
    void shouldCheckAllNamesOfAGivenClass() {
        ArrayList<String> values = new ArrayList<>();
        values.add("id");
        values.add("title");
        values.add("price");
        values.add("year");
        values.add("console");
        Class clazz = new VideoGame().getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        ArrayList<String> fieldNames = new ArrayList<>();
        for(Field f : fields) {
            String fieldName = f.toString();
            String[] splits = fieldName.split("\\.");
            String endName = splits[splits.length - 1];
            fieldNames.add(endName);
        }
        Assertions.assertAll(() -> Assertions.assertEquals(values.get(0), fieldNames.get(0)),
                () -> Assertions.assertEquals(values.get(1), fieldNames.get(1)),
                () -> Assertions.assertEquals(values.get(2), fieldNames.get(2)),
                () -> Assertions.assertEquals(values.get(3), fieldNames.get(3)),
                () -> Assertions.assertEquals(values.get(4), fieldNames.get(4)));
    }

    @Test
    @DisplayName("Should display all field values of given created class except id")
    void shouldCheckAllValuesOfAGivenCreatedClassExceptId() {
        VideoGame vg = new VideoGame(1, "Call of Duty Black ops Cold War", 59.99, "2020", "Xbox Series X");
        ArrayList<String> values = new ArrayList<>();
        values.add("'Call of Duty Black ops Cold War'");
        values.add("59.99");
        values.add("'2020'");
        values.add("'Xbox Series X'");
        Class clazz = new VideoGame().getClass();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        String newString = vg.toString();
        String[] newStringArray = newString.split(", ");
        ArrayList<String> fieldValueList = new ArrayList<>();
        for (int i = 1; i < newStringArray.length; i++) {
            String[] fieldArray = newStringArray[i].split("=");
            if(i == newStringArray.length-1) {
                fieldArray[1] = fieldArray[1].replaceAll("}", "");
            }
            for (int j = 1; j < fieldArray.length; j++) {
                fieldValueList.add(fieldArray[j]);
            }
        }
        Assertions.assertAll(() -> Assertions.assertEquals(values.get(0), fieldValueList.get(0)),
                () -> Assertions.assertEquals(values.get(1), fieldValueList.get(1)),
                () -> Assertions.assertEquals(values.get(2), fieldValueList.get(2)),
                () -> Assertions.assertEquals(values.get(3), fieldValueList.get(3)));
    }

    @Test
    @DisplayName("Should build new Object from data from database")
    void findById() {
        int id = 1;
        String tableName = "videoGames";
        VideoGame vg = new VideoGame();
        VideoGame vg1 = new VideoGame();
        vg.setId(id);
        vg.setTitle("Gears 6");
        vg.setPrice(59.99);
        vg.setYear("2021");
        vg.setConsole("Xbox One");
        String sql = "SELECT * FROM " + tableName + " WHERE id = " + id;
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            Class[] parameters = new Class[rsmd.getColumnCount()];
            List<Object> values = new ArrayList<>();

            while(rs.next()){
                for(int i = 1; i <= rsmd.getColumnCount(); i++){
                    parameters[i-1] = rs.getObject(i).getClass();
                    values.add(rs.getObject(i));
                }
                Constructor constructor = vg.getClass().getConstructor(parameters);
                vg1 = (VideoGame) constructor.newInstance(values.toArray());
            }
        } catch (SQLException e) {
            System.out.println("There was nothing in the database matching the given id");
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        VideoGame finalVg = vg1;
        Assertions.assertAll(
                () -> Assertions.assertEquals(vg.getId(), finalVg.getId()),
                () -> Assertions.assertEquals(vg.getTitle(), finalVg.getTitle()),
                () -> Assertions.assertEquals(vg.getPrice(), finalVg.getPrice()),
                () -> Assertions.assertEquals(vg.getYear(), finalVg.getYear()),
                () -> Assertions.assertEquals(vg.getConsole(), finalVg.getConsole()));
    }



}
