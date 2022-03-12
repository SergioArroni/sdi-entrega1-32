package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.LinkedList;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/user/list")
    public String getListado(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<User> users = new PageImpl<>(new LinkedList<>());
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            users = usersService.getUsers(pageable);
        } else {
            users = usersService.getStandardUsers(activeUser,pageable);
        }
        model.addAttribute("usersList",users.getContent());
        model.addAttribute("page",users);
        return "user/list";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName(); // Email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        model.addAttribute("usersList", usersService.getUsers(pageable) );
        return "user/list :: tableUsers";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }



}
