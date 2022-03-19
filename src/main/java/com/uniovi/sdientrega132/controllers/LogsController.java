package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.FriendsForAll;
import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.LogService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Controller
public class LogsController {

    @Autowired
    private LogService logService;

    @Autowired
    private UsersService usersService;

    public void LogInEx() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Log l = new Log("LOGIN-EX", Timestamp.from(Instant.now()).toString(), email);
        System.out.println(l);
        logService.addlog(l);
    }

    public void LogOut() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Log l = new Log("LOGOUT", Timestamp.from(Instant.now()).toString(), email);
        System.out.println(l);
        logService.addlog(l);
    }

    public void LogInEr() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Log l = new Log("LOGIN-ERR", Timestamp.from(Instant.now()).toString(), email);
        System.out.println(l);
        logService.addlog(l);
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
        logService.addlog(l);
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
        logService.addlog(l);
    }
}
