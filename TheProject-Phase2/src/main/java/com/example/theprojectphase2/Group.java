package com.example.theprojectphase2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Group implements Comparable<Group> ,Saveable {

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

    public transient static ArrayList<Group> Groups = new ArrayList<>();




    int ID;
    String GroupName;
    User Owner;
    ArrayList<User> Managers = new ArrayList<>();
    ArrayList<User> Members = new ArrayList<>();
    int NumberOfUsers;
    ArrayList<Post> Posts = new ArrayList<>();

    Group(String groupName) {this.GroupName=groupName;}

    private static final String TABLE_NAME = "groups";


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

         DBManager.save(group);
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
    public String getTableName() {
        return TABLE_NAME;
    }

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


    @Override
    public int compareTo(Group o) {
        if(this.ID > o.ID)
            return 1;
        else return 0;
    }
}
