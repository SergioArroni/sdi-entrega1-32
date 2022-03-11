package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicationsService {
    @Autowired
    private PublicationsRepository publicationsRepository;
}
