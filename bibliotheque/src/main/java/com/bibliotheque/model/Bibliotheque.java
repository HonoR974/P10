package com.bibliotheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bibliotheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String  nom;
    private  String adresse;

    @OneToMany(mappedBy = "bibliotheque")
    private List<Livre> livres;
}
