package com.uniovi.sdientrega132.voter;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.services.FriendsService;
import com.uniovi.sdientrega132.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

public class FriendPublicationsVoter implements AccessDecisionVoter<FilterInvocation> {

    @Autowired
    private UsersService usersService;

    @Autowired
    private FriendsService friendsService;

    @Override
    public int vote(Authentication authentication, FilterInvocation filter, Collection<ConfigAttribute> attributes){
        String url = filter.getRequestUrl();
//        if (url=="/publication/list/user01@email.com") {
            String[] urlDividida = url.split("/");
            String emailFriend = urlDividida[urlDividida.length - 1];
            String emailUser = authentication.getName();

            User friend = usersService.getUserByEmail(emailFriend);
            User user = usersService.getUserByEmail(emailUser);

            Friend friendRelation = friendsService.getCoupleFriends(friend.getId(), user.getId());

            System.out.println("Petición a : " + filter.getRequestUrl());
            if (friendRelation != null) {
                return ACCESS_GRANTED;
            } else {
                return ACCESS_DENIED;
            }
//        }
//        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
