package com.bibliotheque.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class Pret  
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date_debut;
    private LocalDate date_fin;

    private Boolean prolonger;

    @ManyToOne
    private Statut statut;


    @ManyToOne
    private User user;


    @ManyToOne
    private Examplaire examplaire;

    @JsonIgnore
    @ManyToOne
    private ImageGallery image;

    private Boolean email;

    public Pret ()
    {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public Boolean getProlonger() {
        return prolonger;
    }

    public void setProlonger(boolean prolonger) {
        this.prolonger = prolonger;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Examplaire getExamplaire() {
        return examplaire;
    }

    public void setExamplaire(Examplaire examplaire) {
        this.examplaire = examplaire;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public ImageGallery getImage() {
        return image;
    }

    public void setImage(ImageGallery image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id=" + id +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", prolonger=" + prolonger +
                ", statut=" + statut +
                ", user=" + user +
                ", examplaire=" + examplaire +
                ", image=" + image +
                ", email=" + email +
                '}';
    }
}
