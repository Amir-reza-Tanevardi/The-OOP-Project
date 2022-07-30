package com.example.theprojectphase2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NewGroupController {

    @FXML
    TextField group_name;

    @FXML
    Button add_button;

    @FXML
    ImageView picture;

    @FXML
    Label msg;

    @FXML
    VBox vbox;

    User us;

    Group group ;

    public void initialize(User user, Group g){
        for(User u : User.Users)
            if(u.getID() == user.getID())
                us=u;

        group = g;


        Circle circle = new Circle(50);
        circle.setTranslateX(55);
        circle.setTranslateY(55);

        if(!group.getMembers().contains(user))
          group.getMembers().add(user);

        loadList();


    }

    public void loadList(){
        for(User uu : group.getMembers())
            loadPV(uu);
    }

    public void AddMember(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FollowersView_newGroup.fxml"));
        Parent parent = loader.load();

        Popup popup = new Popup();
        //Scene scene = new Scene(parent);


        Stage s = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FollowersViewnewGroup_Controller controller = loader.getController();
        controller.initialize(us,group,s);


        popup.getContent().add(parent);
        popup.setAutoHide(true);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //Stage MainStage = (Stage) popup1.getOwnerWindow();
        //MainStage.setResizable(false);
        //MainStage.setTitle("Group Profile");
        //MainStage.setX(x + event.getSceneX());
        //MainStage.setY(y + event.getSceneY());

        //MainStage.setScene(scene);
        popup.show(MainStage);
    }

    public void loadPV(User user1){
        TextFlow textFlow = new TextFlow();

        TextFlow container = new TextFlow();

        ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
        Circle circle = new Circle(20);
        circle.setTranslateX(30);
        circle.setTranslateY(30);
        profileImage.setClip(circle);
        profileImage.setId(String.valueOf(user1.getID()));
        profileImage.setFitWidth(50);
        profileImage.setFitHeight(50);


        Text text;
        text  = new Text(user1.getUserName());


        textFlow.setTranslateY(-profileImage.getFitHeight()/2);
        container.getChildren().add(profileImage);
        textFlow.getChildren().add(text);

        container.setId(String.valueOf(user1.getID()));
        textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE );
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE );



        container.setStyle("-fx-background-color: rgb(255,255,255);");

        container.setPrefHeight(60);
        container.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        container.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        container.setMinHeight(TextFlow.USE_PREF_SIZE );
        container.setMaxHeight(TextFlow.USE_COMPUTED_SIZE );

        textFlow.setTranslateX(10);
        container.getChildren().add(textFlow);
        vbox.getChildren().add(container);
    }

    public void Confirm(ActionEvent event) throws IOException {
        group.GroupName = group_name.getText();
        //group.image =
        if(!group.getGroupName().isEmpty() && group.getMembers().size()>0){
            Group.Groups.add(group);


            for(User u : group.getMembers()) {
                u.getGroups().add(group);
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

        else if(group.getGroupName().isEmpty())
            msg.setText("Type a Name");

        else if(group.getMembers().isEmpty())
            msg.setText("Choose at Least One Member");
    }

}
