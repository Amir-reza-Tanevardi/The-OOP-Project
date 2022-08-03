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
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;

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
    private void LogIn(ActionEvent actionEvent) throws SQLException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String s1 = username_text.getText();
        String s2 = password_text.getText();

        initializeUser();

        if(User.Users.isEmpty())
            msg.setText("No User Found.");


       for(User user : User.Users) {
           if (user.getUserName().equals(s1) && user.getPassWord().equals(s2)) {
               LoadAll();
               LoginToMainPage(actionEvent, user);
           }

           else if(user.getUserName().equals(s1) && !user.getPassWord().equals(s2)){
               msg.setText("Wrong password");
           }

           else
               msg.setText("No User Found.");
       }
    }

    public static void initializeUser() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        org.joda.time.LocalDateTime l = org.joda.time.LocalDateTime.now();
        String s = l.getClass().getSimpleName();
        System.out.println(s);

        User.Users = DBManagerTester.doSelectQuery("SELECT * FROM users;", User.class);

        Group.Groups = DBManagerTester.doSelectQuery("SELECT * FROM projectgroups;", Group.class);


        Post.Posts = DBManagerTester.doSelectQuery("SELECT * FROM posts;",Post.class);


        Comment.Comments = DBManagerTester.doSelectQuery("SELECT * FROM comments;",Comment.class);


   }

    public static void LoadAll(){
        //load All Posts
        for(Post post : Post.Posts){

            for(User user : User.Users)
                if(user.getID() == post.getOwnerId())
                    post.setOwner(user);


            for(Double i : post.getLikersId())
                for(User user : User.Users)
                    if(user.getID() == i)
                        post.getLikers().add(user);

            for(Double i : post.getCommentsId())
                for(Comment comment : Comment.Comments)
                    if(comment.getId() == i)
                        post.getComments().add(comment);

            if(!post.getImageString().equals("null")){
              String base64Image = post.getImageString().split(",")[0];
              InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Image));
              post.setImage(new Image(stream));
            }
        }

        //load All Comments
        for(Comment comment : Comment.Comments){
            for(User user : User.Users)
                if(Objects.equals(user.getID(), comment.getOwnerId()))
                    comment.setOwner(user);


            for(Double i : comment.getLikersId())
                for(User user : User.Users)
                    if(user.getID() == i)
                        comment.getLikers().add(user);

        }


        //load All Groups
        for(Group group : Group.Groups){

            for(Double i : group.getMembersId())
                for(User user : User.Users)
                    if(user.getID() == i)
                        group.getMembers().add(user);

            for(Double i : group.getPostsId())
                for(Post post : Post.Posts)
                    if(post.getId() == i)
                        group.getPosts().add(post);

            if(group.getImageString() != null){
                String base64Image = group.getImageString().split(",")[0];
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Image));
                group.setImage(new Image(stream));
            }
        }

        //load All Users
        for(User user : User.Users)
        {
            for(Double i : user.getPostsId())
                for(Post post : Post.Posts)
                    if(post.getId() == i)
                        user.getPosts().add(post);

            for(Double i : user.getSavedPostsId())
                for(Post post : Post.Posts)
                    if(post.getId() == i)
                        user.getSavedPosts().add(post);

            for(Double i : user.getLikedPostsId())
                for(Post post : Post.Posts)
                    if(post.getId() == i)
                        user.getLikedPosts().add(post);

            for(Double i : user.getReceivedMessagesId())
                for(Post post : Post.Posts)
                    if(post.getId() == i)
                        user.getReceivedMessages().add(post);

            for(Double i : user.getGroupsId())
                for(Group group :Group.Groups)
                    if(group.getID() == i)
                        user.getGroups().add(group);

            for(Double i : user.followersId)
                for(User user1 : User.Users)
                    if(user1.getID() == i)
                        user.getFollowers().add(user1);


            for(Double i : user.followedId)
                for(User user1 : User.Users)
                    if(user1.getID() == i)
                        user.getFollowed().add(user1);

            if(user.getImageString() != null){
                String base64Image = user.getImageString().split(",")[0];
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(base64Image));
                user.setImage(new Image(stream));
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


        MainStage.setOnCloseRequest(event1 -> {

            //update All Posts
            for(Post post : Post.Posts){
                post.getLikersId().clear();
                for(User user1 : post.getLikers())
                        {
                            //post.getLikersId().retainAll(user.getID());
                            //if(!post.getLikersId().contains((double) user1.getID()))
                                post.getLikersId().add((double) user1.getID());
                        }

                    post.getCommentsId().clear();
                for(Comment comment : post.getComments())
                    {
                        post.getCommentsId().add((double)comment.getId());
                        //if(!post.getCommentsId().contains((double)comment.getId()))
                            //post.getCommentsId().add((double)comment.getId());
                    }

                DBManagerTester.update(post);
            }

            //update All Comments
            for(Comment comment : Comment.Comments){
                comment.setOwnerId(comment.getOwner().getID());


                for(User user1 : comment.getLikers())
                {
                    //post.getLikersId().retainAll(user.getID());
                    if(!comment.getLikersId().contains((double) user1.getID()))
                        comment.getLikersId().add((double) user1.getID());
                }


                DBManagerTester.update(comment);
            }


            //update All Groups
            for(Group group : Group.Groups){

                for(User user1 : group.getMembers())
                    {
                        //post.getLikersId().retainAll(user.getID());
                        if(!group.getMembersId().contains((double)user1.getID()))
                            group.getMembersId().add((double) user1.getID());
                    }

                for(Post post : group.getPosts())
                     {
                        //post.getLikersId().retainAll(user.getID());
                        if(!group.getPostsId().contains((double)post.getId()))
                            group.getPostsId().add((double)post.getId());
                    }

                DBManagerTester.update(group);
            }

            //update all users
            for(User user1 : User.Users)
            {
                for(Post post : user1.getPosts())
                    {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getPostsId().contains((double)post.getId()))
                            user1.getPostsId().add((double)post.getId());
                    }

                for(Post post : user1.getSavedPosts())
                    {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getSavedPostsId().contains((double)post.getId()))
                            user1.getSavedPostsId().add((double)post.getId());
                    }

                for(Post post : user1.getLikedPosts())
                    {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getLikedPostsId().contains((double)post.getId()))
                            user1.getLikedPostsId().add((double)post.getId());
                    }

                for(Post post : user1.getReceivedMessages())
                     {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getReceivedMessagesId().contains((double)post.getId()))
                            user1.getReceivedMessagesId().add((double)post.getId());
                    }

                for(Group group : user1.getGroups())
                    {
                        System.out.println("FUCKING HERE   "+ group.getGroupName());
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getGroupsId().contains((double)group.getID())){
                            user1.getGroupsId().add((double)group.getID());
                            System.out.println(group.getID());
                        }

                        for(Double ii : user1.getGroupsId())
                            System.out.println(ii);
                    }

                for(User u : user1.getFollowers())
                    {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getFollowersId().contains((double)u.getID()))
                            user1.getFollowersId().add((double)u.getID());
                    }


                for(User u : user1.getFollowed())
                     {
                        //post.getLikersId().retainAll(user.getID());
                        if(!user1.getFollowedId().contains((double)u.getID()))
                            user1.getFollowedId().add((double)u.getID());
                    }

                DBManagerTester.update(user1);
            }
        });

        MainStage.setScene(scene);
        MainStage.show();

    }

}