package com.clientui.dto;

import lombok.Data;

@Data
public class ReservationDTO {
    private long id;
    private String username;
    private String titre;
    private String statut;
    private boolean sendMail;
    private String mail;
    private String date_debut;
    private String date_fin;
    private String date_demande;
    private String titreImage;
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public boolean isSendMail() {
        return sendMail;
    }
    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
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
    public String getDate_demande() {
        return date_demande;
    }
    public void setDate_demande(String date_demande) {
        this.date_demande = date_demande;
    }
    public String getTitreImage() {
        return titreImage;
    }
    public void setTitreImage(String titreImage) {
        this.titreImage = titreImage;
    }
}

