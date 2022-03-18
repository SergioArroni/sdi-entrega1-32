package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.controllers.LogsController;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends HandlerInterceptorAdapter {

    private LogsController logsController;

    public LogInterceptor(LogsController logsController) {
        this.logsController = logsController;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        System.out.println("Interceptar la petición");
        System.out.println(request.getRequestURL());
        if (request.getRequestURL().toString().equals("http://localhost:8090/login")) {
            logsController.LogOut();
        }
        // Búscar el parámetro "limite"
        Integer parametroLimite = ServletRequestUtils.getIntParameter(request, "limite", 0);
        System.out.println(parametroLimite);
        return true;
    }
}

