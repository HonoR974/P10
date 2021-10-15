package com.bibliotheque.dto;

import lombok.Data;

@Data
public class ExamplaireDTO {

    private Long id;
    private String edition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
