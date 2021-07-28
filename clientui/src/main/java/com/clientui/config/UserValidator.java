package com.clientui.config;


import com.clientui.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validateur de formulaire pour se connecter
 */
@Component
public class UserValidator implements Validator {


    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN ="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String message ="";
        UserDTO user = (UserDTO) o;


        //Username
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.utilisateur.username");
           message = "la taille de l'username est comprise entre 6  et 32 caractere ";
        }

        //Email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "NotEmpty");
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(user.getEmail());
        //si l'email n'est pas valide
        if (!matcher.matches())
        {
            errors.rejectValue("email", "Email.validation.regex");
        }


        //Password
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userForm.passwordConfirm");
        }


    }



    public String valid(Object o, Errors errors) {
        String message ="";
        UserDTO user = (UserDTO) o;


        //Username
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
            message = "la taille de l'username est comprise entre 6  et 32 caractere ";
        }

        //Email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email", "NotEmpty");
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(user.getEmail());
        //si l'email n'est pas valide
        if (!matcher.matches())
        {
            errors.rejectValue("email", "Email.validation.regex");
        }


        //Password
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getMatchingPassword().equals(user.getPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userForm.passwordConfirm");
        }

        return message;

    }
}