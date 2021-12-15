package com.bibliotheque.dto;

import org.junit.Before;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BibliothequeDTOTest {

    private Long id;
    private String nom;
    private String adresse;


    @Before
    public void setUp()
    {
        this.id = 1L;
        this.nom = "test";
        this.adresse = "rue test";
    }

    @Test
    public void getId()
    {
        long idTest = id;
        assertThat(idTest).isEqualTo(id);

    }

    @Test
    public void getNom()
    {
        String name = nom;
        assertThat(name).isEqualTo(nom);
    }

    @Test
    public void getAdresse()
    {
        String add = adresse;
        assertThat(add).isEqualTo(adresse);
    }

    @Test
    public void setId()
    {
        long ajout = 2L;
        BibliothequeDTO bibliothequeDTO = new BibliothequeDTO();
        bibliothequeDTO.setId(ajout);

        assertThat(bibliothequeDTO.getId()).isEqualTo(2L);
    }

    @Test
    public void setNom()
    {
        String newName = "Sam";
        BibliothequeDTO bibliothequeDTO = new BibliothequeDTO();
        bibliothequeDTO.setNom(newName);
        assertThat(bibliothequeDTO.getNom()).isEqualTo(newName);
    }

    @Test
    public void setAdresse()
    {
        String newAdd = "Rue de la pepinière";
        BibliothequeDTO bibliothequeDTO = new BibliothequeDTO();
        bibliothequeDTO.setAdresse(newAdd);
        assertThat(bibliothequeDTO.getAdresse()).isEqualTo(newAdd);
    }

    @Test
    public void testEquals()
    {
        BibliothequeDTO bi1 = new BibliothequeDTO();
        BibliothequeDTO bi2 = new BibliothequeDTO();

        bi1.setNom("Bibi");
        bi1.setId(1L);
        bi1.setAdresse("rue Test");
        bi2.setId(1L);
        bi2.setNom("Bibi");
        bi2.setAdresse("rue Test");

      assertEquals(bi1.getNom(), bi2.getNom());
      assertEquals(bi1.getId(), bi2.getId());
    }

    @Test
    public void canEqual()
    {

    }

    @Test
    public void testHashCode()
    {
        BibliothequeDTO bi1 = new BibliothequeDTO();
        BibliothequeDTO bi2 = new BibliothequeDTO();

        bi1.setNom("Bibi");
        bi1.setId(1L);
        bi1.setAdresse("rue Test");
        bi2.setId(1L);
        bi2.setNom("Bibi");
        bi2.setAdresse("rue Test");

        assertThat(bi1.hashCode()).isEqualTo(bi2.hashCode());
    }

    @Test
    public void testToString()
    {
        BibliothequeDTO bi1 = new BibliothequeDTO();
        BibliothequeDTO bi2 = new BibliothequeDTO();

        bi1.setNom("Bibi");
        bi1.setId(1L);
        bi1.setAdresse("rue Test");
        bi2.setId(1L);
        bi2.setNom("Bibi");
        bi2.setAdresse("rue Test");

        assertEquals(bi1.toString(), bi2.toString());
    }
}