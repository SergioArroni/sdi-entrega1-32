package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.CustomConfiguration;
import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.FriendsForAll;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class FriendsController {
    @Autowired
    private FriendsService FriendsService;

    @Autowired
    private UsersService usersService;

    @RequestMapping("/friend/list")
    public String getListRealFriendsByUser(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = FriendsService.getFriendByUser1(pageable, activeUser.getId());
        CodeAuxFriends(model, friends, pageable);
        return "friend/list";
    }

    @RequestMapping("/friend/invitation")
    public String getListByUser(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = FriendsService.getInvitationsByUser1_id(pageable, activeUser.getId());
        CodeAuxFriends(model, friends, pageable);
        return "friend/invitation";
    }

    @RequestMapping(value = "/friend/{id}/accept", method = RequestMethod.GET)
    public String setResendTrue(Model model, @PathVariable Long id) {
        FriendsService.setFriendInvitationSend(true, id);
        return "redirect:/friend/invitation";
    }

    @RequestMapping(value = "/friend/{id}/noaccept", method = RequestMethod.GET)
    public String setResendFalse(Model model, @PathVariable Long id) {
        FriendsService.setFriendInvitationSend(false, id);
        return "redirect:/friend/invitation";
    }

    @RequestMapping("/friend/invitation/update")
    public String updateInvitationList(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = FriendsService.getInvitationsByUser1_id(pageable, activeUser.getId());
        CodeAuxFriends(model, friends, pageable);
        return "friend/invitation :: tableFriends";
    }

    private void CodeAuxFriends(Model model, Page<Friend> friends, Pageable pageable) {
        List<FriendsForAll> amigosDeVerdad = new ArrayList<FriendsForAll>();
        for (Friend friend : friends) {
            User amigo = usersService.getUser(friend.getUser2_id());
            if (amigo != null)
                amigosDeVerdad.add(new FriendsForAll(friend, amigo));
        }
        Page<FriendsForAll> userAux = new PageImpl<FriendsForAll>(amigosDeVerdad);

        Page<User> users = new PageImpl<>(new LinkedList<>());

        users = usersService.getUsers(pageable);

        model.addAttribute("page", users);
        model.addAttribute("friendsForAll", userAux);
    }
}
