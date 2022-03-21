package com.uniovi.sdientrega132.services;

import com.uniovi.sdientrega132.entities.Log;
import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.entities.User;
import com.uniovi.sdientrega132.repositories.PublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection"})
public class PublicationsService {
    @Autowired
    private PublicationsRepository publicationsRepository;

    public void addPublication(Publication publication) {
        publicationsRepository.save(publication);
    }

    public Page<Publication> getPublications(Pageable pageable) {
        return publicationsRepository.findAll(pageable);
    }

    public List<Publication> getPublications() {
        List<Publication> publications = new ArrayList<>();
        publicationsRepository.findAll().forEach(publications::add);
        return publications;
    }

    public Page<Publication> getPublicationsForUser(Pageable pageable, User user) {
        Page<Publication> publications;
        publications = publicationsRepository.findAllByUser(pageable, user);
        return publications;
    }

    public Page<Publication> searchPublications(String searchText, Pageable pageable) {
        Page<Publication> publications;
        searchText = "%" + searchText + "%";
        publications = publicationsRepository.searchPublications(pageable, searchText);
        return publications;
    }

    public void editStateOf(Long id, String state) {
        publicationsRepository.editState(id, state);
    }


    public Publication getPublication(Long id) {
        return publicationsRepository.findById(id).get();
    }

    public void deleteAllPublications() {
        publicationsRepository.deleteAll();
    }
}
