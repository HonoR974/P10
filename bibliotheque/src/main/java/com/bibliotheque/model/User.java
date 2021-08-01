package com.bibliotheque.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String matchingPassword;
    private String email;

    private boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Pret> listeDePret;

    @JsonIgnore
    @OneToMany(mappedBy = "userReservation")
    private List<Reservation> listReservation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Pret> getListeDePret() {
        return listeDePret;
    }

    public void setListeDePret(List<Pret> listeDePret) {
        this.listeDePret = listeDePret;
    }

    public List<Reservation> getListReservation() {
        return listReservation;
    }

    public void setListReservation(List<Reservation> listReservation) {
        this.listReservation = listReservation;
    }
}