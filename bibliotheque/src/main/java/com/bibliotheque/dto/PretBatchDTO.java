package com.bibliotheque.dto;

import lombok.Data;

@Data
public class PretBatchDTO {
    private Long id;
    private String date_debut;
    private String date_fin;

    private String statut;
    private String username;
    private String email;
    private String titre;
    private Boolean envoieEmail;


}
