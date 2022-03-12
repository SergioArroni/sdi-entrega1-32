package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
