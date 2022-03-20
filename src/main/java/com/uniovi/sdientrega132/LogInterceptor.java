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

        System.out.println(request.getMethod());

        if (request.getRequestURL().toString().equals("http://localhost:8090/login")) {
            logsController.LogInEx();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/login?error")) {
            logsController.LogInEr();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/signup") && request.getMethod().equals("POST")) {
            logsController.LogAlta(request.getRequestURL().toString(), request.getMethod(), request.getParameterNames());
            logsController.LogPET(request.getRequestURL().toString(), request.getMethod(), request.getParameterNames());
        } else {
            logsController.LogPET(request.getRequestURL().toString(), request.getMethod(), request.getParameterNames());
        }
        return true;
    }
}

