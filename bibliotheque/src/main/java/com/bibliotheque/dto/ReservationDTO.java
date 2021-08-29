package com.bibliotheque.dto;

import lombok.Data;

@Data
public class ReservationDTO
{

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

}
