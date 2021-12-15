package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Reservation;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;


public interface ReservationService {


    //Create
    Reservation createReservation(long id );

    // Read
    List<Reservation> getAll();

    Reservation getReservById(long id);
    //conversion reserv to DTO
    ReservationDTO giveReservationDTO(Reservation reservation);
    //conversion listReserv to  ListDTO
    List<ReservationDTO> giveListDTO(List<Reservation> reservationList);

    //conversion listDTO to listeReserv
    List<Reservation> giveList(List<ReservationDTO> reservationDTOS) throws ParseException;

    //verification : l'user n'emprunte pas le livre
    boolean checkEmpruntUser(long id_livre);

    //verification : la liste d'attente d'un livre peut contenir une autre reservation
    boolean checkPlaceListe(long id_livre);

    //verification : l'user ne reserve pas deja le livre
    boolean checkReservDispo(long id_livre);

    //Get Reservation By User
    List<Reservation> getByUser();

    //cancel une reservation
    void cancelReservation(long id_reservation);

    //delete 
    void deleteReservation(long id_reservation);

    List<Reservation> getAllFirstReserve();

    List<Reservation> getAllFirstReserveNoSendMail();

    void saveList(HashMap<Integer,ReservationDTO> list) throws ParseException;

    List<Livre> checkListeReservForAllBook();

    List<Reservation> checkDelai();

    List<Reservation> getReservByBook(Long id_livre);

    //Apres avoir recu le mail l'user emprunte le livre
    Examplaire finishReservation(long id_reserv);
}
