package com.bibliotheque.dto;

import com.bibliotheque.model.Livre;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import static org.junit.Assert.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LivreDTOTest {

    LivreDTO l1;
    LivreDTO l2;


    @Before
    public void setUp() throws Exception
    {
        l1 = new LivreDTO();
        l1.setId(1L);
        l1.setAuteur("Marc");

        l2 = new LivreDTO();
        l2.setId(1L);
        l2.setAuteur("Marc");

    }

    @Test
    public void testEquals()
    {
        assertThat(l1).isNotEqualTo(l2);
    }

    @Test
    public void testHashCode()
    {
        assertThat(l1.hashCode()).isNotEqualTo(l2.hashCode());
    }
}