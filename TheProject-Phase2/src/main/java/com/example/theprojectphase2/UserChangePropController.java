package com.example.theprojectphase2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class UserChangePropController {

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
    ImageView picture;

    @FXML
    ComboBox<String> gender_choose;

    @FXML
    ComboBox<String> type_choose;

    @FXML
    Label msg;

    User us;

    User u;

    ObservableList<String> genderList = FXCollections.observableArrayList("Male","Female");
    ObservableList<String> typeList = FXCollections.observableArrayList("Normal","Business");

    public void initialize(User user){
        username_text.setText(user.getUserName());

        password_text.setText(user.getPassWord());

        password2_text.setText(user.getPassWord());

        email_text.setText(user.getEmail());

        phone_text.setText(user.getPhoneNumber());

        age_text.setText(String.valueOf(user.getAge()));

        bio_text.setText(user.getBio());

        picture.setImage(user.getImage());

        if(user.getGender())
            gender_choose.setValue("Male");
        else
            gender_choose.setValue("Female");

        if(user.getNormal())
            type_choose.setValue("Normal");
        else
            type_choose.setValue("Business");

        us = user;

        u = user;

    }

    @FXML
    private void BackToLogIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPage.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        MainPageController mainPageController = loader.getController();
        mainPageController.initialize(u);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();
    }


    @FXML
    private void DoSignUp(ActionEvent event) throws SQLException, IOException {
        gender_choose.setItems(genderList);
        type_choose.setItems(typeList);


        String s1 = username_text.getText();

        String s2 = password_text.getText();
        String s2r = password2_text.getText();


        if(picture.getImage() == null) {
            Random random = new Random();
            int r = random.nextInt(4);

            if (r == 0) {
                us.setImage(new Image("D:\\University\\semester 2\\ProjDir\\TheProject-Phase2\\src\\main\\resources\\com\\example\\theprojectphase2\\blue-background.png"));
            } else if (r == 1) {
                us.setImage(new Image("D:\\University\\semester 2\\ProjDir\\TheProject-Phase2\\src\\main\\resources\\com\\example\\theprojectphase2\\green-background.png"));
            } else if (r == 2) {
                us.setImage(new Image("D:\\University\\semester 2\\ProjDir\\TheProject-Phase2\\src\\main\\resources\\com\\example\\theprojectphase2\\red-background.png"));
            } else {
                us.setImage(new Image("D:\\University\\semester 2\\ProjDir\\TheProject-Phase2\\src\\main\\resources\\com\\example\\theprojectphase2\\orange-background.png"));
            }
        }

        else
            us.setImage(picture.getImage());

        BufferedImage bImage = SwingFXUtils.fromFXImage(us.getImage(), null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        ImageIO.write(bImage, "png", s);
        byte[] res  = s.toByteArray();
        s.close();
        String encodedFile = Base64.getEncoder().encodeToString(res);
        us.setImageString(encodedFile);

        if(username_text.getText().isEmpty())
            msg.setText("Please Enter a UserName");

        else if(password_text.getText().isEmpty())
            msg.setText("Please Enter a Password");

        else if(password2_text.getText().isEmpty())
            msg.setText("Please Repeat your Password");

        else {
            boolean alreadyExists = false;
            for(User u : User.Users)
                if (u.getUserName().equals(s1)) {
                    alreadyExists = true;
                    break;
                }

            if(alreadyExists)
                msg.setText("This Username Is Already In Use.");

            else {
                if(!password_text.getText().equals(password2_text.getText()))
                    msg.setText("The Passwords Are Not The Same.");

                else{


                    if(!email_text.getText().isEmpty()) us.setEmail(email_text.getText());
                    else us.setEmail("");

                    if(!phone_text.getText().isEmpty()) us.setPhoneNumber(phone_text.getText());
                    else us.setPhoneNumber("");

                    if(!age_text.getText().isEmpty()) us.setAge(Integer.parseInt(age_text.getText()));
                    else us.setAge(18);

                    if(!bio_text.getText().isEmpty()) us.setBio(bio_text.getText());
                    else us.setBio("");

                    if (gender_choose.getValue().equals("male"))
                        us.setGender(true);

                    else if (gender_choose.getValue().equals("female"))
                        us.setGender(false);

                    if (type_choose.getValue().equals("Normal"))
                        us.setNormal(true);

                    else if (type_choose.getValue().equals("Business"))
                        us.setNormal(false);

                }

            }
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPage.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        MainPageController mainPageController = loader.getController();
        mainPageController.initialize(us);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();
    }


    public void ChoosePicture(MouseEvent event){
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg", "*.gif"));

        File file = fileChooser.showOpenDialog(window);
        if(file != null){
            Image openedImage = new Image(file.toURI().toString());
            picture.setImage(openedImage);
        }

        event.consume();
    }


}
