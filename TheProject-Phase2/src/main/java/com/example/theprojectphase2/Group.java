package com.example.theprojectphase2;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

@DBTable(tableName = "projectgroups")
public class Group  {

    @DBPrimaryKey
    @DBAutoIncrement
    @DBField(name = "Id")
    Integer ID = 0;

    public static ArrayList<Group> Groups = new ArrayList<>();

    @DBField(name = "GroupName")
    String GroupName;

    @DBField(name = "GroupOwner")
    Integer OwnerId = 0;

    User Owner;

    @DBField(name = "GroupManagers")
    ArrayList<Double> ManagersId = new ArrayList<>();

    ArrayList<User> Managers = new ArrayList<>();

    @DBField(name = "GroupMembers")
    ArrayList<Double> MembersId = new ArrayList<>();

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getImageString() {
        return imageString;
    }

    public Image getImage() {
        return image;
    }

    ArrayList<User> Members = new ArrayList<>();

    @DBField(name = "NumberOfUsers")
    Integer NumberOfUsers = 0;

    @DBField(name = "image")
    String imageString;

    Image image;

    @DBField(name = "GroupPosts")
    ArrayList<Double> PostsId = new ArrayList<>();

    ArrayList<Post> Posts = new ArrayList<>();

    Group(String groupName) {this.GroupName=groupName;}
    Group(){}


    public String getGroupName() {return GroupName;}

    public User getOwner() {
        return Owner;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public void setOwner(User owner) {
        Owner = owner;
    }

    public void setManagers(ArrayList<User> managers) {
        Managers = managers;
    }

    public void setMembers(ArrayList<User> members) {
        Members = members;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        NumberOfUsers = numberOfUsers;
    }

    public void setPosts(ArrayList<Post> posts) {
        Posts = posts;
    }

    public ArrayList<User> getManagers() {
        return Managers;
    }

    public ArrayList<User> getMembers() {
        return Members;
    }

    public int getNumberOfUsers() {
        return NumberOfUsers;
    }

    public ArrayList<Post> getPosts() {
        return Posts;
    }


    public static void CreateGroup(Scanner scanner , User user) throws SQLException {
        System.out.println("Choose Your group Name");
        Group group = new Group(scanner.nextLine());
        group.setOwner(user);
        group.getMembers().add(user);

        group.setID(DBManager.getMaxId()+1);

        //System.out.println("Choose members. Type in 'Exit' to end process.");

        User.viewOptions(scanner , user,group);

        System.out.println("Group Created Successfully.");


        group.setNumberOfUsers(group.getMembers().size());

        for(User user1 : group.getMembers()){
            user1.getGroups().add(group);
        }

    }

    public static void viewGroups(Scanner scanner ,User user) throws SQLException{

        for(Group group : user.getGroups()){
            System.out.println(group.getID()+". "+group.getGroupName());
            if(group.getPosts().size()!=0) System.out.println("      "+group.getPosts().get(group.getPosts().size()-1)+"\n\n");
        }

        for(Group group : user.getGroups()){
            if(group.getID()==Integer.parseInt(scanner.nextLine())){
                viewGroup(group , user);
                break;
            }
        }


    }

    private static void viewGroup(Group group , User user){
        System.out.println("\n\n" + group.getGroupName());
        System.out.println(group.getNumberOfUsers() + "  Members \n");

        for(Post post : group.getPosts()){
               if(post.getOwner().getID()== user.getID()){
                   System.out.println("\t\t\t\t\t "+post.getContext()+"  "+post.getOwner().getUserName());
               }

               else
               {
                   System.out.println(post.getOwner().getUserName() + "   " + post.getContext());
               }
        }

    }

    int getID() {return ID;}



    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        if(getID() == otherGroup.getID()){
            return true;
        }
        return false;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setOwnerId(Integer ownerId) {
        OwnerId = ownerId;
    }

    public void setManagersId(ArrayList<Double> managersId) {
        ManagersId = managersId;
    }

    public void setMembersId(ArrayList<Double> membersId) {
        MembersId = membersId;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        NumberOfUsers = numberOfUsers;
    }

    public void setPostsId(ArrayList<Double> postsId) {
        PostsId = postsId;
    }

    public Integer getOwnerId() {
        return OwnerId;
    }

    public ArrayList<Double> getManagersId() {
        return ManagersId;
    }

    public ArrayList<Double> getMembersId() {
        return MembersId;
    }

    public ArrayList<Double> getPostsId() {
        return PostsId;
    }
}
