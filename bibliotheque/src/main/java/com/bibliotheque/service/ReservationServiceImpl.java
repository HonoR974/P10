package com.bibliotheque.service;

import com.bibliotheque.dto.ReservationDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Creer une reservation avec l'id du livre
     * @param id_livre
     * @return reservation
     */
    @Override
    public Reservation createReservation(long id_livre)
    {



        Livre livre = livreRepository.findById(id_livre);
        User user = securityService.getUser();
        Statut statut = statutRepository.findByNom("En Attente");



        Reservation reservation = new Reservation();
        reservation.setLivreReservation(livre);
        reservation.setUserReservation(user);
        reservation.setStatutReservation(statut);

        reservationRepository.save(reservation);
        return reservation;
    }
    @Override
    public boolean checkEmpruntUser(long id_livre)
    {
        boolean condition = true;

        Livre livre = livreRepository.findById(id_livre);
        User user = securityService.getUser();

        for(Pret pret : user.getListeDePret())
        {
            Livre livreEmprunter = pret.getExamplaire().getLivre();

            //si le livre emprunter à le meme titre que le livre demandé à reservé
            if (livreEmprunter.getTitre().equals(livre.getTitre())) {
                condition = false;
                break;
            }

        }
        return condition;
    }

    @Override
    public List<Reservation> getAll() {

        return reservationRepository.findAll();
    }

    @Override
    public ReservationDTO giveReservationDTO(Reservation reservation) {

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUsername(reservation.getUserReservation().getUsername());
        reservationDTO.setTitre(reservation.getLivreReservation().getTitre());
        reservationDTO.setStatut(reservation.getStatutReservation().getNom());
        reservationDTO.setSendMail(reservationDTO.isSendMail());
        reservationDTO.setMail(reservation.getUserReservation().getEmail());
        return reservationDTO;
    }

    @Override
    public List<ReservationDTO> giveListDTO(List<Reservation> reservationList) {

        List<ReservationDTO> list = new ArrayList<>();

        for (Reservation reservation : reservationList)
        {
            list.add(giveReservationDTO(reservation));
        }
        return list;
    }


    @Override
    public boolean checkPlaceListe(long id_livre) {
        boolean condition = false;

        Livre livre = livreRepository.findById(id_livre);
        Statut statut = statutRepository.findByNom("En Attente");

        long max = livre.getExamplaires().size();

        long liste = reservationRepository.findByStatutReservationAndLivreReservation(statut,livre).size();


        System.out.println("\n max " + max + "\n liste " + liste);

        if (liste < max)
        {
            condition = true;
        }

        return condition;
    }

    @Override
    public List<Reservation> getByUser() {
        User user = securityService.getUser();
        Statut statut = statutRepository.findByNom("En Attente");

        return reservationRepository.findByStatutReservationAndUserReservation(statut,user);
    }

    @Override
    public void cancelReservation(long id_reservation)
    {
        Reservation reservation = reservationRepository.findById(id_reservation);
        Statut statut = statutRepository.findByNom("Annuler");
        reservation.setStatutReservation(statut);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getAllFirstReserve() {
        Statut statut = statutRepository.findByNom("First");
        return reservationRepository.findByStatutReservation(statut);
    }


}
