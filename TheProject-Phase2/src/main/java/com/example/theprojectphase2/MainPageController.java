package com.example.theprojectphase2;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainPageController {

    @FXML
    Label chat_msg;

    @FXML
    VBox chat_box;

    @FXML
    VBox group_vbox;

    @FXML
    ButtonBar button_bar;

    @FXML
    Label welcome_title;

    @FXML
    TabPane the_list;

    @FXML
    TextArea main_type;

    @FXML
    Button send_button;

    @FXML
    ImageView title_image;

    @FXML
    Label title_label;

    @FXML
    AnchorPane title;

    @FXML
    VBox setting;

    @FXML
    ImageView main_image;

    @FXML
    Label main_label;

    @FXML
    Button myposts_button;

    @FXML
    Button followingButton;

    @FXML
    Button followerButton;

    @FXML
    Button search_button;

    @FXML
    TextField search_field;

    @FXML
    Button newGroup_button;

    boolean isInGroup;

    SimpleBooleanProperty isDark;

    User user; //The user that's logged in his/her account
    Post pp;




    public void initialize(User u){
        //Initializes the page
        for(User us : User.Users)
           if(us.getID() == u.getID()){
               user = us;
               break;
           }

        //title.setManaged(false);
        title.setVisible(false);




        title_image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewGroupProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Will be deleted later
        pp = new Post("","HELLO",User.Users.get(0));
        Post.Posts.add(pp);
        user.getGroups().get(2).getPosts().add(pp);
        //will be deleted later

        main_label.setText(user.getUserName()+"\n"+user.getFollowers().size()+" Followers"+"\n"+user.getFollowed().size()+" Followings");
        //main_image.setImage(user.image);
        main_image.setId(String.valueOf(user.getID()));
        main_image.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewUserProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Circle circle = new Circle(40);
        circle.setTranslateX(50);
        circle.setTranslateY(45);
        main_image.setClip(circle);
        main_image.setImage(new Image("https://images.unsplash.com/flagged/photo-1593005510329-8a4035a7238f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"));

        loadData(user);

        myposts_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewMyPosts(user);
            }
        });


        setting.setManaged(false);
        main_image.setManaged(false);
        main_label.setManaged(false);
        myposts_button.setManaged(false);
        followerButton.setManaged(false);
        followingButton.setManaged(false);
        newGroup_button.setManaged(false);

        setting.setDisable(true);
        main_image.setDisable(true);
        main_label.setDisable(true);
        myposts_button.setDisable(true);
        followerButton.setDisable(true);
        followingButton.setDisable(true);
        newGroup_button.setDisable(true);

        myposts_button.setVisible(false);
        followerButton.setVisible(false);
        followingButton.setVisible(false);
        main_label.setVisible(false);
        main_image.setVisible(false);
        newGroup_button.setVisible(false);

        search_button.setOnAction(event -> loadData(user));

        ToggleSwitch toggleSwitch = new ToggleSwitch();

        setting.getChildren().add(toggleSwitch);
        isDark = toggleSwitch.switchOnProperty();
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                toggleSwitch.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                toggleSwitch.getScene().getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });


    }



    private void loadData (User u) {
        //loads date when loging in

        welcome_title.setText("Welcome " + u.UserName);
        group_vbox.getChildren().clear();

        if (search_field.getText().isEmpty()){

            for (Group g : u.getGroups()) {
                TextFlow textFlow = new TextFlow();

                TextFlow container = new TextFlow();

                ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
                Circle circle = new Circle(20);
                circle.setTranslateX(30);
                circle.setTranslateY(30);
                profileImage.setClip(circle);
                profileImage.setId(String.valueOf(g.getID()));
                profileImage.setFitWidth(50);
                profileImage.setFitHeight(50);
                profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        try {
                            ViewGroupProfile(event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Label text;
                if (g.getPosts().isEmpty())
                    text = new Label(g.getGroupName() + "\n" + "No Chat Here");

                else
                    text = new Label(g.getGroupName() + "\n" + g.getPosts().get(g.getPosts().size() - 1).getContext());

                textFlow.setTranslateY(-profileImage.getFitHeight() / 2);
                container.getChildren().add(profileImage);
                textFlow.getChildren().add(text);

                container.setId(String.valueOf(g.getID()));
                textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
                textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
                textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
                textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);


                container.setOnMouseClicked(this::ChooseGroupChat);


                //container.setStyle("-fx-background-color: rgb(255,255,255);");

                container.setPrefHeight(60);
                container.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
                container.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
                container.setMinHeight(TextFlow.USE_PREF_SIZE);
                container.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);

                textFlow.setTranslateX(10);
                container.getChildren().add(textFlow);
                group_vbox.getChildren().add(container);


            }

            ArrayList<User> messengers = new ArrayList<>();

            for (Post post : user.getReceivedMessages())
            messengers.add(post.getOwner());

           for (User uu : User.Users) {
              for (Post p : uu.getReceivedMessages())
                   if (p.getOwner().getID() == user.getID())
                    messengers.add(uu);
           }

           for (User user1 : messengers) {
            loadPV(user1);
           }

      }

        else {

            for (Group g : u.getGroups()) {
                if(g.getGroupName().contains(search_field.getText())) {
                    TextFlow textFlow = new TextFlow();

                    TextFlow container = new TextFlow();

                    ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
                    Circle circle = new Circle(20);
                    circle.setTranslateX(30);
                    circle.setTranslateY(30);
                    profileImage.setClip(circle);
                    profileImage.setId(String.valueOf(g.getID()));
                    profileImage.setFitWidth(50);
                    profileImage.setFitHeight(50);
                    profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                ViewGroupProfile(event);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    Text text;
                    if (g.getPosts().isEmpty())
                        text = new Text(g.getGroupName() + "\n" + "No Chat Here");

                    else
                        text = new Text(g.getGroupName() + "\n" + g.getPosts().get(g.getPosts().size() - 1).getContext());

                    textFlow.setTranslateY(-profileImage.getFitHeight() / 2);
                    container.getChildren().add(profileImage);
                    textFlow.getChildren().add(text);

                    container.setId(String.valueOf(g.getID()));
                    textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
                    textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
                    textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
                    textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);


                    container.setOnMouseClicked(this::ChooseGroupChat);


                    container.setStyle("-fx-background-color: rgb(255,255,255);");

                    container.setPrefHeight(60);
                    container.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
                    container.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
                    container.setMinHeight(TextFlow.USE_PREF_SIZE);
                    container.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);

                    textFlow.setTranslateX(10);
                    container.getChildren().add(textFlow);
                    group_vbox.getChildren().add(container);

                }
            }

            for (User user1 : User.Users) {
                if(user1.getUserName().contains(search_field.getText()))
                  loadPV(user1);
            }

        }

    }



    @FXML
    private void setChat(MouseEvent event){
        //This method is called whenever we want to change the tabs
        //of the tab pane
        //Most likely only will be used with the "New" tab because other tabs don't need immediate filling


        if(the_list.getSelectionModel().getSelectedItem().getText().equals("New")){
            chat_box.getChildren().retainAll(title);
            title.setVisible(false);
            title.setManaged(false);
            //This part is called when we choose the new tab in the tab pane
            //It gets the new posts of the user's followings and sorts them based on their time
            //Then shows the newest ones first
            ArrayList<Post> newPosts = new ArrayList<>();

            for(User u : user.getFollowed())
                newPosts.addAll(u.getPosts());

            newPosts.add(pp);
            Collections.sort(newPosts);
            //not sure if it sorted it ascending or descending. if didn't get
            //wanted results, just uncomment the line below
            //Collections.reverse(newPosts);
            main_type.clear();
            main_type.setDisable(true);
            send_button.setDisable(true);

            for(Post post : newPosts){
                loadPost(post);
            }

        }
    }


    public void ViewMyPosts(User u){
        chat_box.getChildren().retainAll(title);
        title.setVisible(false);
        title.setManaged(false);


        ArrayList<Post> newPosts = new ArrayList<>(u.getPosts());

        //Collections.sort(newPosts);
        //Because The Date is not enabled

        //not sure if it sorted it ascending or descending. if didn't get
        //wanted results, just uncomment the line below
        //Collections.reverse(newPosts);
        main_type.clear();
        main_type.setDisable(true);
        send_button.setDisable(true);

        for(Post post : newPosts){
           loadPost(post);
        }
    }


    public void ViewComments(MouseEvent event){
        //This method is called whenever we want to view the comments of a post
        //clears the chat box and fills it with comments
        chat_box.getChildren().clear();
        Hyperlink hyperlink = (Hyperlink) event.getSource();

        main_type.setDisable(false);
        main_type.clear();
        main_type.setPromptText("Add Comment");
        send_button.setDisable(false);
        send_button.setId(String.valueOf(hyperlink.getId()));
        for(Post post : Post.Posts)
            if(post.getID() == Integer.parseInt(hyperlink.getId())){
                for(Comment comment : post.getComments()){
                    loadComment(comment);
                }
                break;
            }
    }


    public void LikePost(MouseEvent event) throws IOException {
        //Called when we want to like a post
        //works with double click

        TextFlow textFlow = (TextFlow) event.getSource();

        if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY)
         for(Post post : Post.Posts)
            if(post.getID() == Integer.parseInt(textFlow.getId()))
              {
                  System.out.println("ddooo");
                  post.like(user);
              textFlow.getChildren().set(0, new Text(post.getOwner().getUserName()+"\n"+post.getTitle() +"    "+"\n" +
                      post.getContext() + "\n" + post.getLikers().size() + "  likes\n"));}


        else if(event.getButton() == MouseButton.SECONDARY)
            OpenMessageOption(event);


    }


    public void MOPtions(MouseEvent event, TextFlow textFlow, Post post) throws IOException {

        if(event.getButton() == MouseButton.SECONDARY)
           OpenMessageOption(event);
    }


    public void LikeComment(MouseEvent event) throws IOException {
        //Called when we want to like a comment
        //works with double click

        TextFlow textFlow = (TextFlow) event.getSource();

        if(event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY)
            for(Comment comment : Comment.Comments)
                if(comment.getID() == Integer.parseInt(textFlow.getId()))
                   {comment.like(user);
                   textFlow.getChildren().set(0, new Text(comment.getOwner().getUserName() +"       "+"\n" +
                           comment.getContext() +"\n" + comment.getLikers().size() + "  likes"));
                   }

        else if(event.getButton() == MouseButton.SECONDARY)
                    OpenMessageOption(event);
    }


    public void OpenOptions(){
        setting.setManaged(!setting.isManaged());
        main_image.setManaged(!main_image.isManaged());
        main_label.setManaged(!main_label.isManaged());
        myposts_button.setManaged(!myposts_button.isManaged());
        followerButton.setManaged(!followerButton.isManaged());
        followingButton.setManaged(!followingButton.isManaged());
        newGroup_button.setManaged(!newGroup_button.isManaged());

        setting.setDisable(!setting.isDisable());
        main_image.setDisable(!main_image.isDisable());
        main_label.setDisable(!main_label.isDisable());
        myposts_button.setDisable(!myposts_button.isDisable());
        followerButton.setDisable(!followerButton.isDisable());
        followingButton.setDisable(!followingButton.isDisable());
        newGroup_button.setDisable(!newGroup_button.isDisable());

        myposts_button.setVisible(!myposts_button.isVisible());
        followerButton.setVisible(!followerButton.isVisible());
        followingButton.setVisible(!followingButton.isVisible());
        main_label.setVisible(!main_label.isVisible());
        main_image.setVisible(!main_image.isVisible());
        newGroup_button.setVisible(!newGroup_button.isVisible());
        //setting.setVisible(true);

    }


    private void ChoosePVChat(MouseEvent event, ArrayList<Post> messages) {
        TextFlow chosen = (TextFlow) event.getSource();
        title.setVisible(true);
        title.setManaged(true);
        chat_box.setVisible(true);
        chat_box.setDisable(false);
        send_button.setDisable(false);
        isInGroup = false;


        chat_box.getChildren().retainAll(title);

        for (User u : User.Users)
            if (u.getID() == Integer.parseInt(chosen.getId())){

                send_button.setId(String.valueOf(u.getID()));
                main_type.setDisable(false);
                main_type.setPromptText("Type New Massage PV");
                title_label.setText(u.getUserName());
                title_image.setId(chosen.getId());
                //title_image.setImage(g.Image); Uncomment The line when implemented The image attribute

                for (Post post : messages) {
                   loadGroupMessage(post);
                }
      }
    }


    public void ChooseGroupChat(MouseEvent event){
            //This method is called whenever we want to choose a group
            //it basically loads the chat box with group messages and enables us
            //to send messages in the said group

            TextFlow chosen = (TextFlow) event.getSource();
            title.setVisible(true);
            title.setManaged(true);
            chat_box.setVisible(true);
            chat_box.setDisable(false);
            send_button.setDisable(false);

            isInGroup = true;


            chat_box.getChildren().retainAll(title);

            for (Group g : user.getGroups())
                if (g.getID() == Integer.parseInt(chosen.getId())) {
                    main_type.setDisable(false);
                    main_type.setPromptText("Type New Massage");
                    title_label.setText(g.getGroupName()+"\n"+g.getMembers().size()+" members");
                    title_image.setId(chosen.getId());
                    //title_image.setImage(g.Image); Uncomment The line when implemented The image attribute

                    send_button.setId(String.valueOf(g.getID()));


                    if (!g.getPosts().isEmpty())
                        for (Post post : g.getPosts()) {
                            loadGroupMessage(post);
                            main_type.clear();
                        }

                    else
                        System.out.println("it is empty");

                 break;

                }
    }


    public void ViewGroupProfile(MouseEvent event) throws IOException {
        ImageView view = (ImageView) event.getSource();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProfileGroup.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });

        ProfileGroupController controller = loader.getController();
        for(Group g : user.getGroups())
            if(g.getID() == Integer.parseInt(view.getId()))
               controller.initialize(g,user, (Stage) view.getScene().getWindow());

        Stage MainStage = new Stage();
        MainStage.setResizable(false);
        MainStage.setTitle("Group Profile");

        MainStage.setScene(scene);
        MainStage.show();
    }


    public void Send(){
        //This method will be called whenever we want to send a message to PV,
        //Group, or we want to add a Comment to a post


        //This first part is for group messages
        if(the_list.getSelectionModel().getSelectedItem().getText().equals("Groups")) {


          if(isInGroup) {
              for (Group g : user.getGroups())
                  if (g.getID() == Integer.parseInt(send_button.getId()) && !main_type.getText().isEmpty()) {

                      Post post = new Post("", main_type.getText(), user);


                      loadGroupMessage(post);
                      g.getPosts().add(post);
                      Post.Posts.add(post);

                      group_vbox.getChildren().clear();
                      loadData(user);

                      //Because the dataBase doesn't work for now I can't save the massage but whenever it
                      // got fixed just uncomment the kine below
                      //DBManager.save(post);
                      break;
                  }
          }


          else {
                    for (User u : User.Users)
                        if (u.getID() == Integer.parseInt(send_button.getId()) && !main_type.getText().isEmpty()) {

                            Post post = new Post("", main_type.getText(), user);

                            loadGroupMessage(post);
                            u.getReceivedMessages().add(post);
                            Post.Posts.add(post);

                            group_vbox.getChildren().clear();
                            loadData(user);

                            //Because the dataBase doesn't work for now I can't save the massage but whenever it
                            // got fixed just uncomment the kine below
                            //DBManager.save(post);
                            break;
                        }
                }


        }

       //This second part is about adding comments
        else if(the_list.getSelectionModel().getSelectedItem().getText().equals("New")){
           for (Post post : Post.Posts){
               if(post.getID() == Integer.parseInt(send_button.getId())){
                   if(!main_type.getText().isEmpty()){
                       Comment comment = new Comment(main_type.getText(),user);
                       post.comment(comment);
                       Comment.Comments.add(comment);
                       main_type.clear();

                       loadComment(comment);
                   }

                   else{
                       //Do Things if there is nothing in the main_type
                       //It will be about editing a massage label
                   }
               }
           }


        }



    }


    public void Post(ActionEvent actionEvent) throws IOException {
        //This opens the new tweet page to maje a new tweet
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewTweet.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });

        NewTweet_Controller tweetController = loader.getController();
        tweetController.initialize(user);

        Stage MainStage = new Stage();
        MainStage.setTitle("New Tweet");

        MainStage.setScene(scene);
        MainStage.show();


    }


    public void ViewUserProfile(MouseEvent event) throws IOException {
        ImageView view = (ImageView) event.getSource();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserProfile.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });

        UserProfileController controller = loader.getController();
        for(User u : User.Users)
            if(u.getID() == Integer.parseInt(view.getId()))
                controller.initialize(u,user, (Stage) view.getScene().getWindow());

        Stage MainStage = new Stage();
        MainStage.setResizable(false);
        MainStage.setTitle("User Profile");

        MainStage.setScene(scene);
        MainStage.show();
    }


    public void OpenMessageOption(MouseEvent event) throws IOException {
        TextFlow textFlow = (TextFlow) event.getSource();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MessageOptionsMenu.fxml"));
        Parent parent = loader.load();

        Popup popup = new Popup();
        if(isDark.getValue())
            popup.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                popup.getScene().getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                popup.getScene().getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });
        //Scene scene = new Scene(parent);

        MessageOptions_Controller controller = loader.getController();
        for(Post p : Post.Posts)
            if(p.getID() == Integer.parseInt(textFlow.getId())){
                controller.initialize(textFlow,p,user,main_type);

                break;
            }

        double x = textFlow.getScene().getWindow().getX();
        double y = textFlow.getScene().getWindow().getY();

        popup.getContent().add(parent);

        popup.setAutoHide(true);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //MainStage.setResizable(false);
        //MainStage.setTitle("Group Profile");
        //MainStage.setX(x + event.getSceneX());
        //MainStage.setY(y + event.getSceneY());

        //MainStage.setScene(scene);
        popup.show(MainStage);
    }


    public void loadGroupMessage(Post post){
        ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
        Circle circle = new Circle(20);
        circle.setTranslateX(30);
        circle.setTranslateY(30);
        profileImage.setClip(circle);
        profileImage.setId(String.valueOf(post.getOwner().getID()));

        profileImage.setFitHeight(50);
        profileImage.setFitWidth(60);
        profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewUserProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TextFlow container = new TextFlow();

        TextFlow textFlow = new TextFlow();
        String filling = post.getOwner().getUserName() + "          "  + "\n" +
                post.getContext();

        Label text = new Label(filling);

        text.setTranslateX(10);

        container.getChildren().add(profileImage);
        textFlow.setTranslateY(-profileImage.getFitHeight()/2);


        textFlow.getChildren().add(text);
        textFlow.setPrefWidth(100);
        textFlow.setMinWidth(TextFlow.USE_PREF_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setId(String.valueOf(post.getID()));


        textFlow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    MOPtions(event, textFlow, post);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        container.setStyle("-fx-background-color: transparent");
        setStyle(textFlow,post);

        main_type.clear();

        container.getChildren().add(textFlow);


        chat_box.getChildren().add(container);
        VBox.setMargin(textFlow, new Insets(20, 0, 0, 10));

    }


    public void loadPost(Post post){
        TextFlow textFlow = new TextFlow();
        TextFlow container = new TextFlow();
        Hyperlink hyperlink = new Hyperlink("Comments");

        ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
        Circle circle = new Circle(20);
        circle.setTranslateX(30);
        circle.setTranslateY(30);
        profileImage.setClip(circle);
        profileImage.setId(String.valueOf(post.getOwner().getID()));
        profileImage.setFitHeight(50);
        profileImage.setFitWidth(50);
        profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewUserProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Label text = new Label(post.getOwner().getUserName()+"\n"+post.getTitle() +"       "+"\n" +
                post.getContext() + "\n" + post.getLikers().size() + "   likes\n");


        textFlow.setTranslateY(-profileImage.getFitHeight()/2);
        textFlow.setTranslateX(10);
        text.setTranslateX(10);


        container.getChildren().add(profileImage);
        textFlow.getChildren().add(text);
        textFlow.setId(String.valueOf(post.getID()));
        //textFlow.setPrefWidth(text.getWidth()+100);
        textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.getChildren().add(hyperlink);

        //hyperlink.setTranslateY(textFlow.getHeight());
        hyperlink.setId(String.valueOf(post.getID()));
        hyperlink.setOnMouseClicked(this::ViewComments);
        textFlow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    LikePost(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textFlow.setStyle("-fx-background-radius: 15;");
        container.setStyle("-fx-background-color: transparent");

        if(isDark.getValue())
            textFlow.getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

        else
            textFlow.setStyle("-fx-background-color: rgb(200,200,200)");

        VBox.setMargin(textFlow, new Insets(20, 0, 0, 10));
        container.getChildren().add(textFlow);
        chat_box.getChildren().add(container);
    }


    public void loadComment(Comment comment){
        ImageView profileImage = new ImageView("https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/Sunset_2007-1.jpg/640px-Sunset_2007-1.jpg");
        Circle circle = new Circle(20);
        circle.setTranslateX(30);
        circle.setTranslateY(30);
        profileImage.setClip(circle);
        profileImage.setFitWidth(50);
        profileImage.setFitHeight(50);
        profileImage.setId(String.valueOf(user.getID()));
        profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewUserProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        TextFlow textFlow = new TextFlow();
        TextFlow container = new TextFlow();
        container.getChildren().add(profileImage);
        Label text = new Label(comment.getOwner().getUserName() +"       "+"\n" +
                comment.getContext()+"\n"+comment.getLikers().size()+"   likes");
        textFlow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    LikeComment(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        textFlow.getChildren().add(text);
        textFlow.setStyle("-fx-background-radius: 15");
        container.setStyle("-fx-background-color: transparent;");
        setStyle(textFlow,comment);

        textFlow.setId(String.valueOf(comment.getID()));
        textFlow.setPrefWidth(100);
        textFlow.setMinWidth(TextFlow.USE_PREF_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_PREF_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);

        VBox.setMargin(textFlow, new Insets(20, 0, 0, 10));
        container.getChildren().add(textFlow);
        textFlow.setTranslateY(-profileImage.getFitHeight()/2);
        textFlow.setTranslateX(10);
        text.setTranslateX(10);
        chat_box.getChildren().add(container);
    }


    public void loadPV(User user1){

        ArrayList<Post> AllPosts = new ArrayList<>();
        for (Post post1 : user.getReceivedMessages())
            if(post1.getOwner().getID() == user1.getID())
                AllPosts.add(post1);

        for (Post post1 : user1.getReceivedMessages())
            if(post1.getOwner().getID() == user.getID())
                AllPosts.add(post1);

        Collections.sort(AllPosts);
        //Collections.reverse(AllPosts);


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
        profileImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ViewUserProfile(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Text text;
        if(AllPosts.isEmpty())
            text  = new Text(user1.getUserName() + "\n" + "No Chat Here");

        else
            text  = new Text(user1.getUserName() + "\n" + AllPosts.get(AllPosts.size()-1).getContext());

        textFlow.setTranslateY(-profileImage.getFitHeight()/2);
        container.getChildren().add(profileImage);
        textFlow.getChildren().add(text);

        container.setId(String.valueOf(user1.getID()));
        textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE );
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE );


        container.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ChoosePVChat(event,AllPosts);
            }
        });


        container.setPrefHeight(60);
        container.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        container.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        container.setMinHeight(TextFlow.USE_PREF_SIZE );
        container.setMaxHeight(TextFlow.USE_COMPUTED_SIZE );

        textFlow.setTranslateX(10);
        container.getChildren().add(textFlow);
        group_vbox.getChildren().add(container);
    }

    public void ShowFollowers(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FollowersView.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });


        FollowersController controller = loader.getController();
        controller.initialize(user);

        Stage MainStage = new Stage();
        MainStage.setResizable(false);
        MainStage.setTitle("Followers");

        MainStage.setScene(scene);
        MainStage.show();
    }

    public void ShowFollowing(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FollowingView.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });

        FollowingController controller = loader.getController();
        controller.initialize(user);

        Stage MainStage = new Stage();
        MainStage.setResizable(false);
        MainStage.setTitle("Followers");

        MainStage.setScene(scene);
        MainStage.show();
    }

    public void CreateGroup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewGroup.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        if(isDark.getValue())
            scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue)
                scene.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());

            else
                scene.getRoot().getStylesheets().remove(Objects.requireNonNull(getClass().getResource("DarkMode.css")).toString());
        });

        NewGroupController mainPageController = loader.getController();
        Group group = new Group("");
        mainPageController.initialize(user, group);

        Stage MainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        MainStage.setScene(scene);
        MainStage.show();
    }


    public void setStyle(TextFlow textFlow, Post post){

        if(isDark.getValue() && post.getOwner().getID() == user.getID())
            textFlow.setStyle("-fx-background-color: rgb(11,11,69);" + "-fx-background-radius: 15;");

        else if(post.getOwner().getID() == user.getID())
            textFlow.setStyle("-fx-background-color: rgb(131,238,255);" + "-fx-background-radius: 15;");

        else if(!isDark.getValue() && post.getOwner().getID() != user.getID())
            textFlow.setStyle("-fx-background-color: rgb(200,200,200);" + "-fx-background-radius: 15;");

        else if(isDark.getValue() && post.getOwner().getID() != user.getID())
            textFlow.setStyle("-fx-background-color: #373e43;" + "-fx-background-radius: 15;");

        isDark.addListener((observable, oldValue, newValue) -> {
            if(newValue && post.getOwner().getID() == user.getID())
                textFlow.setStyle("-fx-background-color: rgb(11,11,69);" + "-fx-background-radius: 15;");

            else if(post.getOwner().getID() == user.getID())
                textFlow.setStyle("-fx-background-color: rgb(131,238,255);" + "-fx-background-radius: 15;");

            else if(!newValue && post.getOwner().getID() != user.getID())
                textFlow.setStyle("-fx-background-color: rgb(200,200,200);" + "-fx-background-radius: 15;");

            else if(newValue && post.getOwner().getID() != user.getID())
                textFlow.setStyle("-fx-background-color: #373e43;" + "-fx-background-radius: 15;");
        });
    }
}
