package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendsRepository extends CrudRepository<Friend, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Friend SET accept = ?1 WHERE id = ?2")
    void updateResend(Boolean accept, Long id);

    @Query("SELECT r FROM Friend r WHERE r.User1_id = ?1 and r.accept = false")
    Page<Friend> findInvitationsByUser1(Pageable pageable,Long user1_id);

    @Query("SELECT r FROM Friend r WHERE r.User2_id = ?1")
    List<Friend> findInvitationsByUser2Id(Long user1_id);

    @Query("SELECT r FROM Friend r WHERE (r.User1_id = ?1 or r.User2_id = ?1) and r.accept = true ORDER BY r.User1_id ASC")
    Page<Friend> friendsUser1(Pageable pageable, Long user1_id);

    @Query("SELECT r FROM Friend r WHERE r.User1_id = ?1 and r.User2_id = ?2")
    Friend findCoupleFriends(Long user1_id, Long user2_id);

}
