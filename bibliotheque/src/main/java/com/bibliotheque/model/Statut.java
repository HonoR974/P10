package com.bibliotheque.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity

public class Statut
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "statut")
    private List<Pret> listeDePret;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Pret> getListeDePret() {
        return listeDePret;
    }

    public void setListeDePret(List<Pret> listeDePret) {
        this.listeDePret = listeDePret;
    }



}
