package com.example.theprojectphase2;

import java.sql.*;
import java.util.ArrayList;


public class DataBase {
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TheProjectDataBase" , "root" , "400100898");

    Statement statement = connection.createStatement();

    Statement updateEXP;



    public DataBase() throws SQLException {}

    public void UpdateQuery(String sql) throws SQLException {
        statement.executeUpdate(sql);

    }

    public ResultSet getResultSet(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }


}
