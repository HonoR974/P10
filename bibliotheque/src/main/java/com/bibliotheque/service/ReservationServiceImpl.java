package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService{


    @Autowired
    private SecurityService securityService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Override
    public Reservation createReservation(long id_livre)
    {

        Livre livre = livreRepository.findById(id_livre);

        //verification du livre

        User user = securityService.getUser();
        Statut statut = statutRepository.findByNom("ReservationDemande");

        Reservation reservation = new Reservation();
        reservation.setLivreReservation(livre);
        reservation.setUserReservation(user);
        reservation.setStatutReservation(statut);

        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public ReservationDTO giveReservationDTO(Reservation reservation) {

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUsername(reservation.getUserReservation().getUsername());
        reservationDTO.setTitre(reservation.getLivreReservation().getTitre());
        reservationDTO.setStatut(reservation.getStatutReservation().getNom());
        return reservationDTO;
    }

    //verification
}
