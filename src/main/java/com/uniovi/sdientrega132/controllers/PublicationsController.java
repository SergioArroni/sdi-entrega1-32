package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.PublicationsService;
import com.uniovi.sdientrega132.services.UsersService;
import com.uniovi.sdientrega132.validators.PublicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.UUID;

@Controller
public class PublicationsController {

    @Autowired
    private PublicationValidator publicationValidator;

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("/publication/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(value = "", required = false) String searchText) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Publication> publications;
        if (user.getRole().equals("ROLE_ADMIN")) {
            if (searchText != null && !searchText.isEmpty())
                publications = publicationsService.searchPublications(searchText, pageable);
            else
                publications = publicationsService.getPublications(pageable);
        } else {
            publications = publicationsService.getPublicationsForUser(pageable, user);
        }

        model.addAttribute("publicationsList", publications.getContent());
        model.addAttribute("page", publications);

        return "publication/list";
    }

    @RequestMapping("/publication/list/{email}")
    public String getFriendsList(Model model, Pageable pageable, @PathVariable String email) {
        User user = usersService.getUserByEmail(email);
        Page<Publication> publications;
        publications = publicationsService.getPublicationsForFriend(pageable, user);

        model.addAttribute("publicationsList", publications.getContent());
        model.addAttribute("page", publications);

        return "publication/list";
    }

    @RequestMapping("/publication/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Publication> publications = publicationsService.getPublicationsForUser(pageable, user);
        model.addAttribute("publicationsList", publications.getContent() );
        return "publication/list :: tablePublications";
    }

    @RequestMapping(value = "/publication/add", method = RequestMethod.POST)
    public String setPublication(@Validated Publication publication,
                                 @RequestParam("file")MultipartFile imagen, BindingResult result, Model model,
                                 Principal principal) {
        publication.setState("ACEPTADA");
        publicationValidator.validate(publication, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            return "publication/add";
        }

        publication.setPublishingDate(new Date());
        if (!imagen.isEmpty()) {
            String ruta = "C://Productos";

            try {
                byte[] bytes = imagen.getBytes();
                String nombreImagen = UUID.randomUUID()+".png";
                Path rutaCompleta = Paths.get(ruta + "//" + nombreImagen);
                Files.write(rutaCompleta, bytes);

                publication.setPhoto(nombreImagen);
            } catch (IOException e) {
                System.out.println("Fallo con la imagen");
                e.printStackTrace();
            }

        }

        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        publication.setUser(user);

        publicationsService.addPublication(publication);
        return "redirect:/publication/list";
    }

    @RequestMapping(value = "/publication/add")
    public String getPublication(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("publication", new Publication());
        return "publication/add";
    }

    @RequestMapping(value = "/publication/edit")
    public String editState(@RequestParam(required = false) Long id,
                          @RequestParam(required = false) String state) {
        System.out.println("Modificado estado "+state);
        publicationsService.editStateOf(id, state);

        return "redirect:/publication/list";
    }

}
