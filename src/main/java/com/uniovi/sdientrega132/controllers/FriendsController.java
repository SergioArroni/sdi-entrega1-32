package com.uniovi.sdientrega132.controllers;

import com.uniovi.sdientrega132.entities.Friend;
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

@Controller
public class FriendsController {
    @Autowired
    private FriendsService FriendsService;

    /**
     * @Autowired private FriendValidator FriendValidator;
     */
    @RequestMapping("/friend/list")
    public String getList(Model model) {
        model.addAttribute("friendsList", FriendsService.getFriends());
        return "friend/list";
    }

    @RequestMapping(value = "/friend/add")
    public String getProfessor(Model model) {
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
