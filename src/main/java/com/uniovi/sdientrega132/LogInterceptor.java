package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.controllers.LogsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
@SuppressWarnings({"deprecation", "NullableProblems"})
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogsController logsController;

    private boolean point = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        System.out.println("Interceptar la petición");
        System.out.println(request.getRequestURL());

        System.out.println(request.getMethod());

        if (request.getRequestURL().toString().equals("http://localhost:8090/login")) {
            point = true;
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/home") && point) {
            point = false;
            logsController.LogInEx();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/user/list") && point) {
            point = false;
            logsController.LogInEx();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/login?error")) {
            logsController.LogInEr();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/login?logout")) {
            logsController.LogOut();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/signup") && request.getMethod().equals("POST")) {
            point = true;
            logsController.LogAlta(request.getRequestURI(), request.getMethod(), request.getParameterNames());
            logsController.LogPET(request.getRequestURI(), request.getMethod(), request.getParameterNames());
        } else {
            logsController.LogPET(request.getRequestURI(), request.getMethod(), request.getParameterNames());
        }
        return true;
    }
}

