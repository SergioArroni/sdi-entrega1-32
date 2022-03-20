package com.uniovi.sdientrega132.repositories;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PublicationsRepository extends CrudRepository<Publication, Long> {

    @Query("SELECT p FROM Publication p WHERE p.user = ?1 AND p.state<>'censurada' ORDER BY p.id ASC")
    Page<Publication> findAllByUser(Pageable pageable, User user);

    @Query("SELECT p FROM Publication p WHERE p.user = ?1 AND p.state<>'censurada' AND p.state<>'moderada' ORDER BY p.id ASC")
    Page<Publication> findAllByFriend(Pageable pageable, User user);

    Page<Publication> findAll(Pageable pageable);

    @Query("SELECT p from Publication p WHERE (LOWER(p.title) LIKE LOWER(?1) OR LOWER(p.text) LIKE LOWER(?1) OR " +
            "LOWER(p.user.email) LIKE LOWER(?1) OR LOWER(p.state) LIKE LOWER(?1))")
    Page<Publication> searchPublications(Pageable pageable, String searchText);

    @Transactional
    @Modifying
    @Query("UPDATE Publication p SET p.state=?2 WHERE p.id=?1")
    void editState(Long id, String state);


}
