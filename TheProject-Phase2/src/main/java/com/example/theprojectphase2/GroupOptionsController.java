package com.example.theprojectphase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class GroupOptionsController {


    @FXML
    VBox vbox;

    @FXML
    TextField name_field;

    @FXML
    Button confirm_button;

    User us;
    Group group;
    Stage s;

    public void initialize(User u, Group g, Stage stage){
        for(User user : User.Users)
            if(user.getID() == u.getID())
                us = user;

        for(Group group1 : us.getGroups())
            if(group1.getID() == g.getID())
                group=group1;

        s=stage;

        name_field.setDisable(true);
        name_field.setManaged(false);
        name_field.setVisible(false);
        confirm_button.setDisable(true);
        confirm_button.setManaged(false);
        confirm_button.setVisible(false);

    }

    public void AddMember(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FollowersView_Group.fxml"));
        Parent parent = loader.load();

        Popup popup = new Popup();
        //Scene scene = new Scene(parent);


        FollowersViewGroup_Controller controller = loader.getController();
        controller.initialize(us,group,s);


        popup.getContent().add(parent);
        popup.setAutoHide(true);

        Popup popup1 = (Popup) ((Node)event.getSource()).getScene().getWindow();
        Stage MainStage = (Stage) popup1.getOwnerWindow();
        //MainStage.setResizable(false);
        //MainStage.setTitle("Group Profile");
        //MainStage.setX(x + event.getSceneX());
        //MainStage.setY(y + event.getSceneY());

        //MainStage.setScene(scene);
        popup.show(MainStage);
    }

    public void ChangeGroupName(ActionEvent event){
        name_field.setDisable(!name_field.isDisable());
        name_field.setManaged(!name_field.isManaged());
        name_field.setVisible(!name_field.isVisible());
        confirm_button.setDisable(!confirm_button.isDisable());
        confirm_button.setManaged(!confirm_button.isManaged());
        confirm_button.setVisible(!confirm_button.isVisible());


    }

    public void Confirm(ActionEvent event) throws IOException {
        name_field.setDisable(!name_field.isDisable());
        name_field.setManaged(!name_field.isManaged());
        name_field.setVisible(!name_field.isVisible());
        confirm_button.setDisable(!confirm_button.isDisable());
        confirm_button.setManaged(!confirm_button.isManaged());
        confirm_button.setVisible(!confirm_button.isVisible());

        group.GroupName = name_field.getText();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProfileGroup.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        ProfileGroupController controller = loader.getController();
        controller.initialize(group,us, s);

        Popup popup = (Popup) ((Node)event.getSource()).getScene().getWindow();
        Stage MainStage = (Stage) popup.getOwnerWindow();
        MainStage.setResizable(false);
        MainStage.setTitle("Group Profile");

        MainStage.setScene(scene);
        MainStage.show();

    }

    public void ChangePicture(ActionEvent event) throws IOException {
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File file = fileChooser.showOpenDialog(window);
        if(file != null){
            Image openedImage = new Image(file.toURI().toString());


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ProfileGroup.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);

            ProfileGroupController controller = loader.getController();
            controller.initialize(group,us, s);
            controller.loadImage(openedImage);

            Popup popup = (Popup) ((Node)event.getSource()).getScene().getWindow();
            Stage MainStage = (Stage) popup.getOwnerWindow();
            MainStage.setResizable(false);
            MainStage.setTitle("Group Profile");

            MainStage.setScene(scene);
            MainStage.show();
        }
        //update(event);
        event.consume();
    }

    public void update(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainPage.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        MainPageController mainPageController = loader.getController();
        mainPageController.initialize(us);


        Popup popup = (Popup) ((Node)event.getSource()).getScene().getWindow();
        Stage MainStage = (Stage) popup.getOwnerWindow();
        //MainStage.close();

        s.setScene(scene);
        s.show();

    }



}
