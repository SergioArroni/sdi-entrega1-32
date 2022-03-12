package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @PostConstruct
    public void init() {
        User user1 = new User("99999990A", "Pedro", "99999990A");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("99999991B", "Lucas", "99999991B");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("99999992C", "María", "99999992C");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("99999993D", "Marta", "99999993D");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("99999977E", "Pelayo", "99999977E");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);
        User user6 = new User("99999988F", "Edward", "99999988F");
        user6.setPassword("123456");
        user6.setRole(rolesService.getRoles()[1]);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
    }
}