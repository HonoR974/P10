package com.bibliotheque.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExamplaireIntrouvableException extends RuntimeException {
    
    public ExamplaireIntrouvableException(String s)
    {
        super(s);
    }

}
