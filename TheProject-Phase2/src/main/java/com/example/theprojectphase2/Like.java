package com.example.theprojectphase2;

import org.joda.time.LocalDateTime;

public class Like {
    LocalDateTime dateTime =null;
    User liker = null;

    public Like(User user){
        liker = user;
        dateTime = LocalDateTime.now();
    }

    public User getLiker(){
        return liker;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(other instanceof Like like){
            return like.getLiker().equals(liker);
        }

        return false;
    }
}
