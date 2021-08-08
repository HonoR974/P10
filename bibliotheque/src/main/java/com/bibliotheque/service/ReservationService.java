package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Reservation;

import java.util.List;


public interface ReservationService {


    //Create
    Reservation createReservation(long id );

    // Read
    List<Reservation> getAll();

    //conversion DTO
    ReservationDTO giveReservationDTO(Reservation reservation);
    //conversion ListDTO
    List<ReservationDTO> giveListDTO(List<Reservation> reservationList);

    //verification : l'user n'emprunte pas le livre
    boolean checkEmpruntUser(long id_livre);

    //verification : la liste d'attente d'un livre peut contenir une autre reservation
    boolean checkPlaceListe(long id_livre);

    //Get Reservation By User
    List<Reservation> getByUser();

    //cancel une reservation
    void cancelReservation(long id_reservation);

    List<Reservation> getAllFirstReserve();

}
