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
import java.util.List;

@Controller
public class LogsController {
    @Autowired
    private LogService logService;

    public void LogInEx(String username) {
        Log l = new Log("LOGIN-EX", Timestamp.from(Instant.now()).toString(), username);
        System.out.println(l);
        logService.addlog(l);
    }

    public void LogInEr(String username) {
        Log l = new Log("LOGIN-ERR", Timestamp.from(Instant.now()).toString(), username);
        System.out.println(l);
        logService.addlog(l);
    }

}
