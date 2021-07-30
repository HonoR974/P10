package com.bibliotheque.repository;

import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findById(long id);

    List<Reservation> findByStatutReservation(Statut statut);
}
