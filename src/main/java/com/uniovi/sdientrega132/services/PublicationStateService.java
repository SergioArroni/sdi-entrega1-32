package com.uniovi.sdientrega132.services;

import org.springframework.stereotype.Service;

@Service
public class PublicationStateService {
    String[] states = {"ACEPTADA", "MODERADA", "CENSURADA"};
    public String[] getStates() {
        return states;
    }

}
