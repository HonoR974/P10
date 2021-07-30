package com.bibliotheque.dto;

import lombok.Data;

@Data
public class ReservationDTO
{

    private Long id;
    private String username;
    private String titre;
    private String statut;
}
