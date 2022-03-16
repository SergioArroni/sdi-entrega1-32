package com.uniovi.sdientrega132.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_STANDARDUSER", "ROLE_ADMIN"};

    public String[] getRoles() {
        return roles;
    }
}
