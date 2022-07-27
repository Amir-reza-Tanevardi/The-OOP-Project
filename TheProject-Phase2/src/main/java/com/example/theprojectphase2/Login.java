package com.example.theprojectphase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
   @FXML
   TextField username_text;

   @FXML
   PasswordField password_text;

    @FXML
    Label msg;

   @FXML
    private void SignUp(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("SignUp.fxml"));
       Parent parent = loader.load();

       Scene scene = new Scene(parent);

       Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

       MainStage.setScene(scene);
       MainStage.show();
   }

    @FXML
    private void LogIn(ActionEvent actionEvent) throws SQLException, IOException {
        String s1 = username_text.getText();
        String s2 = password_text.getText();
        DataBase dataBase = new DataBase();
        ResultSet set = DBManager.getResultSet("SELECT * FROM users WHERE username = '"+s1 +"' AND password = '"+s2 + "';");
        //set.next();

        if(!set.isBeforeFirst())
           msg.setText("User Not Found");

        else {
            set.next();
            if (set.getBoolean("isNormal")) {
                User user = User.initializeUser(set);
                LoginToMainPage(actionEvent, user);



            }
            else if (!set.getBoolean("isNormal")) {


            }
        }
    }

    @FXML
    public void LoginToMainPage(ActionEvent event , User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPage.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        MainPageController mainPageController = loader.getController();
        mainPageController.initialize(user);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();

    }

}