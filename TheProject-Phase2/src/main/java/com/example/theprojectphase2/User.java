package com.example.theprojectphase2;

import javafx.scene.image.Image;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@DBTable(tableName = "users")
public class User implements Comparable<Post>{

    public ArrayList<Double> getFollowersId() {
        return followersId;
    }

    public Boolean getNormal() {
        return isNormal;
    }

    public ArrayList<Double> getFollowedId() {
        return followedId;
    }

    public ArrayList<Double> getGroupsId() {
        return groupsId;
    }

    public ArrayList<Double> getSavedPostsId() {
        return savedPostsId;
    }

    public ArrayList<Double> getLikedPostsId() {
        return likedPostsId;
    }

    public ArrayList<Double> getPostsId() {
        return postsId;
    }

    public ArrayList<Double> getReceivedMessagesId() {
        return receivedMessagesId;
    }

    @DBPrimaryKey
    @DBAutoIncrement
    @DBField(name = "Id")
    Integer ID = 0;

    @DBField(name = "followers")
    public ArrayList<Double> followersId = new ArrayList<>();

    public ArrayList<User> followers = new ArrayList<>();

    public static ArrayList<User> Users = new ArrayList<>();



    @DBField(name = "age")
    Integer Age = 0;

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @DBField(name = "phoneNumber")
    String PhoneNumber;

    public String getImageString() {
        return imageString;
    }

    public Image getImage() {
        return image;
    }

    @DBField(name = "email")
    String Email;

    @DBField(name = "image")
    String imageString;

    Image image;

    @DBField(name = "Username")
    public String UserName;

    @DBField(name = "Password")
    protected String PassWord;

    @DBField(name = "gender")
    Boolean gender; //true for male and false for female


    @DBField(name = "IsNormal")
    Boolean isNormal;

    @DBField(name = "bio")
    String Bio;


    User(String userName,String passWord){this.UserName = userName; this.PassWord=passWord;}
    User(){}//default constructor



    @DBField(name = "followed")
    ArrayList<Double> followedId = new ArrayList<>();

    ArrayList<User>  followed = new ArrayList<>();

    @DBField(name = "myGroups")
    ArrayList<Double> groupsId = new ArrayList<>();

    ArrayList<Group> groups = new ArrayList<>();

    @DBField(name = "mySavedPosts")
    ArrayList<Double> savedPostsId = new ArrayList<>();

    ArrayList<Post> savedPosts = new ArrayList<>();

    @DBField(name = "myLikedPosts")
    ArrayList<Double> likedPostsId = new ArrayList<>();

    ArrayList<Post> likedPosts = new ArrayList<>();

    @DBField(name = "myPosts")
    ArrayList<Double> postsId = new ArrayList<>();

    ArrayList<Post> posts = new ArrayList<>();

    @DBField(name = "receivedMessages")
    ArrayList<Double>  receivedMessagesId = new ArrayList<>();

    ArrayList<Post> receivedMessages = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return Users;
    }

    public int getID() {
        return ID;
    }

    public int getAge() {
        return Age;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public boolean isGender() {
        return gender;
    }

    public String getBio() {
        return Bio;
    }

    public boolean getGender() {
        return gender;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<User> getFollowed() {
        return followed;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ArrayList<Post> getSavedPosts() {
        return savedPosts;
    }

    public ArrayList<Post> getLikedPosts() {
        return likedPosts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setReceivedMessages(ArrayList<Post> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }



    public ArrayList<Post> getReceivedMessages() {
        return receivedMessages;
    }


    void ChangeUserName(String NewUserName){}
    void ChangePassword(){}
    void SetBio(String NewBio){}

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    void addFollower(){}
    void addLikedPost(){}
    void addSavedPost(){}
    void Post(){}
    void CreateGroup(){}
    void LikePost(){}
    void DirectMessage(){}
    void DeletePost(){}

    /*
    public static void SignUp(Scanner myConsole , DataBase dataBase) throws SQLException {
        System.out.println("Enter Your User Name");
        String s1 = myConsole.nextLine();
        System.out.println("Enter Your Password");
        String s2 = myConsole.nextLine();
        ResultSet set = DBManager.getResultSet("SELECT * fROM USERS WHERE username = '"+s1+"';");

        if(set.isBeforeFirst())
            System.out.println("This username is already in use.");

        else{
            System.out.println("please determine your account type");
            System.out.println("1.Normal account");
            System.out.println("2.Business account");
            String type = myConsole.nextLine();

            if(type.contains("1")) {
                User user = new User(s1, s2);
                System.out.println("please enter your email (leave blank if you want to)");
                user.setEmail(myConsole.nextLine());
                System.out.println("please enter your phone number (leave blank if you want to)");
                user.setPhoneNumber(myConsole.nextLine());
                System.out.println("please enter your age");
                user.setAge(Integer.parseInt(myConsole.nextLine()));
                System.out.println("please enter your bio (leave blank if you want to)");
                user.setBio(myConsole.nextLine());
                System.out.println("please determine your gender (1 for male and 0 for female) (leave blank if you want to)");
                user.setGender(Boolean.parseBoolean(myConsole.nextLine()));
                System.out.println("please determine your privacy preference (1 for private account and 0 for public account)");
                user.setPrivate(Boolean.parseBoolean(myConsole.nextLine()));
                User.Users.add(user);
                DBManager.UpdateQuery("INSERT INTO users (username , password , Email , Phonenumber , Age , Bio , Gender , isPrivate , isNormal) VALUES ('" + user.getUserName() + "' , '" + user.getPassWord() + "' , '" + user.getEmail() + "' , '" + user.getPhoneNumber() + "' , '" +
                        user.getAge() + "' , '" + user.getBio() + "' , " + user.getGender() + " , " + user.getPrivacy() + " , " + "TRUE" + ")");

                System.out.println("Congratulations! Your account has been created successfully. \n You can now log in. \n \n");
            }

            else if (type.contains("2")){
                BusinessUser user = new BusinessUser(s1, s2);
                System.out.println("please enter your email (leave blank if you want to)");
                user.setEmail(myConsole.nextLine());
                System.out.println("please enter your phone number (leave blank if you want to)");
                user.setPhoneNumber(myConsole.nextLine());
                System.out.println("please enter your age (leave blank if you want to)");
                user.setAge(Integer.parseInt(myConsole.nextLine()));
                System.out.println("please enter your bio (leave blank if you want to)");
                user.setBio(myConsole.nextLine());
                System.out.println("please determine your gender (1 for male and 0 for female) (leave blank if you want to)");
                user.setGender(Boolean.parseBoolean(myConsole.nextLine()));
                System.out.println("please determine your privacy preference (1 for private account and 0 for public account)");
                user.setPrivate(Boolean.parseBoolean(myConsole.nextLine()));
                System.out.println("please enter your Bank id");
                user.setBankId(myConsole.nextLine());
                User.Users.add(user);
                DBManager.UpdateQuery("INSERT INTO users (username , password , Email ," +
                        "Phonenumber ," +
                        "Age ," +
                        "Bio ," +
                        "Gender ," +
                        "isPrivate ," +
                        "isNormal) VALUES ('" + user.getUserName() + "' , '" + user.getPassWord() + "' , '" + user.getEmail() + "' , '" + user.getPhoneNumber() + "' , '" +
                        user.getAge() + "' , '" + user.getGender() + "' , '" + user.getPrivacy() + "' , '" + "FALSE" + ");");
            }

        }
    }
     */

    /*public static void Login(String s1, String s2) throws SQLException {


        DataBase dataBase = new DataBase();
        ResultSet set = DBManager.getResultSet("SELECT * FROM users WHERE username = '"+s1 +"' AND password = '"+s2 + "';");
        //set.next();

        if(!set.isBeforeFirst())
            System.out.println("User not found.");

        else {
            set.next();
            if (set.getBoolean("isNormal")) {
                User user = initializeUser(set);

                while(true) {
                    System.out.println("Welcome " + user.UserName);
                    System.out.println("1.View Followers.");
                    System.out.println("2.View Followed.");
                    System.out.println("3.Create New Group.");
                    System.out.println("4.View Groups.");
                    String s3 = myConsole.nextLine();
                    if (s3.contains("1")) {
                        viewFollowers(myConsole, user);
                    }

                    else if (s3.contains("2")) {
                        viewFolloweds(myConsole, user);
                    }

                    else if (s3.contains("3")) {
                        Group.CreateGroup(myConsole , user);
                    }

                    else if (s3.contains("4")) {
                        Group.viewGroups(myConsole , user);
                    }
                }


            }
            else if (!set.getBoolean("isNormal")) {


            }
        }
    }*/
    void Logout(){}




    public static void viewFolloweds(Scanner myConsole , User user) throws SQLException {
        while (true) {
            if (user.getFollowed().isEmpty()) {
                System.out.println("You Have No followings. \n");

                System.out.println("1.back");

                if(myConsole.nextLine().contains("1"))
                    break;
            }

            else {
                int n = 1;
                for (User u : user.getFollowed()) {
                    System.out.println(u.getUserName());
                    System.out.println(n + ".unFollow\n");
                    n++;
                }

                System.out.println(n + ".back");

                int x = Integer.parseInt(myConsole.nextLine());
                if (x < n) {
                    System.out.println("Unfollowed Successfully");
                    DBManager.UpdateQuery("DELETE FROM follow where follower_id = " + user.getID() + " and followed_id = " + user.getFollowed().get(x - 1).getID());
                    user.getFollowed().remove(x - 1);
                }

                else if (x == n)
                    break;
            }
        }


    }

    public static void viewFollowers(Scanner myConsole , User user) throws SQLException {
        while (true) {
            if (user.getFollowers().isEmpty()) {
                System.out.println("You Have No followers. \n");

                System.out.println("1.back");

                if(myConsole.nextLine().contains("1"))
                    break;
            }

            else {
                int n = 1;
                for (User u : user.getFollowers()) {
                    System.out.println(u.getUserName());
                    System.out.println(n + ".Ban\n");
                    n++;
                }

                System.out.println(n + ".back");

                int x = Integer.parseInt(myConsole.nextLine());
                if (x < n) {
                    System.out.println("Banned Successfully");
                    DBManager.UpdateQuery("DELETE FROM follow where followed_id = " + user.getID() + " and follower_id = " + user.getFollowers().get(x - 1).getID());
                    user.getFollowers().remove(x - 1);
                }

                else if (x == n)
                    break;
            }
        }


    }

    public static void viewOptions(Scanner myConsole , User user, Group group) throws SQLException
    {
        while (true) {
            if (user.getFollowers().isEmpty() && user.getFollowed().isEmpty()) {
                System.out.println("You Have No Options. Be a little more active.\n");

                System.out.println("1.back");

                if(myConsole.nextLine().contains("1"))
                    break;
            }

            else {
                int n = 1;
                for (User u : user.getFollowed()) {
                    System.out.println(u.getUserName());
                    System.out.println(n + ".Choose\n");
                    n++;
                }


                System.out.println(n + ".back");

                int x = Integer.parseInt(myConsole.nextLine());
                if (x < n) {
                    System.out.println("Joined");
                    //DBManager.UpdateQuery("DELETE FROM follow where followed_id = " + user.getID() + " and follower_id = " + user.getFollowers().get(x - 1).getID());
                    group.getMembers().add(user.getFollowed().get(x - 1));
                }

                else if (x == n)
                    break;
            }
        }
    }

    public static void viewGroups(Scanner myConsole , User user) throws SQLException
    {

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAge(int age) {
        Age = age;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setBio(String bio) {
        Bio = bio;
    }


    @Override
    public int compareTo(Post o) {
        return 0;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setFollowersId(ArrayList<Double> followersId) {
        this.followersId = followersId;
    }

    public void setFollowers(ArrayList<User> followers) {
        this.followers = followers;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public void setNormal(Boolean normal) {
        isNormal = normal;
    }

    public void setFollowedId(ArrayList<Double> followedId) {
        this.followedId = followedId;
    }

    public void setFollowed(ArrayList<User> followed) {
        this.followed = followed;
    }

    public void setGroupsId(ArrayList<Double> groupsId) {
        this.groupsId = groupsId;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void setSavedPostsId(ArrayList<Double> savedPostsId) {
        this.savedPostsId = savedPostsId;
    }

    public void setSavedPosts(ArrayList<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public void setLikedPostsId(ArrayList<Double> likedPostsId) {
        this.likedPostsId = likedPostsId;
    }

    public void setLikedPosts(ArrayList<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public void setPostsId(ArrayList<Double> postsId) {
        this.postsId = postsId;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public void setReceivedMessagesId(ArrayList<Double> receivedMessagesId) {
        this.receivedMessagesId = receivedMessagesId;
    }
}
