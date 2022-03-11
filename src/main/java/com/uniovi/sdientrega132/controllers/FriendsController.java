package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class FriendsController {
    @Autowired
    private FriendsService FriendsService;

    @Autowired
    private UsersService usersService;

    /**
     * @Autowired private FriendValidator FriendValidator;
     */
    @RequestMapping("/friends/invitation/{user_id}")
    public String getListByUser(Model model, @PathVariable long user_id) {
        List<Friend> friends = FriendsService.getInvitationsByUser1_id(user_id);
        List<User> users = new ArrayList<User>();
        for(Friend friend : friends){
            User amigo = usersService.getUser(friend.getUser2_id());
            if(amigo != null)
                users.add(amigo);
        }
        model.addAttribute("friendsList", users);
        return "friends/invitation";
    }

    @RequestMapping("/friend/list")
    public String getList(Model model) {
        model.addAttribute("friendsList", FriendsService.getFriends());
        return "friend/list";
    }

    @RequestMapping(value = "/friend/add")
    public String getFriend(Model model) {
        model.addAttribute("friend", new Friend());
        return "friend/add";
    }

    @RequestMapping("/friends/invitation/{user_id}")
    public String getListByUser(Model model, Pageable pageable, @PathVariable long user_id) {
        Page<Friend> friends = FriendsService.getFriendByUser1(pageable, user_id);
        List<User> users = new ArrayList<User>();
        for (Friend friend : friends) {
            User amigo = userService.getUser(friend.getUser2_id());
            if (amigo != null)
                users.add(amigo);
        }
        model.addAttribute("friendsList", users);
        return "friends/invitation";
    }

    @RequestMapping(value = "/friend/add", method = RequestMethod.POST)
    public String setFriend(@ModelAttribute Friend friend) {

        FriendsService.addFriend(friend);
        return "redirect:/friend/list";
    }

    @RequestMapping("/friend/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("friend", FriendsService.getFriend(id));
        return "friend/details";
    }

    @RequestMapping("/friend/delete/{id}")
    public String deleteFriend(@PathVariable Long id) {
        FriendsService.deleteFriend(id);
        return "redirect:/friend/list";
    }

    @RequestMapping(value = "/friend/{id}/accept", method = RequestMethod.GET)
    public String setResendTrue(Model model, @PathVariable Long id) {
        FriendsService.setFriendInvitationSend(true, id);
        return "redirect:/friends/invitation";
    }

    @RequestMapping(value = "/friend/{id}/noaccept", method = RequestMethod.GET)
    public String setResendFalse(Model model, @PathVariable Long id) {
        FriendsService.setFriendInvitationSend(false, id);
        return "redirect:/friends/invitation";
    }
/**
 @RequestMapping("/friends/invitation/update") public String updateInvitationList(Model model, Pageable pageable, Principal principal) {
 String dni = principal.getName(); // Obtenemos el DNI del Usuario autenticado
 User user = userService.getUserByDni(dni);
 Page<Friend> frieds = FriendsService.getFriendByUser1(pageable, user.getId());
 model.addAttribute("FriendsList", frieds.getContent());
 return "friends/invitation :: tableFriends";
 }
 */
}
