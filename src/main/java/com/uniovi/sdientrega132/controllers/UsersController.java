package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            model.addAttribute("usersList", usersService.getUsers());
        } else {
            model.addAttribute("usersList", usersService.getStandardUsers(activeUser));
        }
        return "user/list";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }



}
