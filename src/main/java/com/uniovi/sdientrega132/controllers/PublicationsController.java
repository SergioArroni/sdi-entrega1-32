package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.PublicationsService;
import com.uniovi.sdientrega132.services.UsersService;
import com.uniovi.sdientrega132.validators.PublicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

@Controller
public class PublicationsController {

    @Autowired
    private PublicationValidator publicationValidator;

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private FriendsService friendsService;

    @RequestMapping("/publication/list")
    public String getList(Model model, Pageable pageable, Principal principal,
                          @RequestParam(value = "", required = false) String searchText) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Publication> publications = new PageImpl<>(new LinkedList<Publication>());
        if (user.getRole().equals("ROLE_ADMIN")) {
            if (searchText != null && !searchText.isEmpty())
                publications = publicationsService.searchPublications(searchText, pageable);
            else
                publications = publicationsService.getPublications(pageable);
        } else {
            publications = publicationsService.getPublicationsForUser(pageable, user);
        }

        model.addAttribute("publicationsRecommended", publications.getContent());
        //model.addAttribute("publicationsNotRecommended", new ArrayList<Publication>());
        model.addAttribute("page", publications);

        return "publication/list";
    }

    @RequestMapping("/publication/listFriend/{email}")
    public String getFriendsList(Model model, Pageable pageable, Principal principal, @PathVariable String email) {
        String emailAut = principal.getName();
        User userAut = usersService.getUserByEmail(emailAut);
        User user = usersService.getUserByEmail(email);

        Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
        publications = publicationsService.getPublicationsForUser(pageable, user);
        if(friendsService.getCoupleFriends(userAut.getId(), user.getId()) == null &&
                friendsService.getCoupleFriends(user.getId(), userAut.getId()) == null){
            model.addAttribute("publicationsNotRecommended", new ArrayList<Publication>());
            model.addAttribute("publicationsRecommended", new ArrayList<Publication>());
            model.addAttribute("page", publications);
        }
        else {
            List<Publication> publicactionRecommended = new ArrayList<Publication>();
            List<Publication> publicactionNotRecommended = new ArrayList<Publication>();

            for (Publication pub : publications) {
                if (pub.getRecomendaciones().contains(userAut)) {
                    publicactionRecommended.add(pub);
                } else {
                    publicactionNotRecommended.add(pub);
                }
            }
            Page<Publication> publicationsRecommended = new PageImpl<Publication>(publicactionRecommended);
            Page<Publication> publicationsNotRecommended = new PageImpl<Publication>(publicactionNotRecommended);
            model.addAttribute("publicationsNotRecommended", publicationsNotRecommended.getContent());
            model.addAttribute("publicationsRecommended", publicationsRecommended.getContent());
            model.addAttribute("page", publications);
        }

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

    @RequestMapping("/publication/listFriend/update/{email}")
    public String updateFriendList(Model model, Pageable pageable, Principal principal, @PathVariable String email){
        return getFriendsList(model, pageable, principal, email);
    }

    @RequestMapping(value = "/publication/add", method = RequestMethod.POST)
    public String setPublication(@Validated Publication publication,
                                 @RequestParam("file")MultipartFile imagen, BindingResult result, Model model,
                                 Principal principal) {
        publication.setState("Aceptada");
        publicationValidator.validate(publication, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            return "publication/add";
        }

        publication.setPublishingDate(new Date());

        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        publication.setUser(user);

        publicationsService.addPublication(publication);

        if (!imagen.isEmpty()) {
//            Path directorio = Paths.get("src//main//resources//static//images");
//            String ruta = directorio.toFile().getAbsolutePath();
            String ruta = "C://Productos";

            try {
                byte[] bytes = imagen.getBytes();
                String nombreImagen = publication.getId()+".png";
                Path rutaCompleta = Paths.get(ruta + "//" + nombreImagen);
                Files.write(rutaCompleta, bytes);

                publication.setPhoto(publication.getId()+"");

                publicationsService.addPublication(publication);
            } catch (IOException e) {
                System.out.println("Fallo con la imagen");
                e.printStackTrace();
            }

        }

        return "redirect:/publication/list";
    }

    @RequestMapping(value = "/publication/add")
    public String getPublication(Model model, Pageable pageable) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("publication", new Publication());
        return "publication/add";
    }

    @RequestMapping(value = "/publication/edit")
    public String editState(@RequestParam(value = "", required = false) Long id,
                          @RequestParam(value = "", required = false) String state) {
        System.out.println("Modificado estado "+state);
        publicationsService.editStateOf(id, state);

        return "redirect:/publication/list";
    }

    @RequestMapping("/publication/{pubId2}/recommend")
    public String recommendPublication(@PathVariable Long pubId2) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Publication pub = publicationsService.getPublication(pubId2);
        if(!pub.getRecomendaciones().contains(activeUser) && !pub.getUser().equals(activeUser)){
            pub.getRecomendaciones().add(activeUser);
            publicationsService.addPublication(pub);
        }
        return "redirect:/publication/listFriend/" + pub.getUser().getEmail();
    }

//    public void handleFileUpload(FileUpload event) throws IOException {
//
//    }





}
