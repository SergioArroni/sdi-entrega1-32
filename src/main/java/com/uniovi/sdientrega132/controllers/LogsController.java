package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.LogsService;
import com.uniovi.sdientrega132.services.UserDetailsServiceImpl;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Enumeration;
import java.util.List;

@Controller
public class LogsController {
    @Autowired
    private LogsService logsService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UsersService usersService;

    private String emailLogin;

    @RequestMapping("/logs/list")
    public String getListado(Model model) {
        //Comprobamos si es admin por si intentan acceder por la URL
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (activeUser.getRole().equals("ROLE_ADMIN")) {
            List<Log> logs = logsService.getLogs();
            model.addAttribute("logsList", logs);
            return "logs/list";
        } else {
            return "home";
        }
    }

    @SuppressWarnings("SpringMVCViewInspection")
    @RequestMapping("/logs/list/update")
    public String updateList(Model model) {
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

    public void LogInEx() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailLogin = auth.getName();
        Log l = new Log("LOGIN-EX", Timestamp.from(Instant.now()).toString(), emailLogin);
        System.out.println(l);
        logsService.addLog(l);
    }

    public void LogOut() {
        Log l = new Log("LOGOUT", Timestamp.from(Instant.now()).toString(), emailLogin);
        emailLogin = null;
        System.out.println(l);
        logsService.addLog(l);
    }

    public void LogInEr() {

        Log l = new Log("LOGIN-ERR", Timestamp.from(Instant.now()).toString(), userDetailsService.getEmailInvalid());
        System.out.println(l);
        logsService.addLog(l);
    }

    public void LogPET(String pathInfo, String method, Enumeration<String> parameterNames) {

        var iter = parameterNames.asIterator();

        StringBuilder parametros = new StringBuilder();

        while (iter.hasNext()) {
            String param = iter.next();
            parametros.append(param).append(" ");
        }

        Log l = new Log("PET", Timestamp.from(Instant.now()).toString(), pathInfo + "\t" + method + "\t" + parametros);
        System.out.println(l);
        logsService.addLog(l);
    }

    public void LogAlta(String pathInfo, String method, Enumeration<String> parameterNames) {

        var iter = parameterNames.asIterator();

        StringBuilder parametros = new StringBuilder();

        while (iter.hasNext()) {
            String param = iter.next();
            parametros.append(param).append(" ");
        }

        Log l = new Log("ALTA", Timestamp.from(Instant.now()).toString(), pathInfo + "\t" + method + "\t" + parametros);
        System.out.println(l);
        logsService.addLog(l);
    }

}
