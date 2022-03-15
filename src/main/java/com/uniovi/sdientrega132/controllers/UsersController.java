package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.RolesService;
import com.uniovi.sdientrega132.services.SecurityService;
import com.uniovi.sdientrega132.services.UsersService;
import com.uniovi.sdientrega132.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private RolesService rolesService;


    @RequestMapping(value="/signup", method= RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result){
        signUpFormValidator.validate(user,result);
        if(result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:/user/list";
    }

    @RequestMapping(value="/signup", method= RequestMethod.GET)
    public String signup(Model model){
        model.addAttribute("user",new User());
        return "signup";
    }
}
