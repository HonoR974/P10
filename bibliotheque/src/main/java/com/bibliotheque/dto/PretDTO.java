package com.bibliotheque.dto;


import lombok.Data;



@Data
public class PretDTO
{

    private Long id;
    private String date_debut;
    private String date_fin;

    private String statut;
    private String  username;
    private String  titre;
    private boolean enabled;
    private String titreImage;
}
