package com.bibliotheque.web.controller;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RestController PretController
 */
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    //creer une reservation
    @GetMapping("/create/{id}")
    public ResponseEntity<?> createPret(@PathVariable(name = "id")Long id_livre)
    {
        Reservation reservation = reservationService.createReservation(id_livre);

        ReservationDTO reservationDTO = reservationService.giveReservationDTO(reservation);
        return new ResponseEntity<ReservationDTO>(reservationDTO, HttpStatus.ACCEPTED);

    }
}
