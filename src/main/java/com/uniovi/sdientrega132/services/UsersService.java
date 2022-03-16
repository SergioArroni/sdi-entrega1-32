package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public User getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

}
