package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.FriendsForAll;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class FriendsController {
    @Autowired
    private FriendsService FriendsService;

    @Autowired
    private UsersService usersService;

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


    @RequestMapping("/friends/list/{user_id}")
    public String getListRealFriendsByUser(Model model, Pageable pageable, @PathVariable long user_id) {
        Page<Friend> friends = FriendsService.getFriendByUser1(pageable, user_id);
        List<FriendsForAll> amigosDeVerdad = new ArrayList<FriendsForAll>();
        for (Friend friend : friends) {
            User amigo = usersService.getUser(friend.getUser2_id());
            if (amigo != null)
                amigosDeVerdad.add(new FriendsForAll(friend, amigo));
        }
        friends = FriendsService.getFriendByUser2(pageable, user_id);
        for (Friend friend : friends) {
            User amigo = usersService.getUser(friend.getUser1_id());
            if (amigo != null)
                amigosDeVerdad.add(new FriendsForAll(friend, amigo));
        }
        Page<FriendsForAll> userAux = new PageImpl<FriendsForAll>(amigosDeVerdad);
        model.addAttribute("friendsForAll", userAux.getContent());
        model.addAttribute("page", userAux);
        return "friends/list";
    }

    @RequestMapping("/friends/invitation/{user_id}")
    public String getListByUser(Model model, Pageable pageable, @PathVariable long user_id) {
        Page<Friend> friends = FriendsService.getInvitationsByUser1_id(pageable, user_id);
        List<FriendsForAll> amigosDeVerdad = new ArrayList<FriendsForAll>();
        for (Friend friend : friends) {
            User amigo = usersService.getUser(friend.getUser2_id());
            if (amigo != null)
                amigosDeVerdad.add(new FriendsForAll(friend, amigo));
        }
        Page<FriendsForAll> userAux = new PageImpl<FriendsForAll>(amigosDeVerdad);
        model.addAttribute("friendsForAll", userAux.getContent());
        model.addAttribute("page", userAux);
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

    @RequestMapping("/friend/invitation/update")
    public String updateInvitationList(Model model, Pageable pageable, Principal principal) {
        //String dni = principal.getName(); // Obtenemos el DNI del Usuario autenticado
        //User user = userService.getUserByDni(dni);
        // Page<Friend> frieds = FriendsService.getFriendByUser1(pageable, 1L);
        // model.addAttribute("FriendsList", frieds.getContent());
        return "friends/invitation :: tableFriends";
    }


}
