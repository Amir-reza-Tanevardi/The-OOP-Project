package com.example.theprojectphase2;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class User implements Comparable<Post> ,Saveable{

    public static ArrayList<User> Users = new ArrayList<>();
    int ID;
    int Age;

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

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getBio() {
        return Bio;
    }

    public boolean getGender() {
        return gender;
    }

    public boolean getPrivacy() {
        return isPrivate;
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

    String PhoneNumber;
    String Email;
    public String UserName;
    protected String PassWord;
    boolean gender; //true for male and false for female
    boolean isPrivate; //true for private and false for not private
    boolean isNormal;
    String Bio;
    User(String userName,String passWord){this.UserName = userName; this.PassWord=passWord;}
    User(){}//default constructor

    ArrayList<User> followers = new ArrayList<>();
    ArrayList<User>  followed = new ArrayList<>();
    transient ArrayList<Group> groups = new ArrayList<>();
    ArrayList<Post> savedPosts = new ArrayList<>();
    ArrayList<Post> likedPosts = new ArrayList<>();

    public void setReceivedMessages(ArrayList<Post> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    ArrayList<Post> posts = new ArrayList<>();

    public ArrayList<Post> getReceivedMessages() {
        return receivedMessages;
    }

    ArrayList<Post> receivedMessages = new ArrayList<>();
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

    public static User initializeUser(ResultSet set) {
        User user = null;
        try {

            user = new User(set.getString("username"), set.getString("password"));
            user.setID(set.getInt("id"));
            user.setAge(set.getInt("age"));
            user.setBio(set.getString("Bio"));
            user.setEmail(set.getString("Email"));
            user.setPhoneNumber(set.getString("Phonenumber"));
            user.setGender(set.getBoolean("Gender"));
            user.setPrivate(set.getBoolean("isPrivate"));
            user.setNormal(set.getBoolean("isNormal"));




            //Initialize all accounts
            ResultSet allSet = DBManager.getResultSet("SELECT * FROM users;");

            while(allSet.next()){
                User user1 = new User(allSet.getString(2), allSet.getString(3));
                user1.setID(allSet.getInt("id"));
                user1.setAge(allSet.getInt("age"));
                user1.setBio(allSet.getString("Bio"));
                user1.setEmail(allSet.getString("Email"));
                user1.setPhoneNumber(allSet.getString("Phonenumber"));
                user1.setGender(allSet.getBoolean("Gender"));
                user1.setPrivate(allSet.getBoolean("isPrivate"));
                user1.setNormal(allSet.getBoolean("isNormal"));


                if(!User.Users.contains(user1))
                    User.Users.add(user1);
            }

            //Initialize All Groups
            ResultSet groupSet = DBManager.getResultSet("SELECT * FROM `groups`;");
            while(groupSet.next()){
                Group group = DBManager.getObject("`groups`",groupSet.getInt("id"),Group.class);
                Group g = DBManager.getObject("`groups`",groupSet.getInt("id"),Group.class);
                group.setID(groupSet.getInt("id"));

                group.getMembers().clear();


                //System.out.println(group.getMembers().get(0).getUserName() + "  " + group.getMembers().get(1).getUserName());

                for (User user1 : g.getMembers()){
                    for(User user2 : Users)
                     if(user2.getID() == user1.getID()) {
                         user2.getGroups().add(group);
                         group.getMembers().add(user2);
                     }
                }
                

                Group.Groups.add(group);

            }

            ///


            ResultSet followersSet = DBManager.getResultSet("SELECT * FROM follow WHERE followed_id = " + user.getID());
            //followersSet.next();
            while (followersSet.next())
            {
                for(User u : User.Users)
                    if(u.getID()==followersSet.getInt(1))
                    {user.getFollowers().add(u); break;}
            }

            ResultSet followingSet = DBManager.getResultSet("SELECT * FROM follow WHERE follower_id = " + user.getID());

            while (followingSet.next())
            {
                for(User u : User.Users)
                    if(u.getID()==followingSet.getInt(2))
                    {user.getFollowed().add(u); break;}
            }


        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }


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

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setBio(String bio) {
        Bio = bio;
    }

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public int compareTo(Post o) {
        return 0;
    }
}
