package com.uniovi.sdientrega132.services;
import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private PublicationsService publicationsService;
    @Autowired
    private FriendsService friendsService;
    @Autowired
    private RolesService rolesService;

    @PostConstruct
    public void init() {
        //Usuario normal (ROLE_STANDARDUSER)
        User user1 = new User("Andrea", "Delgado", "user01@email.com");
        user1.setPassword("user01");
        user1.setRole(rolesService.getRoles()[0]);
        //Usuario admin (ROLE_ADMIN)
        User admin = new User("Juan", "Diaz", "admin@email.com");
        admin.setPassword("admin");
        admin.setRole(rolesService.getRoles()[1]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user2 = new User("Sara", "Alonso", "user02@email.com");
        user2.setPassword("user02");
        user2.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user3 = new User("Nuria", "Inchaurrandieta", "user03@email.com");
        user3.setPassword("user03");
        user3.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user4 = new User("Juan", "Martinez", "user04@email.com");
        user4.setPassword("user04");
        user4.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user5 = new User("Alex", "Delgado", "user05@email.com");
        user5.setPassword("user05");
        user5.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user6 = new User("Luis", "Garcia", "user06@email.com");
        user6.setPassword("user06");
        user6.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user7 = new User("Clara", "Alvarez", "user07@email.com");
        user7.setPassword("user07");
        user7.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user8 = new User("Lucia", "Alvarez", "user08@email.com");
        user8.setPassword("user08");
        user8.setRole(rolesService.getRoles()[0]);

        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
        usersService.addUser(user7);
        usersService.addUser(user8);
        usersService.addUser(admin);

        user1 = usersService.getUserByEmail(user1.getEmail());
        user2 = usersService.getUserByEmail(user2.getEmail());
        user3 = usersService.getUserByEmail(user3.getEmail());
        user4 = usersService.getUserByEmail(user4.getEmail());
        user5 = usersService.getUserByEmail(user5.getEmail());
        user6 = usersService.getUserByEmail(user6.getEmail());
        user7 = usersService.getUserByEmail(user7.getEmail());
        user8 = usersService.getUserByEmail(user8.getEmail());

        //friendsService.addFriend(new Friend(user1.getId(), user6.getId(), true));
        //friendsService.addFriend(new Friend(user1.getId(), user3.getId(), true));
        //friendsService.addFriend(new Friend(user1.getId(), user4.getId(), true));
        //friendsService.addFriend(new Friend(user5.getId(), user2.getId(), false));
        //friendsService.addFriend(new Friend(user5.getId(), user3.getId(), false));
        //friendsService.addFriend(new Friend(user5.getId(), user4.getId(), false));
        //friendsService.addFriend(new Friend(user5.getId(), user7.getId(), false));
        //friendsService.addFriend(new Friend(user5.getId(), user8.getId(), false));
        //friendsService.addFriend(new Friend(user5.getId(), user1.getId(), true));
/**
        friendsService.addFriend(new Friend(user1.getId(), user2.getId(), true));
        friendsService.addFriend(new Friend(user1.getId(), user3.getId(), true));
        friendsService.addFriend(new Friend(user1.getId(), user4.getId(), true));
        friendsService.addFriend(new Friend(user5.getId(), user2.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user3.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user4.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user7.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user8.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user1.getId(), true));
        friendsService.addFriend(new Friend(user4.getId(), user3.getId(), false));
*/

        Publication pub1 = new Publication("publicacion 1 de Andrea", "probando las publicaciones", user1);
        Publication pub2 = new Publication("Hola :P", "Esto es aburrido", user6);
        Publication pub3 = new Publication("publicacion 2 de Andrea", "buscando amigos", user1);

        publicationsService.addPublication(pub1);
        publicationsService.addPublication(pub2);
        publicationsService.addPublication(pub3);

    }
}
