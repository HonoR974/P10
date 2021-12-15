package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.web.exception.LivreIntrouvableException;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.service.ExamplaireService;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController PretController
 */
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {


    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreService livreService;

    @Autowired
    private ExamplaireService examplaireService;

    /**
     * Creer une Reservation avec l'id du livre
     * @param id_livre
     * @return reservationDTO
     */
    @GetMapping("/create/{id}")
    public ResponseEntity<?> createPret(@PathVariable(name = "id")Long id_livre)
    {
        long id = id_livre;
        String message;


        System.out.println("\n create ");

        // si le livre existe
        if (livreService.getLivreById(id) == null )
        {
            throw new LivreIntrouvableException( "Le livre avec l'id "
            + id + " est INTROUVABLE.");
        }

        //si le livre a des examplaire disponible
        // ( donc l'user peut emprunté )
        if (livreService.checkDispo(id))
        {
            message = "Le livre a des examplaires disponibles ";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        }
        //si le livre n'a plus de place
        else  if (! reservationService.checkPlaceListe(id_livre))
        {
            message = "le livre n'a plus de place dans sa fille d'atttente  ";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        }

        //si l'user emprunte le livre
        else if ( ! reservationService.checkEmpruntUser(id_livre))
        {
            message = "l'user possede deja ce livre ";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        }

        //si l'user reserve deja le livre
         else if ( ! reservationService.checkReservDispo(id_livre))
        {
            message = "L'user reserve deja le livre";
            return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
        }

        Reservation reservation = reservationService.createReservation(id_livre);

       
        ReservationDTO reservationDTO = reservationService.giveReservationDTO(reservation);
        
         System.out.println("\n la reservation creer  : " + reservationDTO.toString());

        return new ResponseEntity<ReservationDTO>(reservationDTO, HttpStatus.ACCEPTED);

    }

    /**
     * Get All Reservation
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAll()
    {
        System.out.println("\n get all ");
        List<Reservation> reservationList = reservationService.getAll();


        List<ReservationDTO> reservationListDTO = reservationService.giveListDTO(reservationList);

        return new ResponseEntity<List<ReservationDTO>>(reservationListDTO,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get/livre/{id}")
    public ResponseEntity<?> getReservationByBook(@PathVariable(name = "id")Long id_livre)
    {
        List<Reservation> reservationList = reservationService.getReservByBook(id_livre);

        List<ReservationDTO> dtoList = reservationService.giveListDTO(reservationList);

        return new ResponseEntity<List<ReservationDTO>>(dtoList, HttpStatus.ACCEPTED);
    }

    /**
     * Get Reservation By User
     * @return
     */
    @GetMapping("/get/user")
    public ResponseEntity<?> getReservByIdUser()
    {
        List<Reservation> reservationList = reservationService.getByUser();



        List<ReservationDTO> reservationDTOS = reservationService.giveListDTO(reservationList);

        return new ResponseEntity<List<ReservationDTO>>(reservationDTOS, HttpStatus.ACCEPTED);
    }

    //annulation reservation
    @DeleteMapping("/cancel/{id}")
    public HttpStatus cancelReservation(@PathVariable(name = "id")Long id_reservation)
    {
        reservationService.cancelReservation(id_reservation);
        return HttpStatus.ACCEPTED;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteReservation(@PathVariable(name = "id")Long id_reservation)
    {
        reservationService.deleteReservation(id_reservation);
        return HttpStatus.ACCEPTED;
    }

    //get reservation by id
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getReservById(@PathVariable("id")Long id_reserv)
    {
        Reservation reservation = reservationService.getReservById(id_reserv);
        ReservationDTO reservationDTO = reservationService.giveReservationDTO(reservation);

        return new ResponseEntity<ReservationDTO>(reservationDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("finish/{id}")
    public ResponseEntity<?> finishReservation(@PathVariable("id")Long id_reserv)
    {
        Examplaire examplaire = reservationService.finishReservation(id_reserv);

        ExamplaireDTO examplaireDTO = examplaireService.convertExamplaire(examplaire);

        return new ResponseEntity<ExamplaireDTO>(examplaireDTO, HttpStatus.ACCEPTED);

    }
}
