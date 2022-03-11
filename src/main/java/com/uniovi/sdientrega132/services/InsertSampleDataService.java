package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FriendsService friendsService;

    @PostConstruct
    public void init() {
        User user1 = new User("Pedro", "Diaz", "xd@gmail.com");
        user1.setPassword("123456");
        User user2 = new User("Lucas", "Rodriguez", "xd1@gmail.com");
        user2.setPassword("123456");
        User user3 = new User("Maria", "Belmonte", "xd2@gmail.com");
        user3.setPassword("123456");
        User user4 = new User("Marta", "Almonte", "xd3@gmail.com");
        user4.setPassword("123456");

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);

        friendsService.addFriend(new Friend(1, user2.getId(), false));
        friendsService.addFriend(new Friend(1, user3.getId(), false));
        friendsService.addFriend(new Friend(1, user4.getId(), false));
    }
}
