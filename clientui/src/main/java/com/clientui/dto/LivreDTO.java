package com.clientui.dto;

import lombok.Data;

@Data
public class LivreDTO {

    private Long id;
    private String auteur;
    private String titre;
    private long examplaires;
    private String titreImage;
    private String description;
}