package com.bibliotheque.dto;

import com.bibliotheque.model.Examplaire;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ExamplaireDTOTest {

    ExamplaireDTO e1;
    ExamplaireDTO e2;

    @Before
    public void setUp()
    {
        e1 = new ExamplaireDTO();
        e1.setId(1L);
        e1.setEdition("Pocket");

        e2 = new ExamplaireDTO();
        e2.setId(1L);
        e2.setEdition("Pocket");

    }


    @Test
    public void testEquals()
    {

        assertThat(e1).isEqualTo(e2);
    }

    @Test
    public void testHashCode()
    {
        assertThat(e1).isEqualTo(e2);
    }
}