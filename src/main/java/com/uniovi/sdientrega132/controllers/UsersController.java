package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;


}
