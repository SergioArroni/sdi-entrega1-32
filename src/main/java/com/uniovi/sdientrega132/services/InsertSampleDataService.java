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
        User user1 = new User("Pedro", "Gómez", "pedro@gmail.com");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        User user2 = new User("Lucas", "Lucas", "lucas@gmail.com");
        user2.setPassword("123456");
        user2.setRole(rolesService.getRoles()[0]);
        User user3 = new User("María", "María", "mari@gmail.com");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        User user4 = new User("Marta", "Marta", "martita@gmail.com");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[1]);
        User user5 = new User("Pelayo", "Pelayo", "pelayo@gmail.com");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[1]);
        User user6 = new User("Edward", "Edward", "edward@uniovi.es");
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