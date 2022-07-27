package com.example.theprojectphase2;

import java.util.ArrayList;

public class Comment extends Post {
    private static final String TABLE_NAME="comments";

    public static transient ArrayList<Comment> Comments = new ArrayList<>();

    Comment(String context , User owner){
        super("", context , owner);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }



}
