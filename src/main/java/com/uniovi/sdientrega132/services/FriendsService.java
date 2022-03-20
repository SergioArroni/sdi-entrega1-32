package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.repositories.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;

    public Page<Friend> getInvitationsByUser1_id(Pageable pageable, long user1_id) {
        return friendsRepository.findInvitationsByUser1(pageable, user1_id);
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

    public void setFriendInvitationSend(boolean revised, Long id) {
        friendsRepository.updateResend(revised, id);
    }

}
