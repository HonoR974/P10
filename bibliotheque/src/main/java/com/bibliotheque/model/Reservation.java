package com.bibliotheque.model;

import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

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

    private Date dateDemande;


    private boolean mailSend;

    private Date date_debut;

    private Date date_fin;



    //access

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

    public boolean isMailSend() {
        return mailSend;
    }

    public void setMailSend(boolean mailSend) {
        this.mailSend = mailSend;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userReservation=" + userReservation +
                ", livreReservation=" + livreReservation +
                ", statutReservation=" + statutReservation +
                ", dateDemande=" + dateDemande +
                ", mailSend=" + mailSend +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}
