package com.bibliotheque.model;


import javax.persistence.*;
import java.util.List;


@Entity
public class Examplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String edition;

    @ManyToOne
    private Livre livre;

    @OneToMany(mappedBy = "examplaire")
    private List<Pret> listeDePret;

    @Column(name = "emprunt", nullable = true)
    private Boolean emprunt;

    public Examplaire(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public List<Pret> getListeDePret() {
        return listeDePret;
    }

    public void setListeDePret(List<Pret> listeDePret) {
        this.listeDePret = listeDePret;
    }

    public Boolean isEmprunt() {
        return emprunt;
    }

    public void setEmprunt(boolean emprunt) {
        this.emprunt = emprunt;
    }

    @Override
    public String toString() {
        return "Examplaire [edition=" + edition + ", emprunt=" + emprunt + ", id=" + id + ", listeDePret=" + listeDePret
                + ", livre=" + livre + "]";
    }



}
