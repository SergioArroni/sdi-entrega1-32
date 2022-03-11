package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/friend/add")
    public String getFriend(Model model) {
        model.addAttribute("friend", new Friend());
        return "friend/add";
    }

    @RequestMapping(value = "/friend/add", method = RequestMethod.POST)
    public String setFriend(@Validated Friend friend, BindingResult result) {
        /**
         professorValidator.validate(friend, result);
         if(result.hasErrors()){
         return "friend/add";
         }
         */
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

}
