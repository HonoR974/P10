package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Reservation;


public interface ReservationService {


    Reservation createReservation(long id );

    ReservationDTO giveReservationDTO(Reservation reservation);
}
