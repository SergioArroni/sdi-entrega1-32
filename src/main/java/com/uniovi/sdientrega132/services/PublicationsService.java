package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class PublicationsService {
    @Autowired
    private PublicationsRepository publicationsRepository;

    public void addPublication(Publication publication) {
        publicationsRepository.save(publication);
    }

    public Page<Publication> getPublications(Pageable pageable) {
        Page<Publication> publications = publicationsRepository.findAll(pageable);
        return publications;
    }

    public Page<Publication> getPublicationsForUser(Pageable pageable, User user) {
        Page<Publication> publications = new PageImpl<Publication>(new LinkedList<Publication>());
        publications = publicationsRepository.findAllByUser(pageable, user);
        return publications;
    }

    public Publication getPublication(Long id) {
        return publicationsRepository.findById(id).get();
    }

}
