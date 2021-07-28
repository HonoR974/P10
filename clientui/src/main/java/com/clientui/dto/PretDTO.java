package com.clientui.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
public class PretDTO {

    private Long id;
    private String date_debut;
    private String date_fin;

    private String statut;
    private String  username;
    private String  titre;
    private boolean enabled;
    private String titreImage;
}
