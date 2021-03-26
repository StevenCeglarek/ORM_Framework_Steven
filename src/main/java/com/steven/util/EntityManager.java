package com.steven.util;

import javax.persistence.Table;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EntityManager {

    public Map<String, ArrayList<String>> extractData(Object o) {
        Class clazz = o.getClass();
        String tableName = getTableName(o);
        if (!doesTableExist(tableName)) {
            createTable(o, tableName);
        }
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        ArrayList<String> fieldType = getFieldTypes(fields);
        ArrayList<String> fieldNames = getFieldNames(fields);
        ArrayList<String> fieldValues = getValues(o);
//        ArrayList<String> fieldOptions = new ArrayList<>();
        Map<String, ArrayList<String>> results = new HashMap<>();
        results.put("Types", fieldType);
        results.put("Names", fieldNames);
        results.put("Values", fieldValues);
        return results;
    }

    public String saveToDb(Map<String, ArrayList<String>> results, String tableName) {
        ArrayList<String> fieldNames = results.get("Names");
        ArrayList<String> fieldValues = results.get("Values");
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("INSERT into " + tableName + " (");
        for (int i = 1; i < fieldNames.size(); i++) {
            if(i < fieldNames.size()-1) {
                sqlQuery.append(fieldNames.get(i) + ", ");
            } else {
                sqlQuery.append(fieldNames.get(i));
            }
        }
        sqlQuery.append(") values (");
        for (int i = 0; i < fieldValues.size(); i++) {
            if (fieldValues.size() == 1) {
                sqlQuery.append(fieldValues.get(i));
                break;
            }
            if(i < fieldValues.size()-1) {
                sqlQuery.append(fieldValues.get(i) + ", ");
            } else {
                sqlQuery.append(fieldValues.get(i));
            }
        }
        sqlQuery.append(");");
        String sql = sqlQuery.toString();
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            Logger lgr = Logger.getLogger(EntityManager.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            return "Your INSERT was not completed";
        }
        return sql;
    }

    public String updateOne(String tableName, String columnName, int id, Object value) {
        String sql = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE id = ?";

        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setObject(1, value);
            ps.setObject(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return sql;
    }

    public ArrayList<Object> findAllFromTable(String tableName, Object o) {
        ArrayList<Object> tableData = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
                Class[] parameters = new Class[rsmd.getColumnCount()];
                while(rs.next()){
                    List<Object> values = new ArrayList<>();
                    Constructor constructor;
                    for(int i = 1; i <= rsmd.getColumnCount(); i++){
                        parameters[i-1] = rs.getObject(i).getClass();
                        values.add(rs.getObject(i));
                    }
                    constructor = o.getClass().getConstructor(parameters);
                    tableData.add(constructor.newInstance(values.toArray()));
                }
            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteTableFromDb(String tableName) {
        String sql = "Drop table " + tableName;
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean deleteRowByIdFromDb(String tableName, int id) {
        String sql = "Delete from  " + tableName + " where id = ?";
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;

    }

    public boolean createTable(Object o, String tableName) {
        List<Field> fields = Arrays.stream(o.getClass().getDeclaredFields()).collect(Collectors.toList());
        ArrayList<String> fieldType = getFieldTypes(fields);
        ArrayList<String> fieldNames = getFieldNames(fields);
        ArrayList<String> nameType = consolidateQuery(fieldNames, fieldType);
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("CREATE TABLE " +tableName+"(" +
               nameType.get(0) +  " SERIAL PRIMARY KEY,");
        for(int i = 2; i < nameType.size(); i+=2) {
            sqlQuery.append(nameType.get(i)+ " " + nameType.get(i+1) + ",");
        }
        sqlQuery.deleteCharAt(sqlQuery.length()-1);
        sqlQuery.append(");");
        String sql = sqlQuery.toString();
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public Object findByIdDb(String tableName, int id, Object o) {
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
                Constructor constructor = o.getClass().getConstructor(parameters);
                return constructor.newInstance(values.toArray());
            }
        } catch (SQLException e) {
            System.out.println("There was nothing in the database matching the given id");
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean doesTableExist(String tableName) {
        String sql = "SELECT * from ?";
        try (
                Connection conn = DataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ) {

            ps.setString(1, tableName);
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

//    TODO: Try and get Relationships working
//    public String checkForRelationship(Object o) {
//        if(o.getClass().isAnnotationPresent(OneToMany.class)) {
//            boolean oneToMany = true;
//            return "none";
//        }
//        if (o.getClass().isAnnotationPresent(ManyToOne.class)) {
//            String joinColumn = o.getClass().getAnnotation(JoinColumn.class).name();
//            return joinColumn;
//        }
//        return null;
//    }

    public ArrayList<String> consolidateQuery(ArrayList<String> names, ArrayList<String> types) {
        ArrayList<String> nameType = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            nameType.add(names.get(i));
            nameType.add(checkTypes(types.get(i)));
        }
        return nameType;
    }

    public ArrayList<String> getFieldNames(List<Field> fields) {
        ArrayList<String> fieldNames = new ArrayList<>();
        for(Field f : fields) {
            String fieldName = f.toString();
            String[] splits = fieldName.split("\\.");
            String endName = splits[splits.length - 1];
            fieldNames.add(endName);
        }
        return fieldNames;
    }

    public ArrayList<String> getFieldTypes(List<Field> fields) {
        // TODO: Need to get this method to work with primitives as well
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
        return fieldTypes;
    }

    public ArrayList<String> getValues(Object o) {
        String newString = o.toString();
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
        return fieldValueList;
    }

    public String getTableName(Object o) {
        String tableName = o.getClass().getAnnotation(Table.class).name();
        return tableName;
    }

    private String checkTypes(String s) {
        switch (s) {
            case"Integer": return "INTEGER";
            case"int": return "int4";
            case"Long": return "BIGINT";
            case"long": return "int8";
            case"Float":
            case"float": return "REAL";
            case"Boolean":
            case"boolean": return "BOOLEAN";
            case"Short":
            case"short": return "SMALLINT";
            case"Byte":
            case"byte": return "TINYINT";
            case"Double":
            case"double": return "FLOAT";
            default: return"TEXT";
        }
    }
//    TODO:This goes along with trying to get relationships working
//    private String checkObjTypes(String s) {
//        switch (s) {
//            case"Integer":
//            case"int": return "INTEGER";
//            case"Long":
//            case"long": return "BIGINT";
//            case"Float":
//            case"float": return "REAL";
//            case"Boolean":
//            case"boolean": return "BOOLEAN";
//            case"Short":
//            case"short": return "SMALLINT";
//            case"Byte":
//            case"byte": return "TINYINT";
//            case"Double":
//            case"double": return "FLOAT";
//            default: return"TEXT";
//        }
//    }
}
