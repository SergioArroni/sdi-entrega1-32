package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.services.LogService;
import com.uniovi.sdientrega132.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Iterator;

@Controller
public class LogsController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String emailLogin;

    public void LogInEx() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailLogin = auth.getName();
        Log l = new Log("LOGIN-EX", Timestamp.from(Instant.now()).toString(), emailLogin);
        System.out.println(l);
        logService.addlog(l);
    }

    public void LogOut() {
        Log l = new Log("LOGOUT", Timestamp.from(Instant.now()).toString(), emailLogin);
        emailLogin=null;
        System.out.println(l);
        logService.addlog(l);
    }

    public void LogInEr() {

        Log l = new Log("LOGIN-ERR", Timestamp.from(Instant.now()).toString(), userDetailsService.getEmailInvalid());
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
