package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {

    @Query("SELECT p FROM Publication p WHERE p.user = ?1 ORDER BY p.id ASC")
    Page<Publication> findAllByUser(Pageable pageable, User user);

    Page<Publication> findAll(Pageable pageable);

}
