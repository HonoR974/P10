package com.bibliotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "livre", uniqueConstraints = {@UniqueConstraint(columnNames = {"titre"})})
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String auteur;

    private String description;

    @Column(name = "titre")
    private String titre;

    @ManyToOne
    private Bibliotheque bibliotheque;

    @OneToMany(mappedBy = "livre")
    private List<Examplaire> examplaires;

    @JsonIgnore
    @ManyToOne
    private ImageGallery image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Bibliotheque getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public List<Examplaire> getExamplaires() {
        return examplaires;
    }

    public void setExamplaires(List<Examplaire> examplaires) {
        this.examplaires = examplaires;
    }

    public ImageGallery getImage() {
        return image;
    }

    public void setImage(ImageGallery image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
