package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.RolesService;
import com.uniovi.sdientrega132.services.SecurityService;
import com.uniovi.sdientrega132.services.UsersService;
import com.uniovi.sdientrega132.validators.SignUpFormValidator;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SignUpFormValidator signUpFormValidator;
    @Autowired
    private RolesService rolesService;

    @Autowired
    private LogsController logsController;

    @Autowired
    private FriendsService friendsService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);
        if (result.hasErrors()) {
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        return "redirect:/home";
    }


    @RequestMapping("/user/list")
    public String getListado(Model model, Pageable pageable,
                             @RequestParam(value = "", required = false) String searchText) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);

        List<User> listUsers = new ArrayList<>();
        Page<User> users = new PageImpl<>(new LinkedList<>());
        Set<Long> usersFriends = new HashSet<>();

        if (searchText != null && !searchText.isEmpty())
            users = usersService.searchUserByEmailAndName(searchText, activeUser, pageable);
        else {
            if (activeUser.getRole().equals("ROLE_ADMIN")) {
                listUsers = usersService.getUsers();
            } else {
                users = usersService.getStandardUsers(activeUser, pageable);
            }
        }
        AuxCodeList(model, activeUser, users, listUsers);

        var friends = friendsService.getFriends(activeUser.getId());

        List<Long> friendId = new ArrayList<>();

        for (Friend u : friends) {
            System.out.println(u);
            friendId.add(u.getUser1_id());
        }

        model.addAttribute("actualUser", activeUser);
        model.addAttribute("usersListFriends", friendId);
        return "user/list";
    }

    @RequestMapping("/user/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName(); // Email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Page<User> users = new PageImpl<>(new LinkedList<>());
        List<User> listUsers = new ArrayList<>();
        Set<Long> usersFriends = new HashSet<>();
        if (user.getRole().equals("ROLE_ADMIN")) {
            listUsers = usersService.getUsers();
        } else {
            users = usersService.getStandardUsers(user, pageable);
        }
        AuxCodeList(model, user, users, listUsers);

        var friends = friendsService.getFriends(user.getId());

        model.addAttribute("usersListFriends", friends);
        return "user/list :: tableUsers";
    }

    private void AuxCodeList(Model model, User user, Page<User> users, List<User> listUsers) {
        if (listUsers.isEmpty()) {
            model.addAttribute("usersList", users.getContent());
            model.addAttribute("page", users);
        } else {
            model.addAttribute("usersList", listUsers);
        }
    }

    @GetMapping("/user/delete")
    public String delete(@RequestParam(value = "", required = false) List<Long> ids) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        ids.remove(activeUser.getId());
        usersService.deleteUsers(ids);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "");
            logsController.LogInEr();
        }
        if (logout != null) {
            model.addAttribute("message", "");
            logsController.LogOut();
        }
        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

}
