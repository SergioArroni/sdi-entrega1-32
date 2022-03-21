package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.FriendsForAll;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.LogsService;
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
import java.util.List;

@SuppressWarnings({"SpringJavaStaticMembersAutowiringInspection", "SpringMVCViewInspection"})
@Controller
public class FriendsController {
    @Autowired
    private FriendsService friendsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    public static LogsService logService;

    @RequestMapping("/friend/list")
    public String getListRealFriendsByUser(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = friendsService.getFriendByUser(pageable, activeUser.getId());
        if (friends != null)
            CodeAuxFriends(model, friends);
        return "friend/list";
    }

    @RequestMapping("/friend/invitation")
    public String getListByUser(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = friendsService.getInvitationsByUser1_id(pageable, activeUser.getId());
        CodeAuxFriends(model, friends);
        return "friend/invitation";
    }

    @RequestMapping(value = "/friend/{id}/accept", method = RequestMethod.GET)
    public String setResendTrue(@PathVariable Long id) {
        friendsService.setFriendInvitationSend(true, id);
        return "redirect:/friend/invitation";
    }

    @RequestMapping(value = "/friend/{id}/noaccept", method = RequestMethod.GET)
    public String setResendFalse(@PathVariable Long id) {
        friendsService.setFriendInvitationSend(false, id);
        return "redirect:/friend/invitation";
    }

    @RequestMapping("/friend/invitation/update")
    public String updateInvitationList(Model model, Pageable pageable) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = friendsService.getInvitationsByUser1_id(pageable, activeUser.getId());
        CodeAuxFriends(model, friends);
        System.out.println("a");
        return "friend/invitation :: tableFriends";
    }

    @RequestMapping("/friend/send/{userId2}")
    public String sendInvitationTo(@PathVariable Long userId2) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        if (friendsService.getCoupleFriends(activeUser.getId(), userId2) == null
                && friendsService.getCoupleFriends(userId2, activeUser.getId()) == null) {
            friendsService.addFriend(new Friend(userId2, activeUser.getId(), false));
        }
        return "redirect:/user/list";
    }

    private void CodeAuxFriends(Model model, Page<Friend> friends) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        List<FriendsForAll> amigosDeVerdad = new ArrayList<>();
        for (Friend friend : friends) {
            User amigo;
            if (friend.getUser2_id() != activeUser.getId())
                amigo = usersService.getUser(friend.getUser2_id());
            else
                amigo = usersService.getUser(friend.getUser1_id());
            if (amigo != null)
                amigosDeVerdad.add(new FriendsForAll(friend, amigo));
        }

        Page<FriendsForAll> userAux = new PageImpl<>(amigosDeVerdad);

        model.addAttribute("page", friends);
        model.addAttribute("friendsForAll", userAux);
    }

    @RequestMapping("/friend/list/update")
    public String updateListList(Model model, Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);
        Page<Friend> friends = friendsService.getFriendByUser(pageable, activeUser.getId());

        CodeAuxFriends(model, friends);
        return "friend/list :: tableFriends";
    }

}
