package com.example.theprojectphase2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;


public class NewTweet_Controller {

    @FXML
    TextArea title_text;

    @FXML
    TextArea newTweet_text;

    @FXML
    ImageView image;

    User user;



    public void initialize(User u){
        for(User us : User.Users)
            if(us.getID() == u.getID()){
                user = us;
                break;
            }

        System.out.println(u.UserName);

    }

    public void newPost(ActionEvent event){
        Post post;
        post =  new Post(title_text.getText(), newTweet_text.getText(), user);

        //post.setImage(image.getImage());

        DBManagerTester.insert(post);

        Post.Posts.add(post);
        user.getPosts().add(post);
        //Because the dataBase doesn't work for now I can't save the massage but whenever it
        // got fixed just uncomment the kine below
        //DBManager.save(post);


        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

    public void attachment(ActionEvent event){
        Window window = ((Node) (event.getSource())).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File file = fileChooser.showOpenDialog(window);
        if(file != null){
            Image openedImage = new Image(file.toURI().toString());
            image.setImage(openedImage);
        }
        event.consume();
    }

    public void close(ActionEvent event){

    }




}
