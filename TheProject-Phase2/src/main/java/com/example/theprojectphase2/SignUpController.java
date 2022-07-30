package com.example.theprojectphase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SignUpController {

    @FXML
    TextField username_text;

    @FXML
    PasswordField password_text;

    @FXML
    PasswordField password2_text;

    @FXML
    TextField email_text;

    @FXML
    TextField phone_text;

    @FXML
    TextField age_text;

    @FXML
    TextField bio_text;

    @FXML
    ChoiceBox<String> gender_choose;

    @FXML
    ChoiceBox<String> privacy_choose;

    @FXML
    Label msg;


    @FXML
    private void BackToLogIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();
    }


    @FXML
    private void DoSignUp(ActionEvent event) throws SQLException, IOException {
        gender_choose.getItems().add("male");
        gender_choose.getItems().add("female");
        privacy_choose.getItems().add("private");
        privacy_choose.getItems().add("public");

        String s1 = username_text.getText();

        String s2 = password_text.getText();
        String s2r = password2_text.getText();

        //ResultSet set = DBManager.getResultSet("SELECT * FROM `users` WHERE username = '"+s1+"';");

        if(username_text.getText().isEmpty())
            msg.setText("Please Enter a UserName");

        else if(password_text.getText().isEmpty())
            msg.setText("Please Enter a Password");

        else if(password2_text.getText().isEmpty())
            msg.setText("Please Repeat your Password");

       else {
            //if (set.isBeforeFirst())
                //System.out.println("This username is already in use.");

            //else {
                if(!password_text.getText().equals(password2_text.getText()))
                    msg.setText("The Passwords Are Not The Same.");

                else{
                    User user = new User(s1, s2);

                    user.setEmail(email_text.getText());

                    user.setPhoneNumber(phone_text.getText());

                    user.setAge(Integer.parseInt(age_text.getText()));

                    user.setBio(bio_text.getText());

                    user.setGender(true);

                    user.setNormal(true);

                    //if (gender_choose.getValue().equals("male"))
                        //user.setGender(true);

                    //else if (gender_choose.getValue().equals("female"))
                        //user.setGender(false);

                   // if (privacy_choose.getValue().equals("private"))
                        //user.setPrivate(true);

                    // if (gender_choose.getValue().equals("public"))
                        //user.setPrivate(false);

                    User.Users.add(user);

                    DBManagerTester.insert(user);
                }

            //}
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();
    }


}
