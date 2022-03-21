package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.Log;
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
    @Autowired
    private LogsService logsService;

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
        //Usuario normal (ROLE_STANDARDUSER)
        User user9 = new User("Paco", "Diaz", "user09@email.com");
        user9.setPassword("user09");
        user9.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user10 = new User("Juan", "Alonso", "user10@email.com");
        user10.setPassword("user10");
        user10.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user11 = new User("Fernando", "Alvarez", "user11@email.com");
        user11.setPassword("user11");
        user11.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user12 = new User("Hugo", "Garcia", "user12@email.com");
        user12.setPassword("user12");
        user12.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user13 = new User("Miriam", "Gonzalez", "user13@email.com");
        user13.setPassword("user13");
        user13.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user14 = new User("Marta", "Alonso", "user14@email.com");
        user14.setPassword("user14");
        user14.setRole(rolesService.getRoles()[0]);
        //Usuario normal (ROLE_STANDARDUSER)
        User user15 = new User("Sara", "Garcia", "user15@email.com");
        user15.setPassword("user15");
        user15.setRole(rolesService.getRoles()[0]);


        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
        usersService.addUser(user7);
        usersService.addUser(user8);
        usersService.addUser(user9);
        usersService.addUser(user10);
        usersService.addUser(user11);
        usersService.addUser(user12);
        usersService.addUser(user13);
        usersService.addUser(user14);
        usersService.addUser(user15);
        usersService.addUser(admin);


        user1 = usersService.getUserByEmail(user1.getEmail());
        user2 = usersService.getUserByEmail(user2.getEmail());
        user3 = usersService.getUserByEmail(user3.getEmail());
        user4 = usersService.getUserByEmail(user4.getEmail());
        user5 = usersService.getUserByEmail(user5.getEmail());
        user6 = usersService.getUserByEmail(user6.getEmail());
        user7 = usersService.getUserByEmail(user7.getEmail());
        user8 = usersService.getUserByEmail(user8.getEmail());
        user9 = usersService.getUserByEmail(user9.getEmail());
        user10 = usersService.getUserByEmail(user10.getEmail());
        user11 = usersService.getUserByEmail(user11.getEmail());
        user12 = usersService.getUserByEmail(user12.getEmail());
        user13 = usersService.getUserByEmail(user13.getEmail());
        user14 = usersService.getUserByEmail(user14.getEmail());
        user15 = usersService.getUserByEmail(user15.getEmail());

        friendsService.addFriend(new Friend(user1.getId(), user6.getId(), true));
        friendsService.addFriend(new Friend(user1.getId(), user3.getId(), true));
        friendsService.addFriend(new Friend(user1.getId(), user4.getId(), true));
        friendsService.addFriend(new Friend(user5.getId(), user2.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user3.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user4.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user7.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user8.getId(), false));
        friendsService.addFriend(new Friend(user5.getId(), user1.getId(), true));
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
        Publication pub2 = new Publication("publicacion 2 de Andrea", "buscando amigos", user1);
        Publication pub3 = new Publication("Hola :P", "Esto es aburrido", user6);
        Publication pub4 = new Publication("publicacion 1 de Juan", "Primer comentario.", user4);
        Publication pub5 = new Publication("Nueva publicacion", "Me aburrooo", user6);
        Publication pub6 = new Publication("Buenaas", "uwu", user2);
        Publication pub7 = new Publication("Esto no lo lee nadie", "Sample text", user4);
        Publication pub8 = new Publication("Publicacion 1 de Nuria", "Gracias por aceptar la petición, Alex", user3);
        Publication pub9 = new Publication(">Respuesta", "Nadaa", user5);
        Publication pub10 = new Publication("como funciona esto", "?", user5);
        Publication pub11 = new Publication("Petición de amistad", "Me aceptas la petición, Sara?", user3);
        Publication pub12 = new Publication(">Respuesta", "Nope", user2);

        Publication pub_1_3 = new Publication("pub T-1-3", "pub P-1-3", user1);
        Publication pub_1_4 = new Publication("pub T-1-4", "pub P-1-4", user1);
        Publication pub_1_5 = new Publication("pub T-1-5", "pub P-1-5", user1);
        Publication pub_1_6 = new Publication("pub T-1-6", "pub P-1-6", user1);
        Publication pub_1_7 = new Publication("pub T-1-7", "pub P-1-7", user1);
        Publication pub_1_8 = new Publication("pub T-1-8", "pub P-1-8", user1);
        Publication pub_1_9 = new Publication("pub T-1-9", "pub P-1-9", user1);
        Publication pub_1_10 = new Publication("pub T-1-10", "pub P-1-10", user1);

        Publication pub_2_3 = new Publication("pub T-2-3", "pub P-2-3", user2);
        Publication pub_2_4 = new Publication("pub T-2-4", "pub P-2-4", user2);
        Publication pub_2_5 = new Publication("pub T-2-5", "pub P-2-5", user2);
        Publication pub_2_6 = new Publication("pub T-2-6", "pub P-2-6", user2);
        Publication pub_2_7 = new Publication("pub T-2-7", "pub P-2-7", user2);
        Publication pub_2_8 = new Publication("pub T-2-8", "pub P-2-8", user2);
        Publication pub_2_9 = new Publication("pub T-2-9", "pub P-2-9", user2);
        Publication pub_2_10 = new Publication("pub T-2-10", "pub P-2-10", user2);

        Publication pub_3_3 = new Publication("pub T-3-3", "pub P-3-3", user3);
        Publication pub_3_4 = new Publication("pub T-3-4", "pub P-3-4", user3);
        Publication pub_3_5 = new Publication("pub T-3-5", "pub P-3-5", user3);
        Publication pub_3_6 = new Publication("pub T-3-6", "pub P-3-6", user3);
        Publication pub_3_7 = new Publication("pub T-3-7", "pub P-3-7", user3);
        Publication pub_3_8 = new Publication("pub T-3-8", "pub P-3-8", user3);
        Publication pub_3_9 = new Publication("pub T-3-9", "pub P-3-9", user3);
        Publication pub_3_10 = new Publication("pub T-3-10", "pub P-3-10", user3);

        Publication pub_4_3 = new Publication("pub T-4-3", "pub P-4-3", user4);
        Publication pub_4_4 = new Publication("pub T-4-4", "pub P-4-4", user4);
        Publication pub_4_5 = new Publication("pub T-4-5", "pub P-4-5", user4);
        Publication pub_4_6 = new Publication("pub T-4-6", "pub P-4-6", user4);
        Publication pub_4_7 = new Publication("pub T-4-7", "pub P-4-7", user4);
        Publication pub_4_8 = new Publication("pub T-4-8", "pub P-4-8", user4);
        Publication pub_4_9 = new Publication("pub T-4-9", "pub P-4-9", user4);
        Publication pub_4_10 = new Publication("pub T-4-10", "pub P-4-10", user4);

        Publication pub_5_3 = new Publication("pub T-5-3", "pub P-5-3", user5);
        Publication pub_5_4 = new Publication("pub T-5-4", "pub P-5-4", user5);
        Publication pub_5_5 = new Publication("pub T-5-5", "pub P-5-5", user5);
        Publication pub_5_6 = new Publication("pub T-5-6", "pub P-5-6", user5);
        Publication pub_5_7 = new Publication("pub T-5-7", "pub P-5-7", user5);
        Publication pub_5_8 = new Publication("pub T-5-8", "pub P-5-8", user5);
        Publication pub_5_9 = new Publication("pub T-5-9", "pub P-5-9", user5);
        Publication pub_5_10 = new Publication("pub T-5-10", "pub P-5-10", user5);

        Publication pub_6_3 = new Publication("pub T-6-3", "pub P-6-3", user6);
        Publication pub_6_4 = new Publication("pub T-6-4", "pub P-6-4", user6);
        Publication pub_6_5 = new Publication("pub T-6-5", "pub P-6-5", user6);
        Publication pub_6_6 = new Publication("pub T-6-6", "pub P-6-6", user6);
        Publication pub_6_7 = new Publication("pub T-6-7", "pub P-6-7", user6);
        Publication pub_6_8 = new Publication("pub T-6-8", "pub P-6-8", user6);
        Publication pub_6_9 = new Publication("pub T-6-9", "pub P-6-9", user6);
        Publication pub_6_10 = new Publication("pub T-6-10", "pub P-6-10", user6);

        Publication pub_7_1 = new Publication("pub T-7-3", "pub P-7-3", user7);
        Publication pub_7_2 = new Publication("pub T-7-2", "pub P-7-2", user7);
        Publication pub_7_3 = new Publication("pub T-7-3", "pub P-7-3", user7);
        Publication pub_7_4 = new Publication("pub T-7-4", "pub P-7-4", user7);
        Publication pub_7_5 = new Publication("pub T-7-5", "pub P-7-5", user7);
        Publication pub_7_6 = new Publication("pub T-7-6", "pub P-7-6", user7);
        Publication pub_7_7 = new Publication("pub T-7-7", "pub P-7-7", user7);
        Publication pub_7_8 = new Publication("pub T-7-8", "pub P-7-8", user7);
        Publication pub_7_9 = new Publication("pub T-7-9", "pub P-7-9", user7);
        Publication pub_7_10 = new Publication("pub T-7-10", "pub P-7-10", user7);

        Publication pub_8_1 = new Publication("pub T-8-3", "pub P-8-3", user8);
        Publication pub_8_2 = new Publication("pub T-8-2", "pub P-8-2", user8);
        Publication pub_8_3 = new Publication("pub T-8-3", "pub P-8-3", user8);
        Publication pub_8_4 = new Publication("pub T-8-4", "pub P-8-4", user8);
        Publication pub_8_5 = new Publication("pub T-8-5", "pub P-8-5", user8);
        Publication pub_8_6 = new Publication("pub T-8-6", "pub P-8-6", user8);
        Publication pub_8_7 = new Publication("pub T-8-7", "pub P-8-7", user8);
        Publication pub_8_8 = new Publication("pub T-8-8", "pub P-8-8", user8);
        Publication pub_8_9 = new Publication("pub T-8-9", "pub P-8-9", user8);
        Publication pub_8_10 = new Publication("pub T-8-10", "pub P-8-10", user8);

        Publication pub_9_1 = new Publication("pub T-9-3", "pub P-9-3", user9);
        Publication pub_9_2 = new Publication("pub T-9-2", "pub P-9-2", user9);
        Publication pub_9_3 = new Publication("pub T-9-3", "pub P-9-3", user9);
        Publication pub_9_4 = new Publication("pub T-9-4", "pub P-9-4", user9);
        Publication pub_9_5 = new Publication("pub T-9-5", "pub P-9-5", user9);
        Publication pub_9_6 = new Publication("pub T-9-6", "pub P-9-6", user9);
        Publication pub_9_7 = new Publication("pub T-9-7", "pub P-9-7", user9);
        Publication pub_9_8 = new Publication("pub T-9-8", "pub P-9-8", user9);
        Publication pub_9_9 = new Publication("pub T-9-9", "pub P-9-9", user9);
        Publication pub_9_10 = new Publication("pub T-9-10", "pub P-9-10", user9);

        Publication pub_10_1 = new Publication("pub T-10-3", "pub P-10-3", user10);
        Publication pub_10_2 = new Publication("pub T-10-2", "pub P-10-2", user10);
        Publication pub_10_3 = new Publication("pub T-10-3", "pub P-10-3", user10);
        Publication pub_10_4 = new Publication("pub T-10-4", "pub P-10-4", user10);
        Publication pub_10_5 = new Publication("pub T-10-5", "pub P-10-5", user10);
        Publication pub_10_6 = new Publication("pub T-10-6", "pub P-10-6", user10);
        Publication pub_10_7 = new Publication("pub T-10-7", "pub P-10-7", user10);
        Publication pub_10_8 = new Publication("pub T-10-8", "pub P-10-8", user10);
        Publication pub_10_9 = new Publication("pub T-10-9", "pub P-10-9", user10);
        Publication pub_10_10 = new Publication("pub T-10-10", "pub P-10-10", user10);

        Publication pub_11_1 = new Publication("pub T-11-3", "pub P-11-3", user11);
        Publication pub_11_2 = new Publication("pub T-11-2", "pub P-11-2", user11);
        Publication pub_11_3 = new Publication("pub T-11-3", "pub P-11-3", user11);
        Publication pub_11_4 = new Publication("pub T-11-4", "pub P-11-4", user11);
        Publication pub_11_5 = new Publication("pub T-11-5", "pub P-11-5", user11);
        Publication pub_11_6 = new Publication("pub T-11-6", "pub P-11-6", user11);
        Publication pub_11_7 = new Publication("pub T-11-7", "pub P-11-7", user11);
        Publication pub_11_8 = new Publication("pub T-11-8", "pub P-11-8", user11);
        Publication pub_11_9 = new Publication("pub T-11-9", "pub P-11-9", user11);
        Publication pub_11_10 = new Publication("pub T-11-10", "pub P-11-10", user11);

        Publication pub_12_1 = new Publication("pub T-12-3", "pub P-12-3", user12);
        Publication pub_12_2 = new Publication("pub T-12-2", "pub P-12-2", user12);
        Publication pub_12_3 = new Publication("pub T-12-3", "pub P-12-3", user12);
        Publication pub_12_4 = new Publication("pub T-12-4", "pub P-12-4", user12);
        Publication pub_12_5 = new Publication("pub T-12-5", "pub P-12-5", user12);
        Publication pub_12_6 = new Publication("pub T-12-6", "pub P-12-6", user12);
        Publication pub_12_7 = new Publication("pub T-12-7", "pub P-12-7", user12);
        Publication pub_12_8 = new Publication("pub T-12-8", "pub P-12-8", user12);
        Publication pub_12_9 = new Publication("pub T-12-9", "pub P-12-9", user12);
        Publication pub_12_10 = new Publication("pub T-12-10", "pub P-12-10", user12);

        Publication pub_13_1 = new Publication("pub T-13-3", "pub P-13-3", user13);
        Publication pub_13_2 = new Publication("pub T-13-2", "pub P-13-2", user13);
        Publication pub_13_3 = new Publication("pub T-13-3", "pub P-13-3", user13);
        Publication pub_13_4 = new Publication("pub T-13-4", "pub P-13-4", user13);
        Publication pub_13_5 = new Publication("pub T-13-5", "pub P-13-5", user13);
        Publication pub_13_6 = new Publication("pub T-13-6", "pub P-13-6", user13);
        Publication pub_13_7 = new Publication("pub T-13-7", "pub P-13-7", user13);
        Publication pub_13_8 = new Publication("pub T-13-8", "pub P-13-8", user13);
        Publication pub_13_9 = new Publication("pub T-13-9", "pub P-13-9", user13);
        Publication pub_13_10 = new Publication("pub T-13-10", "pub P-13-10", user13);

        Publication pub_14_1 = new Publication("pub T-14-3", "pub P-14-3", user14);
        Publication pub_14_2 = new Publication("pub T-14-2", "pub P-14-2", user14);
        Publication pub_14_3 = new Publication("pub T-14-3", "pub P-14-3", user14);
        Publication pub_14_4 = new Publication("pub T-14-4", "pub P-14-4", user14);
        Publication pub_14_5 = new Publication("pub T-14-5", "pub P-14-5", user14);
        Publication pub_14_6 = new Publication("pub T-14-6", "pub P-14-6", user14);
        Publication pub_14_7 = new Publication("pub T-14-7", "pub P-14-7", user14);
        Publication pub_14_8 = new Publication("pub T-14-8", "pub P-14-8", user14);
        Publication pub_14_9 = new Publication("pub T-14-9", "pub P-14-9", user14);
        Publication pub_14_10 = new Publication("pub T-14-10", "pub P-14-10", user14);

        Publication pub_15_1 = new Publication("pub T-15-3", "pub P-15-3", user15);
        Publication pub_15_2 = new Publication("pub T-15-2", "pub P-15-2", user15);
        Publication pub_15_3 = new Publication("pub T-15-3", "pub P-15-3", user15);
        Publication pub_15_4 = new Publication("pub T-15-4", "pub P-15-4", user15);
        Publication pub_15_5 = new Publication("pub T-15-5", "pub P-15-5", user15);
        Publication pub_15_6 = new Publication("pub T-15-6", "pub P-15-6", user15);
        Publication pub_15_7 = new Publication("pub T-15-7", "pub P-15-7", user15);
        Publication pub_15_8 = new Publication("pub T-15-8", "pub P-15-8", user15);
        Publication pub_15_9 = new Publication("pub T-15-9", "pub P-15-9", user15);
        Publication pub_15_10 = new Publication("pub T-15-10", "pub P-15-10", user15);

        publicationsService.addPublication(pub1);
        publicationsService.addPublication(pub2);
        publicationsService.addPublication(pub3);
        publicationsService.addPublication(pub4);
        publicationsService.addPublication(pub5);
        publicationsService.addPublication(pub6);
        publicationsService.addPublication(pub7);
        publicationsService.addPublication(pub8);
        publicationsService.addPublication(pub9);
        publicationsService.addPublication(pub10);
        publicationsService.addPublication(pub11);
        publicationsService.addPublication(pub12);

        logsService.addLog(new Log("PET","2022-03-21 20:54:01.1847723","Mapping: / Method: GET Param:" ));
        logsService.addLog(new Log("PET","2022-03-21 21:04:42.3237548","Mapping: /logs/list Method: GET Param:" ));
        logsService.addLog(new Log("LOGIN-EX","2022-03-21 21:04:33.2715268","Username: user01@email.com" ));
        logsService.addLog(new Log("LOGIN-EX","2022-03-21 21:04:37.1219494","Username: admin@email.com" ));
        logsService.addLog(new Log("LOGIN-ERR","2022-03-21 21:04:33.2635488","Username: user01@email.com" ));
        logsService.addLog(new Log("LOGIN-ERR","2022-03-21 21:04:33.2675379","Username: admin@email.com" ));
        logsService.addLog(new Log("ALTA","2022-03-21 21:04:37.2226464","Username: user01@email.com" ));
        logsService.addLog(new Log("ALTA","2022-03-21 21:04:33.2445986","Username: admin@email.com" ));
        logsService.addLog(new Log("LOGOUT","2022-03-21 21:04:31.9370754","Username: user01@email.com" ));
        logsService.addLog(new Log("LOGOUT","2022-03-21 21:04:37.2276331","Username: admin@email.com" ));

        publicationsService.addPublication(pub_1_3);
        publicationsService.addPublication(pub_1_4);
        publicationsService.addPublication(pub_1_5);
        publicationsService.addPublication(pub_1_6);
        publicationsService.addPublication(pub_1_7);
        publicationsService.addPublication(pub_1_8);
        publicationsService.addPublication(pub_1_9);
        publicationsService.addPublication(pub_1_10);

        publicationsService.addPublication(pub_2_3);
        publicationsService.addPublication(pub_2_4);
        publicationsService.addPublication(pub_2_5);
        publicationsService.addPublication(pub_2_6);
        publicationsService.addPublication(pub_2_7);
        publicationsService.addPublication(pub_2_8);
        publicationsService.addPublication(pub_2_9);
        publicationsService.addPublication(pub_2_10);

        publicationsService.addPublication(pub_3_3);
        publicationsService.addPublication(pub_3_4);
        publicationsService.addPublication(pub_3_5);
        publicationsService.addPublication(pub_3_6);
        publicationsService.addPublication(pub_3_7);
        publicationsService.addPublication(pub_3_8);
        publicationsService.addPublication(pub_3_9);
        publicationsService.addPublication(pub_3_10);

        publicationsService.addPublication(pub_4_3);
        publicationsService.addPublication(pub_4_4);
        publicationsService.addPublication(pub_4_5);
        publicationsService.addPublication(pub_4_6);
        publicationsService.addPublication(pub_4_7);
        publicationsService.addPublication(pub_4_8);
        publicationsService.addPublication(pub_4_9);
        publicationsService.addPublication(pub_4_10);

        publicationsService.addPublication(pub_5_3);
        publicationsService.addPublication(pub_5_4);
        publicationsService.addPublication(pub_5_5);
        publicationsService.addPublication(pub_5_6);
        publicationsService.addPublication(pub_5_7);
        publicationsService.addPublication(pub_5_8);
        publicationsService.addPublication(pub_5_9);
        publicationsService.addPublication(pub_5_10);

        publicationsService.addPublication(pub_6_3);
        publicationsService.addPublication(pub_6_4);
        publicationsService.addPublication(pub_6_5);
        publicationsService.addPublication(pub_6_6);
        publicationsService.addPublication(pub_6_7);
        publicationsService.addPublication(pub_6_8);
        publicationsService.addPublication(pub_6_9);
        publicationsService.addPublication(pub_6_10);

        publicationsService.addPublication(pub_7_1);
        publicationsService.addPublication(pub_7_2);
        publicationsService.addPublication(pub_7_3);
        publicationsService.addPublication(pub_7_4);
        publicationsService.addPublication(pub_7_5);
        publicationsService.addPublication(pub_7_6);
        publicationsService.addPublication(pub_7_7);
        publicationsService.addPublication(pub_7_8);
        publicationsService.addPublication(pub_7_9);
        publicationsService.addPublication(pub_7_10);

        publicationsService.addPublication(pub_8_1);
        publicationsService.addPublication(pub_8_2);
        publicationsService.addPublication(pub_8_3);
        publicationsService.addPublication(pub_8_4);
        publicationsService.addPublication(pub_8_5);
        publicationsService.addPublication(pub_8_6);
        publicationsService.addPublication(pub_8_7);
        publicationsService.addPublication(pub_8_8);
        publicationsService.addPublication(pub_8_9);
        publicationsService.addPublication(pub_8_10);

        publicationsService.addPublication(pub_9_1);
        publicationsService.addPublication(pub_9_2);
        publicationsService.addPublication(pub_9_3);
        publicationsService.addPublication(pub_9_4);
        publicationsService.addPublication(pub_9_5);
        publicationsService.addPublication(pub_9_6);
        publicationsService.addPublication(pub_9_7);
        publicationsService.addPublication(pub_9_8);
        publicationsService.addPublication(pub_9_9);
        publicationsService.addPublication(pub_9_10);

        publicationsService.addPublication(pub_10_1);
        publicationsService.addPublication(pub_10_2);
        publicationsService.addPublication(pub_10_3);
        publicationsService.addPublication(pub_10_4);
        publicationsService.addPublication(pub_10_5);
        publicationsService.addPublication(pub_10_6);
        publicationsService.addPublication(pub_10_7);
        publicationsService.addPublication(pub_10_8);
        publicationsService.addPublication(pub_10_9);
        publicationsService.addPublication(pub_10_10);

        publicationsService.addPublication(pub_11_1);
        publicationsService.addPublication(pub_11_2);
        publicationsService.addPublication(pub_11_3);
        publicationsService.addPublication(pub_11_4);
        publicationsService.addPublication(pub_11_5);
        publicationsService.addPublication(pub_11_6);
        publicationsService.addPublication(pub_11_7);
        publicationsService.addPublication(pub_11_8);
        publicationsService.addPublication(pub_11_9);
        publicationsService.addPublication(pub_11_10);

        publicationsService.addPublication(pub_12_1);
        publicationsService.addPublication(pub_12_2);
        publicationsService.addPublication(pub_12_3);
        publicationsService.addPublication(pub_12_4);
        publicationsService.addPublication(pub_12_5);
        publicationsService.addPublication(pub_12_6);
        publicationsService.addPublication(pub_12_7);
        publicationsService.addPublication(pub_12_8);
        publicationsService.addPublication(pub_12_9);
        publicationsService.addPublication(pub_12_10);

        publicationsService.addPublication(pub_13_1);
        publicationsService.addPublication(pub_13_2);
        publicationsService.addPublication(pub_13_3);
        publicationsService.addPublication(pub_13_4);
        publicationsService.addPublication(pub_13_5);
        publicationsService.addPublication(pub_13_6);
        publicationsService.addPublication(pub_13_7);
        publicationsService.addPublication(pub_13_8);
        publicationsService.addPublication(pub_13_9);
        publicationsService.addPublication(pub_13_10);

        publicationsService.addPublication(pub_14_1);
        publicationsService.addPublication(pub_14_2);
        publicationsService.addPublication(pub_14_3);
        publicationsService.addPublication(pub_14_4);
        publicationsService.addPublication(pub_14_5);
        publicationsService.addPublication(pub_14_6);
        publicationsService.addPublication(pub_14_7);
        publicationsService.addPublication(pub_14_8);
        publicationsService.addPublication(pub_14_9);
        publicationsService.addPublication(pub_14_10);

        publicationsService.addPublication(pub_15_1);
        publicationsService.addPublication(pub_15_2);
        publicationsService.addPublication(pub_15_3);
        publicationsService.addPublication(pub_15_4);
        publicationsService.addPublication(pub_15_5);
        publicationsService.addPublication(pub_15_6);
        publicationsService.addPublication(pub_15_7);
        publicationsService.addPublication(pub_15_8);
        publicationsService.addPublication(pub_15_9);
        publicationsService.addPublication(pub_15_10);

    }

    public void reset(){
        publicationsService.deleteAllPublications();
        friendsService.deleteAllFriends();
        usersService.deleteAllUsers();

        init();
    }
}