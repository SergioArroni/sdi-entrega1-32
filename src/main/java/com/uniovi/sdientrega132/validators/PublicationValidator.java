package com.uniovi.sdientrega132.validators;

import com.uniovi.sdientrega132.entities.Publication;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@SuppressWarnings("NullableProblems")
@Component
public class PublicationValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Publication.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
    }

}
