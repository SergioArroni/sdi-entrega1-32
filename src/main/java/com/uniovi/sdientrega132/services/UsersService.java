package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    /*@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public List<User> getStandardUsers() {
        List<User> users = new ArrayList<User>();
        for (User user : getUsers()){
            if (!user.getRole().equals("ROLE_ADMIN")) {
                users.add(user);
            }
        }
        return users;
    }

    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public void addUser(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

}
