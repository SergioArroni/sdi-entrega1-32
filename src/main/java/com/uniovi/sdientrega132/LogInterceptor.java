package com.uniovi.sdientrega132;

import com.uniovi.sdientrega132.controllers.LogsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

//@Component
@SuppressWarnings({"deprecation", "NullableProblems"})
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LogsController logsController;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        System.out.println("Interceptar la petición");
        System.out.println(request.getRequestURL());

        System.out.println(request.getMethod());

        if (request.getRequestURL().toString().equals("http://localhost:8090/home")) {
            logsController.LogInEx();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/user/list")) {
            logsController.LogInEx();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/login?error")) {
            logsController.LogInEr();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/login?logout")) {
            logsController.LogOut();
        } else if (request.getRequestURL().toString().equals("http://localhost:8090/signup") && request.getMethod().equals("POST")) {
            StringBuilder parametros = conversor(request.getParameterNames());
            logsController.LogAlta(request.getRequestURL().toString(), request.getMethod(), parametros);
            logsController.LogPET(request.getRequestURL().toString(), request.getMethod(), parametros);
        } else {

            StringBuilder parametros = conversor(request.getParameterNames());

            logsController.LogPET(request.getRequestURL().toString(), request.getMethod(), parametros);
        }
        return true;
    }

    private StringBuilder conversor(Enumeration<String> parameterNames) {

        var iter = parameterNames.asIterator();

        StringBuilder parametros = new StringBuilder();

        while (iter.hasNext()) {
            String param = iter.next();
            parametros.append(param).append(" ");
        }
        return parametros;
    }

}

