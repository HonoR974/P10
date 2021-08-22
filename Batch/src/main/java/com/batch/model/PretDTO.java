package com.batch.model;

import lombok.Data;

@Data
public class PretDTO
{

    private Long id;
    private String date_debut;
    private String date_fin;

    private String statut;
    private String username;
    private String email;
    private String titre;
    private Boolean envoieEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Boolean getEnvoieEmail() {
        return envoieEmail;
    }

    public void setEnvoieEmail(Boolean envoieEmail) {
        this.envoieEmail = envoieEmail;
    }
}
