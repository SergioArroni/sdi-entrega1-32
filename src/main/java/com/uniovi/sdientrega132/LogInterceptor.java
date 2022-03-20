package com.uniovi.sdientrega132;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        System.out.println("Interceptar la petición");
        // Búscar el parámetro "limite"
        Integer parametroLimite = ServletRequestUtils.getIntParameter(request, "limite", 0);
        return true;
    }
}

