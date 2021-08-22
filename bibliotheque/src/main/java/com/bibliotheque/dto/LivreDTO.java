package com.bibliotheque.dto;

import lombok.Data;


@Data
public class LivreDTO {
    private Long id;
    private String auteur;
    private String description;
    private String titre;
    private long examplaires;
    private String titreImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public long getExamplaires() {
        return examplaires;
    }

    public void setExamplaires(long examplaires) {
        this.examplaires = examplaires;
    }

    public String getTitreImage() {
        return titreImage;
    }

    public void setTitreImage(String titreImage) {
        this.titreImage = titreImage;
    }
}
