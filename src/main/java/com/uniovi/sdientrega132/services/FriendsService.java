package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
//import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;


    public Page<Friend> getFriends(Pageable pageable) {
        return friendsRepository.findAll(pageable);
    }

    public Page<Friend> getInvitationsByUser1_id(Pageable pageable, long user1_id) {
        Page<Friend> friends = friendsRepository.findInvitationsByUser1(pageable, user1_id);
        return friends;
    }

    public Friend getFriend(Long id) {
        return friendsRepository.findById(id).get();
    }

    public Page<Friend> getFriendByUser2(Pageable pageable, Long User2_id) {

        return friendsRepository.friendsUser2(pageable, User2_id);
    }

    public Page<Friend> getFriendByUser(Pageable pageable, Long User1_id) {
        return friendsRepository.friendsUser1(pageable, User1_id);
    }

    public Friend getCoupleFriends(Long User1_id, Long User2_id) {
        return friendsRepository.findCoupleFriends(User1_id, User2_id);
    }

    public void addFriend(Friend friend) {
        friendsRepository.save(friend);
    }

    public void deleteFriend(Long id) {
        friendsRepository.deleteById(id);
    }

    public void setFriendInvitationSend(boolean revised, Long id) {
        friendsRepository.updateResend(revised, id);
    }

}
