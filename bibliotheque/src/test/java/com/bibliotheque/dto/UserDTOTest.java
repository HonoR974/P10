package com.bibliotheque.dto;

import org.junit.Before;
import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;



public class UserDTOTest {

    UserDTO u1;
    UserDTO u2;

    @Before
    public void setUp() throws Exception
    {
        u1 = new UserDTO();

        u1.setId(1L);
        u1.setUsername("UserTest");

        u2 = new UserDTO();
        u2.setId(1L);
        u2.setUsername("UserTest");


    }

    @Test
    public void testEquals()
    {
        assertThat(u1.equals(u2)).isFalse();

    }

    @Test
    public void testHashCode()
    {
    
        assertThat(u1.hashCode()).isNotEqualTo(u2.hashCode());
    }
}