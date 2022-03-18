package com.uniovi.sdientrega132.entities;

public class FriendsForAll {

    private Friend friend;
    private User user;

    public FriendsForAll(Friend friend, User user) {
        this.friend = friend;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
