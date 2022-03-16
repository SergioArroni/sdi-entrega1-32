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
        //Usuario normal (ROLE_STANDARDUSER)
        User user1 = new User("Andrea", "Delgado", "uo271355@uniovi.es");
        user1.setPassword("123456");
        user1.setRole(rolesService.getRoles()[0]);
        //Usuario admin (ROLE_ADMIN)
        User user2 = new User("Juan", "Diaz", "admin@email.com");
        user2.setPassword("admin");
        user2.setRole(rolesService.getRoles()[1]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user3 = new User("Sara", "Alonso", "sara@uniovi.es");
        user3.setPassword("123456");
        user3.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user4 = new User("Juan", "Martinez", "juan@uniovi.es");
        user4.setPassword("123456");
        user4.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user5 = new User("Alex", "Delgado", "alex@uniovi.es");
        user5.setPassword("123456");
        user5.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user6 = new User("Luis", "Garcia", "luis@uniovi.es");
        user6.setPassword("123456");
        user6.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user7 = new User("Clara", "Alvarez", "clara@uniovi.es");
        user7.setPassword("123456");
        user7.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user8 = new User("Lucia", "Alvarez", "lucia@uniovi.es");
        user8.setPassword("123456");
        user8.setRole(rolesService.getRoles()[0]);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
        usersService.addUser(user7);
        usersService.addUser(user8);

        user1 = usersService.getUserByEmail(user1.getEmail());
        user2 = usersService.getUserByEmail(user2.getEmail());
        user3 = usersService.getUserByEmail(user3.getEmail());
        user4 = usersService.getUserByEmail(user4.getEmail());
        user5 = usersService.getUserByEmail(user5.getEmail());
        user6 = usersService.getUserByEmail(user6.getEmail());
        user7 = usersService.getUserByEmail(user7.getEmail());
        user8 = usersService.getUserByEmail(user8.getEmail());
    }
}
