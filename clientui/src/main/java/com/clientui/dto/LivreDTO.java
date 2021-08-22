package com.clientui.dto;

import lombok.Data;

@Data
public class LivreDTO {

    private Long id;
    private String auteur;
    private String description;
    private String titre;
    private long examplaires;
    private String titreImage;

    //date retour la plus proche reservation ( la date de fin )
    private String dateRetour;

    //nmb user ayant réservé
    private int nmbUserReserv;

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

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public int getNmbUserReserv() {
        return nmbUserReserv;
    }

    public void setNmbUserReserv(int nmbUserReserv) {
        this.nmbUserReserv = nmbUserReserv;
    }

    @Override
    public String toString() {
        return "LivreDTO{" +
                "id=" + id +
                ", auteur='" + auteur + '\'' +
                ", description='" + description + '\'' +
                ", titre='" + titre + '\'' +
                ", examplaires=" + examplaires +
                ", titreImage='" + titreImage + '\'' +
                ", dateRetour='" + dateRetour + '\'' +
                ", nmbUserReserv=" + nmbUserReserv +
                '}';
    }
}