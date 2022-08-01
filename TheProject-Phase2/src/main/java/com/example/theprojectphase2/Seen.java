package com.example.theprojectphase2;

import org.joda.time.LocalDateTime;

import java.util.Objects;

public class Seen {
    LocalDateTime dateTime;
    Integer watcherId;

    public Seen(User user){
        watcherId = user.getID();
        dateTime = LocalDateTime.now();
    }

    public User getUser(){
        User u = null;
        for(User user : User.Users)
            if(user.getID() == watcherId)
                u = user;
        return u;
    }

    public LocalDateTime getDateTime(){
        return dateTime;
    }

    @Override
    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(other instanceof Seen){
            Seen seen = (Seen)other;
            return (Objects.equals(((Seen) other).watcherId, watcherId));
        }

        return false;
    }

}
