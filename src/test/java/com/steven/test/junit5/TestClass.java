package com.steven.test.junit5;

import com.steven.test.models.VideoGame;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
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



}
