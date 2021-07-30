package com.bibliotheque.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User userReservation ;

    @ManyToOne
    private Livre livreReservation;

    @ManyToOne
    private Statut statutReservation;



    //dateRetour
    //taille liste


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUserReservation() {
        return userReservation;
    }

    public void setUserReservation(User userReservation) {
        this.userReservation = userReservation;
    }



    public Statut getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(Statut statutReservation) {
        this.statutReservation = statutReservation;
    }

    public Livre getLivreReservation() {
        return livreReservation;
    }

    public void setLivreReservation(Livre livreReservation) {
        this.livreReservation = livreReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userReservation=" + userReservation +
                ", livreReservation=" + livreReservation +
                ", statutReservation=" + statutReservation +
                '}';
    }
}
