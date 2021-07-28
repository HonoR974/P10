package com.clientui.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

public class PretBean
{
    private Long id;

  //  @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date_debut;

  //  @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date date_fin;

    private String username;
    private String  titre;
    private String statut;
    private String titreImage;

    public PretBean()
    {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTitreImage() {
        return titreImage;
    }

    public void setTitreImage(String titreImage) {
        this.titreImage = titreImage;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }
}
