package com.bibliotheque.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PretBatchDTOTest {


    PretBatchDTO p1;
    PretBatchDTO p2;

    @Before
    public void setUp()
    {
        p1 = new PretBatchDTO();
        p1.setId(1L);
        p1.setDate_debut("2001/01/01");
        p1.setDate_fin("2001/02/02");
        p1.setStatut("En Attente");
        p1.setUsername("PretTest");
        p1.setEmail("pret@mail.fr");
        p1.setTitre("titreTest");
        p1.setEnvoieEmail(false);


        p2 = new PretBatchDTO();
        p2.setId(1L);
        p2.setDate_debut("2001/01/01");
        p2.setDate_fin("2001/02/02");
        p2.setStatut("En Attente");
        p2.setUsername("PretTest");
        p2.setEmail("pret@mail.fr");
        p2.setTitre("titreTest");
        p2.setEnvoieEmail(false);

    }

    @Test
    public void testEquals()
    {
        assertThat(p1).isNotEqualTo(p2);
        assertThat(p1.getId()).isEqualTo(p2.getId());
        assertThat(p1.getDate_debut()).isEqualTo(p2.getDate_debut());
        assertThat(p1.getDate_fin()).isEqualTo(p2.getDate_fin());
        assertThat(p1.getId()).isEqualTo(p2.getId());

    }

    @Test
    public void testHashCode()
    {
        assertThat(p1.hashCode()).isNotEqualTo(p2.hashCode());
    }
}