package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.services.PublicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PublicationsController {
    @Autowired
    private PublicationsService publicationsService;

    public void handleFileUpload(FileUpload event) throws IOException

}
