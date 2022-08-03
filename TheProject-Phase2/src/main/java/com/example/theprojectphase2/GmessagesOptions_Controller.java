package com.example.theprojectphase2;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class GmessagesOptions_Controller {

    @FXML
    Button edit_button;

    @FXML
    Button delete_button;

    TextFlow textFlow;
    Post post;
    User us;
    TextArea type;
    AnchorPane title;
    ImageView view;

    public void initialize(TextFlow t, Post p, User u, TextArea ta, AnchorPane a, ImageView imageView){
        textFlow = t;

        for(Post pp : Post.Posts)
            if(pp.getId() == p.getId())
                post = pp;

        for(User uu : User.Users)
            if(uu.getID() == u.getID())
                us = uu;

        type = ta;

        title = a;

        view = imageView;


        if(!(post.getOwner().getID() == us.getID())){
            edit_button.setManaged(false);
            delete_button.setManaged(false);
            edit_button.setDisable(true);
            edit_button.setVisible(false);
            delete_button.setDisable(true);
            delete_button.setVisible(false);
        }


    }

    public void Copy(ActionEvent event1){
        VBox vBox1 = (VBox) textFlow.getChildren().get(0);
        Label label = (Label) vBox1.getChildren().get(1);
        String copyText = label.getText();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(copyText);
        clipboard.setContent(content);

        Popup popup = (Popup) ((Node)event1.getSource()).getScene().getWindow();
        popup.hide();
    }

    public void Delete(ActionEvent event1) throws IllegalAccessException {

        if(post.getOwner().equals(us)){
            if(us.getPosts().contains(post)){
                us.getPosts().remove(post);
                DBManagerTester.deleteRecordIfExist(post);
                VBox vBox = (VBox)(textFlow.getParent().getParent());
                vBox.getChildren().remove(textFlow.getParent());


            }

            else{
                if(Comment.Comments.contains((Comment) post))
                {
                    System.out.println("comment");
                    for(Post p : Post.Posts)
                        if(p.getComments().contains(post)){
                            p.getComments().remove(post);
                            DBManagerTester.deleteRecordIfExist(post);
                            VBox vBox = (VBox) (textFlow.getParent().getParent());
                            vBox.getChildren().remove(textFlow.getParent());
                        }
                }

                else {
                    System.out.println("not comment");
                    for (Group group : Group.Groups)
                        if (group.getPosts().contains(post)) {
                            group.getPosts().remove(post);
                            DBManagerTester.deleteRecordIfExist(post);
                            VBox vBox = (VBox) (textFlow.getParent().getParent());
                            vBox.getChildren().remove(textFlow.getParent());


                        }
                }
            }
        }


        Popup popup = (Popup) ((Node)event1.getSource()).getScene().getWindow();
        popup.hide();
    }

    public void Edit(ActionEvent event1){
        VBox vBox = (VBox)(textFlow.getParent().getParent());
        type.setDisable(false);
        title.setManaged(true);
        title.setVisible(true);
        title.setDisable(false);
        title.getChildren().get(0).setDisable(true);
        title.getChildren().get(0).setVisible(false);
        title.getChildren().get(1).setDisable(true);
        title.getChildren().get(1).setVisible(false);
        Button confirm = new Button("Confirm");
        Button decline = new Button("Decline");
        title.getChildren().add(decline);
        title.getChildren().add(confirm);
        AnchorPane.setTopAnchor(decline,10.0);
        AnchorPane.setLeftAnchor(decline,10.0);
        AnchorPane.setTopAnchor(confirm,10.0);
        AnchorPane.setRightAnchor(confirm,10.0);
        VBox vBox1 = (VBox) textFlow.getChildren().get(0);
        Label label;

        if( vBox1.getChildren().size() > 2 && vBox1.getChildren().get(2).getClass().getSimpleName().equals("Label")) {
            label = (Label) vBox1.getChildren().get(2);
        }

        else {
            label = (Label) vBox1.getChildren().get(1);
        }
        String content = label.getText();


        type.setText(content);
        //textArea.setEditable(true);
        confirm.setOnAction(event -> {
            label.setText(type.getText());
            for(Post post : Post.Posts)
                if(post.getId() == Integer.parseInt(textFlow.getId())){
                    post.setContext(type.getText());
                }

            type.clear();
            title.getChildren().get(0).setDisable(false);
            title.getChildren().get(0).setVisible(true);
            title.getChildren().get(1).setDisable(false);
            title.getChildren().get(1).setVisible(true);
            title.getChildren().remove(confirm);
            title.getChildren().remove(decline);
        });

        decline.setOnAction(event -> {
            label.setText(content);
            type.clear();
            title.getChildren().get(0).setDisable(false);
            title.getChildren().get(0).setVisible(true);
            title.getChildren().get(1).setDisable(false);
            title.getChildren().get(1).setVisible(true);
            title.getChildren().remove(confirm);
            title.getChildren().remove(decline);
        });

        Popup popup = (Popup) ((Node)event1.getSource()).getScene().getWindow();
        popup.hide();


    }

    public void Reply(ActionEvent event1){
        VBox chat_box = (VBox)(textFlow.getParent().getParent());
        type.setDisable(false);
        title.setManaged(true);
        title.setVisible(true);
        title.setDisable(false);
        title.getChildren().get(0).setDisable(true);
        title.getChildren().get(0).setVisible(false);
        title.getChildren().get(1).setDisable(true);
        title.getChildren().get(1).setVisible(false);
        Button confirm = new Button("Confirm");
        Button decline = new Button("Decline");
        title.getChildren().add(decline);
        title.getChildren().add(confirm);
        AnchorPane.setTopAnchor(decline,10.0);
        AnchorPane.setLeftAnchor(decline,10.0);
        AnchorPane.setTopAnchor(confirm,10.0);
        AnchorPane.setRightAnchor(confirm,10.0);
        VBox vBox1 = (VBox) textFlow.getChildren().get(0);

        //new  part


        //textArea.setEditable(true);
        confirm.setOnAction(event -> {
          Post post1 = new Post("",type.getText(),us);
          post1.setOwnerId(us.getID());
            if(view.getImage() == null)
                post1.setImageString("null");

            else{
                post1.setImage(view.getImage());

                BufferedImage bImage = SwingFXUtils.fromFXImage(view.getImage(), null);
                ByteArrayOutputStream s = new ByteArrayOutputStream();
                try {
                    ImageIO.write(bImage, "png", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] res  = s.toByteArray();
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String encodedFile = Base64.getEncoder().encodeToString(res);
                post1.setImageString(encodedFile);
                view.setImage(null);
            }

          DBManagerTester.insert(post1);
          post.getReplies().add(post1);
          Post.Posts.add(post1);
          type.clear();
          title.getChildren().get(0).setDisable(false);
          title.getChildren().get(0).setVisible(true);
          title.getChildren().get(1).setDisable(false);
          title.getChildren().get(1).setVisible(true);
          title.getChildren().remove(confirm);
          title.getChildren().remove(decline);
        });

        decline.setOnAction(event -> {

        });

        Popup popup = (Popup) ((Node)event1.getSource()).getScene().getWindow();
        popup.hide();
    }

    /*
    public void loadGroupMessage(Post post){
        VBox chat_box = (VBox)(textFlow.getParent().getParent());
        ScrollPane scroll_bar = (ScrollPane) (chat_box.getParent());
        TextFlow textFlow = new TextFlow();
        TextFlow container = new TextFlow();
        VBox vBox = new VBox();

        ImageView profileImage = new ImageView(post.getOwner().getImage());
        Circle circle = new Circle(20);
        circle.setTranslateX(30);
        circle.setTranslateY(30);
        profileImage.setClip(circle);
        profileImage.setId(String.valueOf(post.getOwner().getID()));
        profileImage.setFitHeight(50);
        profileImage.setFitWidth(50);
        profileImage.setOnMouseClicked(event -> {
            try {
                ViewUserProfile(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //this part is for post images
        ImageView view = null;
        if(post.image != null) {
            view = new ImageView(post.image);
            view.setFitHeight(view.getImage().getHeight()/4);
            view.setFitWidth(view.getImage().getWidth()/4);
            view.setTranslateX(10);
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Label text1 = new Label(post.getOwner().getUserName() + "        " + post.publishDate.format(formatter));
        text1.setStyle("-fx-text-fill: rgb(150,150,150);" + "-fx-font-size: 13");

        ArrayList<User> watchers = new ArrayList<>();
        for(String i : post.getSeens().keySet()){
            for(User uu : User.Users)
                if(uu.getID() == Integer.parseInt(i))
                    watchers.add(uu);
        }

        vBox.getChildren().add(text1);
        container.setId(String.valueOf(post.getId()));
        textFlow.setId(String.valueOf(post.getId()));

        for(Post p : Post.Posts)
            if(p.getReplies().contains(post)){

                Label label = new Label(p.getOwner().getUserName() +"\n"+ p.getContext());

                if(p.getContext().length() > 7)
                    label.setText(p.getOwner().getUserName() +"\n"+ p.getContext().substring(0,7) + "...");

                label.setStyle("-fx-font-size: 12;" + "-fx-text-fill: rgb(150,150,150);");
                label.setOpacity(0.7);


                label.setOnMouseClicked(event -> {
                    for(Node t : chat_box.getChildren())
                        if(t.getId().equals(String.valueOf(p.getId()))){
                            final Bounds boundsOnScene = t.localToScene( t.getBoundsInLocal() );
                            scroll_bar.setVvalue(boundsOnScene.getMinY()/100);
                        }
                });

                vBox.getChildren().add(label);

            }

        Label text2 = new Label(post.getContext());

        if(!watchers.contains(us))
            text2.setStyle("-fx-font-size: 12;" + "-fx-font-weight: bold;");

        else
            text2.setStyle("-fx-font-size: 12;");

        textFlow.setTranslateY(-profileImage.getFitHeight()/2);

        text1.setTranslateX(10);
        text2.setTranslateX(10);


        vBox.getChildren().add(text2);

        Text t1 = new Text(text1.getText());
        Text t2 = new Text(text2.getText());
        vBox.setMinWidth(Math.max(t1.getLayoutBounds().getWidth()+50,t2.getLayoutBounds().getWidth()+50) );
        //textFlow.setPrefWidth(Math.max(t1.getLayoutBounds().getWidth(),t2.getLayoutBounds().getWidth()) +30);

        if(post.image != null) {
            vBox.getChildren().add(view);
            vBox.setMinWidth(Math.max(t2.getLayoutBounds().getWidth()+50,view.getFitWidth()+50) );
        }



        //textFlow.setPrefWidth(text.getWidth()+100);
        textFlow.setMinWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxWidth(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMinHeight(TextFlow.USE_COMPUTED_SIZE);
        textFlow.setMaxHeight(TextFlow.USE_COMPUTED_SIZE);


        textFlow.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.SECONDARY) {
                try {
                    OpenMessageOption(event,"gmessages_options.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        container.setStyle("-fx-background-color: transparent");
        setStyle(textFlow,post);



        VBox.setMargin(textFlow, new Insets(20, 0, 0, 10));
        textFlow.getChildren().add(vBox);

        if(post.getOwner().getID() == us.getID()){
            container.getChildren().add(textFlow);
            container.getChildren().add(profileImage);
            container.setTextAlignment(TextAlignment.RIGHT);
            profileImage.setTranslateX(-10);
            textFlow.setTranslateX(-10);
        }

        else {
            container.getChildren().add(profileImage);
            container.getChildren().add(textFlow);
            container.setTextAlignment(TextAlignment.LEFT);
            textFlow.setTranslateX(20);
        }


        chat_box.getChildren().add(container);

        container.localToSceneTransformProperty().addListener( ( observable, oldValue, newValue ) ->
        {
            final Bounds boundsOnScene = container.localToScene( container.getBoundsInLocal() );

            if(boundsOnScene.getMinY() > 0  && !watchers.contains(us) && !container.getId().contains("c"))
                for(Post post1 : Post.Posts)
                    if(post1.getId() == Integer.parseInt(container.getId())){
                        text2.setStyle("-fx-font-size: 12;");
                        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        String date = LocalDateTime.now().format(formatter1);
                        post.seens.put(String.valueOf(us.getID()),date);
                    }

                    else if(boundsOnScene.getMinY() > 0  && !watchers.contains(us) && container.getId().contains("c"))
                        for(Comment comment : Comment.Comments)
                            if(comment.getId() == Integer.parseInt(container.getId().substring(0,container.getId().length()-1))){
                                text2.setStyle("-fx-font-size: 12;");
                                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                String date = LocalDateTime.now().format(formatter1);
                                comment.seens.put(String.valueOf(us.getID()),date);
                            }

        } );


    }
    */

}
