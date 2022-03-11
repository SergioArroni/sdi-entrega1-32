package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Friend;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface FriendsRepository extends CrudRepository<Friend, Long> {

    Friend findByUser2_id(String user2_id);

}
