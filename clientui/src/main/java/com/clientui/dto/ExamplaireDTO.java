package com.clientui.dto;

public class ExamplaireDTO {
    private Long id;
    private String edition;

    public ExamplaireDTO()
    {}

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

    @Override
    public String toString() {
        return "ExamplaireBean{" +
                "id=" + id +
                ", edition='" + edition + '\'' +
                '}';
    }
}
