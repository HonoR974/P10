package com.bibliotheque.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ImageGalleryDTOTest {

    ImageGalleryDTO img1;
    ImageGalleryDTO img2;

    @Before
    public void setUp() throws Exception
    {
        img1 = new ImageGalleryDTO();
        img1.setId(1L);
        img1.setName("ImageTest");

        img2 = new ImageGalleryDTO();
        img2.setId(1L);
        img2.setName("ImageTest");
    }

    @Test
    public void testEquals()
    {
       assertEquals(img1, img2);
    
  }

    @Test
    public void testHashCode()
    {
        assertThat(img1.hashCode()).isEqualTo(img2.hashCode());

    }
}