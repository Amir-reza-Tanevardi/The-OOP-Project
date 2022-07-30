package com.example.theprojectphase2;


import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class DBManagerTester {

    private static final String DB_NAME = "TheProjectDataBase";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_UNAME = "root";
    private static final String DB_PASS = "400100898";

    private static Connection conn = null;


    public static void insert(Object object) {
        createTableIfNotExist(object);
        if (object.getClass().isAnnotationPresent(DBTable.class)) {
            String tableName = "";
            for (Annotation anno : object.getClass().getAnnotations()) {
                if (anno.annotationType().equals(DBTable.class)) {
                    DBTable table = (DBTable) anno;
                    tableName = table.tableName();
                    break;
                }
            }

            String query = "INSERT INTO " + tableName+" ";
            String sub_query_record_fields = "";
            String sub_query_record_values = "";

            for (Field f : getAllFields(object.getClass())) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(DBField.class)) {
                    DBField anno = f.getAnnotation(DBField.class);
                    sub_query_record_fields += anno.name() + ",";
                    if (f.isAnnotationPresent(DBAutoIncrement.class)) {
                        try {
                            getConnection();
                            Statement st = conn.createStatement();
                            ResultSet res = st
                                    .executeQuery("SELECT max(" + f.getAnnotation(DBField.class).name() + ")" + " FROM "
                                            + tableName + ";");
                            int index = 0;
                            while (res.next()) {
                                index = res.getInt("max(" + f.getAnnotation(DBField.class).name() + ")");
                            }
                            f.set(object, index + 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        Object r = f.get(object);
                        if (needJson(r)) {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            sub_query_record_values += "\'" + gson.toJson(r) + "\'";
                        } else {
                            sub_query_record_values += "\'" + r + "\'";
                        }
                        sub_query_record_values += ",";

                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            sub_query_record_fields = sub_query_record_fields.replaceAll(",$", "");
            sub_query_record_fields = "(" + sub_query_record_fields + ")";
            sub_query_record_values = sub_query_record_values.replaceAll(",$", "");
            sub_query_record_values = "(" + sub_query_record_values + ")";
            query += sub_query_record_fields + " VALUES " + sub_query_record_values + ";";
            createTableIfNotExist(object);
            doUpdateQuery(query);

        } else {
            throw new IllegalArgumentException(
                    "The class " + object.getClass().getSimpleName()
                            + " most annotate DBTable and marks its fields with DBField Annotation\nFATAL ERROR\nexit program...\n");
        }
    }

    private static int doUpdateQuery(String query) {
        getConnection();
        try {
            Statement st = conn.createStatement();
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
        return -1;
    }

    public static <E> ArrayList<E> doSelectQuery(String query, Class<E> sample)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        getConnection();
        ArrayList<E> finalResult = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(query);
            while (result.next()) {
                E e = sample.getDeclaredConstructor().newInstance();
                for (Field field : getAllFields(sample)) {

                        field.setAccessible(true);

                    if (field.isAnnotationPresent(DBField.class)) {
                        DBField mf = field.getAnnotation(DBField.class);
                        try {
                            Object fieldValue = result.getObject(mf.name());
                            switch (field.getType().getSimpleName()) {
                                case "Integer":
                                    field.set(e,  (Integer)fieldValue);
                                    break;
                                case "Double":
                                    field.set(e, (Double) fieldValue);
                                    break;
                                case "Float":
                                    field.set(e, (Float) fieldValue);
                                    break;
                                case "String":
                                    field.set(e, (String) fieldValue);
                                default: {
                                    GsonBuilder builder = new GsonBuilder();
                                    Gson gson = builder.create();
                                    Object res = gson.fromJson((String) fieldValue, field.getType());
                                    res = field.getType().cast(res);
                                    field.set(e, res);
                                    break;
                                }
                            }
                            // result.next();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                finalResult.add(e);
            }
            return finalResult;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean needJson(Object obj) {
        return !(obj instanceof Integer || obj instanceof Double || obj instanceof Float || obj instanceof String);
    }

    private static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PASS);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\n##FATAL ERROR:\nerror with database!\nclosing program...\n");
                System.exit(-1);
            }
        }
        return conn;
    }

    private static void createTableIfNotExist(Object obj) {
        if (obj.getClass().isAnnotationPresent(DBTable.class)) {
            String tableName = "";
            String fieldsNames = "";
            for (Annotation anno : obj.getClass().getAnnotations()) {
                if (anno.annotationType().equals(DBTable.class)) {
                    DBTable annoTable = (DBTable) anno;
                    tableName = annoTable.tableName();
                }
            }
            for (Field f : getAllFields(obj.getClass())) {
                if (f.isAnnotationPresent(DBField.class)) {
                    DBField DBfield = f.getAnnotation(DBField.class);
                    fieldsNames += DBfield.name() + " ";
                    switch (f.getType().getSimpleName()) {
                        case "Double":
                        case "Float":
                            fieldsNames += "DOUBLE";
                            break;
                        case "Integer":
                            fieldsNames += "INT";
                            break;
                        case "String":
                            fieldsNames += "LONGTEXT";
                            break;
                        default:
                            fieldsNames += "JSON";
                            break;
                    }
                    if (f.isAnnotationPresent(DBAutoIncrement.class)) {
                        fieldsNames += " AUTO_INCREMENT";
                    }
                    if (f.isAnnotationPresent(DBPrimaryKey.class)) {
                        fieldsNames += " PRIMARY KEY";
                    }
                    fieldsNames += ",";
                }
            }
            fieldsNames = fieldsNames.replaceAll(",$", "");
            fieldsNames = "(" + fieldsNames + ")";
            String finalQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " " + fieldsNames + ";";
            doUpdateQuery(finalQuery);
        }
    }

    public static void update(Object obj) {
        if (obj.getClass().isAnnotationPresent(DBTable.class)) {
            DBTable tableAnno = obj.getClass().getAnnotation(DBTable.class);
            String query = "UPDATE " + tableAnno.tableName() + " SET ";
            String primaryKeyColumnName = "";
            Integer primaryKeyColumnValue = 0;
            try {
                for (Field f : getAllFields(obj.getClass())) {
                    f.setAccessible(true);
                    if (f.isAnnotationPresent(DBField.class)) {
                        if (f.isAnnotationPresent(DBPrimaryKey.class)) {
                            primaryKeyColumnName = f.getAnnotation(DBField.class).name();
                            primaryKeyColumnValue = (Integer) f.get(obj);
                        }
                        query += f.getAnnotation(DBField.class).name();
                        query += "=";
                        Object val = f.get(obj);
                        switch (val.getClass().getSimpleName()) {
                            case "Integer":
                            case "String":
                            case "Double":
                            case "Float":
                                query += "\'" + val + "\'";
                                break;
                            default:
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                query += "\'" + gson.toJson(f.get(obj)) + "\'";
                                break;
                        }
                        query += ",";
                    }
                }
                query = query.replaceAll(",$", "");
                query += " WHERE " + primaryKeyColumnName + "=" + primaryKeyColumnValue + ";";
                doUpdateQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static ArrayList<Field> getAllFields(Class<?> inputClass) {
        ArrayList<Field> result = new ArrayList<>();

        result.addAll(Arrays.asList(inputClass.getDeclaredFields()));
        Class<?> temp = inputClass.getSuperclass();

        while (temp != null) {
            result.addAll(Arrays.asList(temp.getDeclaredFields()));
            temp = temp.getSuperclass();
        }

        return result;
    }

    public static int deleteRecordIfExist(Object obj) throws IllegalArgumentException, IllegalAccessException {
        if (obj.getClass().isAnnotationPresent(DBTable.class)) {
            String tableName = obj.getClass().getAnnotation(DBTable.class).tableName();
            String query = "DELETE FROM " + tableName + " WHERE ";
            for (Field f : getAllFields(obj.getClass())) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(DBPrimaryKey.class)) {
                    query += f.getAnnotation(DBField.class).name();
                    query += "=";
                    query += f.get(obj);
                    break;
                }
            }
            if (!query.contains("=")) {
                throw new InputMismatchException("the class has no primary key!");
            }
            query += ";";
            return (doUpdateQuery(query));
        } else {
            throw new InputMismatchException("the class does not Annotate DBTable!");
        }
    }

}