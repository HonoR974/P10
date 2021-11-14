package com.bibliotheque.dto;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;


@ExtendWith(MockitoExtension.class)
public class PretDTOTest {


    PretDTO p1;
    PretDTO p2;

    @Before
    public void setUp() throws Exception
    {
        p1 = new PretDTO();
        p1.setId(1L);
        p1.setDate_debut("2001/01/01");
        p1.setDate_fin("2001/02/02");
        p1.setStatut("En Attente");
        p1.setUsername("PretTest");
        p1.setTitre("titreTest");
        p1.setEnabled(false);
        p1.setTitreImage("ImageTest");

        p2 = new PretDTO();
        p2.setId(1L);
        p2.setDate_debut("2001/01/01");
        p2.setDate_fin("2001/02/02");
        p2.setStatut("En Attente");
        p2.setUsername("PretTest");
        p2.setTitre("titreTest");
        p2.setEnabled(false);
        p2.setTitreImage("ImageTest");

    }

    @Test
    public void testEquals()
    {
       // Assert.assertThat(p2, new ReflectionEquals(p1));
       // assertThat(p1.getId()).isEqualTo(p2.getId());
    }

    @Test
    public void testHashCode()
    {
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode());

    }
}