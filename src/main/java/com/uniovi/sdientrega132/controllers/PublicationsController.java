package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.PublicationsService;
import com.uniovi.sdientrega132.services.UsersService;
import com.uniovi.sdientrega132.validators.PublicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.LinkedList;
import java.util.UUID;

@Controller
public class PublicationsController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PublicationValidator publicationValidator;

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("/publication/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Publication> publications = new PageImpl<>(new LinkedList<Publication>());
        if (user.getRole().equals("ROLE_ADMIN")) {
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
        Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
        publications = publicationsService.getPublicationsForUser(pageable, user);

        model.addAttribute("publicationsList", publications.getContent());
        model.addAttribute("page", publications);

        return "publication/list";
    }

    @RequestMapping("/publication/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Publication> marks = publicationsService.getPublicationsForUser(pageable, user);
        model.addAttribute("publicationsList", marks.getContent() );
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
//            Path directorio = Paths.get("src//main//resources//static//images");
//            String ruta = directorio.toFile().getAbsolutePath();
            String ruta = "C://Productos";

            try {
                byte[] bytes = imagen.getBytes();
                String nombreImagen = UUID.randomUUID()+".png";
                Path rutaCompleta = Paths.get(ruta + "//" + nombreImagen);
                Files.write(rutaCompleta, bytes);

                publication.setFoto(nombreImagen);
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

//    public void handleFileUpload(FileUpload event) throws IOException {
//
//    }

}
