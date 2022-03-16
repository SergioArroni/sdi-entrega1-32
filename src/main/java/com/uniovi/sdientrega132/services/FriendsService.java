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


    public List<Friend> getFriends() {
        List<Friend> professors = new ArrayList<Friend>();
        friendsRepository.findAll().forEach((professors::add));
        return professors;
    }

    public Page<Friend> getInvitationsByUser1_id(Pageable pageable, long user1_id) {
        Page<Friend> friends = friendsRepository.findInvitationsByUser1(pageable, user1_id);
        return friends;
    }

    public Friend getFriend(Long id) {
        return friendsRepository.findById(id).get();
    }

    public Page<Friend> getFriendByUser2(Pageable pageable, Long User2_id) {
        return friendsRepository.findByUser2_id(pageable, User2_id);
    }

    public Page<Friend> getFriendByUser1(Pageable pageable, Long User1_id) {
        return friendsRepository.findByUser1_id(pageable, User1_id);
    }

    public void addFriend(Friend professor) {
        friendsRepository.save(professor);
    }

    public void deleteFriend(Long id) {
        friendsRepository.deleteById(id);
    }

    public void setFriendInvitationSend(boolean revised, Long id) {

        //Friend friend = friendsRepository.findById(id).get();

        friendsRepository.updateResend(revised, id);

    }

}
