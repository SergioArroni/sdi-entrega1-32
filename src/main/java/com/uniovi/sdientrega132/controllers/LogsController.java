package com.uniovi.sdientrega132.controllers;
import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.LogsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LogsController {
    @Autowired
    private LogsService logsService;
    @Autowired
    private UsersService usersService;

    @RequestMapping("/logs/list")
    public String getListado(Model model) {
        //Comprobamos si es admin por si intentan acceder por la URL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            List<Log> logs = new ArrayList<>();
            logs = logsService.getLogs();
            model.addAttribute("logsList", logs);
            return "logs/list";
        } else {
            return "home";
        }
    }

    @RequestMapping("/logs/list/update")
    public String updateList(Model model, Pageable pageable) {
        //Comprobamos si es admin por si intentan acceder por la URL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            List<Log> logs = logsService.getLogs();
            model.addAttribute("logsList", logs);
            return "user/list :: tableLogs";
        } else {
            return "home";
        }
    }


    @RequestMapping("/logs/deleteAll")
    public String delete() {
        //Comprobamos si es admin por si intentan acceder por la URL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            logsService.deleteAllLogs();
            return "redirect:/logs/list";
        } else {
            return "home";
        }
    }

}
