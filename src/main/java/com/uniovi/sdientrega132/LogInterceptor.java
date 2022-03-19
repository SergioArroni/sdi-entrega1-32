package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.controllers.LogsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogsController logsController;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        System.out.println("Interceptar la petición");
        System.out.println(request.getRequestURL());
        if (request.getRequestURL().toString().equals("http://localhost:8090/login")) {
            logsController.LogInEx("manolo");
        }
        else if (request.getRequestURL().toString().equals("http://localhost:8090/login?error")){
            logsController.LogInEr("manolo");
        }
        // Búscar el parámetro "limite"
        Integer parametroLimite = ServletRequestUtils.getIntParameter(request, "limite", 0);
        System.out.println(parametroLimite);
        return true;
    }


}

