package com.uniovi.sdientrega132.controllers;


import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.services.LogService;
import com.uniovi.sdientrega132.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Iterator;

@Controller
public class LogsController {
    @Autowired
    private LogsService logsService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String emailLogin;

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

    public void LogInEx() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailLogin = auth.getName();
        Log l = new Log("LOGIN-EX", Timestamp.from(Instant.now()).toString(), emailLogin);
        System.out.println(l);
        logsService.addlog(l);
    }

    public void LogOut() {
        Log l = new Log("LOGOUT", Timestamp.from(Instant.now()).toString(), emailLogin);
        emailLogin=null;
        System.out.println(l);
        logsService.addlog(l);
    }

    public void LogInEr() {

        Log l = new Log("LOGIN-ERR", Timestamp.from(Instant.now()).toString(), userDetailsService.getEmailInvalid());
        System.out.println(l);
        logsService.addlog(l);
    }

    public void LogPET(String pathInfo, String method, Enumeration<String> parameterNames) {

        var iter = parameterNames.asIterator();

        String parametros = "";

        for (Iterator<String> it = iter; it.hasNext(); ) {
            String param = it.next();
            parametros += param + " ";
        }

        Log l = new Log("PET", Timestamp.from(Instant.now()).toString(), pathInfo + "\t" + method + "\t" + parametros);
        System.out.println(l);
        logsService.addlog(l);
    }

    public void LogAlta(String pathInfo, String method, Enumeration<String> parameterNames) {

        var iter = parameterNames.asIterator();

        String parametros = "";

        for (Iterator<String> it = iter; it.hasNext(); ) {
            String param = it.next();
            parametros += param + " ";
        }

        Log l = new Log("ALTA", Timestamp.from(Instant.now()).toString(), pathInfo + "\t" + method + "\t" + parametros);
        System.out.println(l);
        logsService.addlog(l);
    }

}
