package com.example.theprojectphase2;

import java.sql.Connection;
import java.sql.*;
import java.util.Scanner;

public class Main1 {

    public static void main(String[] args) throws SQLException {
         //boolean onSignup = false;
         Scanner myConsole = new Scanner(System.in);
         DataBase dataBase = new DataBase();




            while(true){
                System.out.println("1.log in");
                System.out.println("2.sign up");
                String s = myConsole.nextLine();


                if(s.contains("1")){
                  //User.Login(myConsole , dataBase);
                }

                else if(s.contains("2")){
                   User.SignUp(myConsole , dataBase);
                }



            }





    }







}
