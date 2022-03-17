package com.uniovi.sdientrega132.validators;

import com.uniovi.sdientrega132.entities.Publication;
import com.uniovi.sdientrega132.services.PublicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PublicationValidator implements Validator {

    @Autowired
    private PublicationsService publicationService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Publication.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Publication publication = (Publication) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");

    }


}
