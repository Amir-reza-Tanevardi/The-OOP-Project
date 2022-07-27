package com.example.theprojectphase2;


import java.sql.Date;
import java.util.ArrayList;

//import oop.prj.model.DB.DBManager;
//import oop.prj.model.DB.Saveable;

public class Post implements Comparable<Post> ,Saveable{
      private ArrayList<User> likers = new ArrayList<>();
      private Date publishDate;
      private ArrayList<Comment> comments = new ArrayList<>();
      //public Image image;
      private String context = "";

      public void setPublishDate(Date publishDate) {
            this.publishDate = publishDate;
      }

      private String title = "";
      User owner;

      public ArrayList<User> getLikers() {
            return likers;
      }

      public static transient ArrayList<Post> Posts = new ArrayList<>();

      //public void setImage(Image image) {
            //this.image = image;
      //}

      //public Image getImage() {
        //    return image;
      //}

      public void setId(int id) {
            this.id = id;
      }

      private int id;

      private static final String TABLE_NAME = "posts";

      @Override
      public boolean equals(Object other) {
            if (other.equals(null)) {
                  return false;
            }
            if (!(other instanceof Post)) {
                  return false;
            }

            Post otherPost = (Post) other;
            if(getID() == otherPost.getID()){
                  return true;
            }
            return false;

      }

      protected Post(String title, String context, User owner) {
            this.title = title;
            this.context = context;
            this.owner = owner;
            if(Post.Posts.isEmpty())
                  this.id =0;
            else
                  this.id = Post.Posts.get(Post.Posts.size()-1).getID()+1;

      }

      public static Post makePost(String title, String context, User owner) {
            if (title.equals("") || context.equals("") || owner.equals(null)) {
                  return null;
            }
            Post instance = new Post(title, context, owner);
            return instance;
      }

      public int getID() {
            return id;
      }

      public void like(User liker) {
            // TODO check like conditions
            if (!liker.equals(null) && !likers.contains(liker)) {
                  likers.add(liker);
            }
      }

      public String getTitle() {
            return title;
      }

      public String getContext() {
            return context;
      }

      public User getOwner() {
            return owner;
      }

      public Date getPublishDate() {
            return publishDate;
      }

      public ArrayList<Comment> getComments() {return comments;}

      public void setTitle(String newTitle) {
            title = newTitle;
      }

      public void setContext(String newContext) {
            context = newContext;
      }

      public void comment(Comment comment) {

                  comments.add(comment);

      }

      @Override
      public int compareTo(Post o) {
            return publishDate.compareTo(o.getPublishDate());
      }


      @Override
      public String getTableName() {
            return TABLE_NAME;
      }
}
