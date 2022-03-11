package com.uniovi.sdientrega132.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {
    @Id
    @GeneratedValue
    private long id;
    private long User1_id;
    private long User2_id;
    private Boolean accept;


    public Friend() {
    }

    public Friend(long User1_id, long User2_id, Boolean accept) {
        this.User1_id = User1_id;
        this.User2_id = User2_id;
        this.accept = accept;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser1_id() {
        return User1_id;
    }

    public void setUser1_id(long user1_id) {
        this.User1_id = user1_id;
    }

    public long getUser2_id() {
        return User2_id;
    }

    public void setUser2_id(long user2_id) {
        this.User2_id = user2_id;
    }

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", user1_id='" + User1_id + '\'' +
                ", user2_id='" + User2_id + '\'' +
                ", accept=" + accept +
                '}';
    }
}
