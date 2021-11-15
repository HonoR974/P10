package com.bibliotheque.dto;

import org.junit.Before;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationDTOTest {

    ReservationDTO r1;
    ReservationDTO r2;

    @Before
    public void setUp()
    {
        r1 = new ReservationDTO();
        r1.setId(1L);
        r1.setUsername("UserTest");
        r1.setTitre("TitreTest");
        r1.setStatut("En Attente");
        r1.setSendMail(false);
        r1.setMail("test@mail.com");
        r1.setDate_debut("2001/02/03");
        r1.setDate_fin("2001/02/04");
        r1.setDate_demande("2001/01/01");
        r1.setTitreImage("ImageTest");

        r2 = new ReservationDTO();
        r2.setId(1L);
        r2.setUsername("UserTest");
        r2.setTitre("TitreTest");
        r2.setStatut("En Attente");
        r2.setSendMail(false);
        r2.setMail("test@mail.com");
        r2.setDate_debut("2001/02/03");
        r2.setDate_fin("2001/02/04");
        r2.setDate_demande("2001/01/01");
        r2.setTitreImage("ImageTest");

    }

    @Test
    public void testEquals()
    {
        assertThat(r1.toString()).isEqualTo(r2.toString());
        assertThat(r1).isNotEqualTo(r2);
    }

    @Test
    public void testHashCode()
    {
        assertThat(r1.hashCode()).isNotEqualTo(r2.hashCode());
    }
}