package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection"})
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Page<User> getStandardUsers(User user, Pageable pageable) {
        return usersRepository.findAllStandard(pageable, user);
    }

    public List<User> getStandardUsers(User user) {
        return usersRepository.findAllStandard(user);
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public Page<User> searchUserByEmailAndName(String searchText, User user, Pageable pageable) {
        Page<User> users = new PageImpl<>(new LinkedList<>());
        searchText = "%" + searchText + "%";
        if (user.getRole().equals("ROLE_USER")) {
            users = usersRepository.searchByEmailAndName(pageable, searchText);
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            users = usersRepository.searchByEmailNameAndSurnames(pageable, searchText);
        }
        return users;
    }

    public void deleteUsers(List<Long> ids) {
        usersRepository.deleteAllById(ids);
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public void deleteAllUsers() {
        usersRepository.deleteAll();
    }
}
