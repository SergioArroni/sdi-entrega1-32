package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.PublicationsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;

@Controller
public class PublicationsController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PublicationsService publicationsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("/publication/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        String username = principal.getName();
        User user = usersService.getUserByUsername(username);
        Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
        publications = publicationsService.getPublicationsForUser(pageable, user);

        model.addAttribute("publicationList", publications.getContent());
        model.addAttribute("page", publications);

        return "publication/list";
    }

    @RequestMapping(value = "/publication/add", method = RequestMethod.POST)
    public String setMark(@Validated Publication publication, BindingResult result, Model model) {
        publicationsService.addPublication(publication);
        return "redirect:/publication/list";
    }

    @RequestMapping(value = "/publication/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("publication", new Publication());
        return "publication/add";
    }

}
