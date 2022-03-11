package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.repositories.FriendsRepository;
import com.uniovi.sdientrega132.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public List<Friend> getInvitationsByUser1_id(long user1_id) {
        List<Friend> friends = friendsRepository.findInvitationsByUser1(user1_id);
        return friends;
    }

    public Friend getFriend(Long id) {
        return friendsRepository.findById(id).get();
    }

    //public Friend getFriendByUser2(String User2_id) {
        //return friendsRepository.findByUser2_id(User2_id);
    //}

    public void addFriend(Friend professor) {
        friendsRepository.save(professor);
    }

    public void deleteFriend(Long id) {
        friendsRepository.deleteById(id);
    }


}
