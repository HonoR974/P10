package com.bibliotheque.repository;

import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findById(long id);

    List<Reservation> findByStatutReservation(Statut statut);

    List<Reservation> findByStatutReservationAndUserReservation(Statut statut, User user);

    List<Reservation> findByStatutReservationAndLivreReservation(Statut statut, Livre livre);

    List<Reservation> findByStatutReservationOrStatutReservationAndLivreReservation(Statut statut1, Statut statut2, Livre livre);
}
