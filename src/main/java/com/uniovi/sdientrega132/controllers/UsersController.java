package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);*/

        model.addAttribute("usersList", usersService.getUsers());
        return "user/list";
    }

    @RequestMapping("/user/list2")
    public String getListado2(Model model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);*/

        model.addAttribute("usersList", usersService.getStandardUsers());
        return "user/list2";
    }


}
