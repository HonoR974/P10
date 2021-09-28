package com.bibliotheque.service;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.ExamplaireRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class ExamplaireServiceImplTest {

    @Mock
    ExamplaireRepository examplaireRepository;

    @InjectMocks
    ExamplaireServiceImpl examplaireService;

    Examplaire e1;
    Examplaire e2;
    Examplaire e3;

    Livre l1;

    List<Examplaire> list;


    @Before
    public void setUp()
    {
        examplaireRepository = mock(ExamplaireRepository.class);

        //---- examplaire
        e1 = new Examplaire();
        e2 = new Examplaire();
        e3 = new Examplaire();

        e1.setId(1L);
        e1.setEdition("Hachette");

        e2.setEdition("Pocket");
       // e3.setId(1L);

        //---- livre

        l1 = new Livre();
        l1.setTitre("Pour une vie");
        e1.setLivre(l1);

        //--- liste

        list = new ArrayList<>();

        list.add(e1);
        list.add(e2);

        initMocks(this);

        when(examplaireRepository.findAll()).thenReturn(list);
        when(examplaireRepository.save(any())).thenReturn(e3);

        //getExamplaire
        when(examplaireRepository.findById(1L)).thenReturn(e1);
        //update
        when(examplaireRepository.save(e1)).thenReturn(e2);

    }

    @Test
    public void getAllExamplaire()
    {
        List<Examplaire> list = examplaireService.getAllExamplaire();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void createExamplaire()
    {
        Examplaire ex = new Examplaire();
        long id = 1L;
        ex.setId(id);

        Examplaire examplaire = examplaireService.createExamplaire(ex);

        assertThat(examplaire.getId()).isEqualTo(id);
    }

    @Test
    public void updateExamplaire()
    {
        Examplaire examplaire = examplaireService.getExamplaireById(1L);
        examplaire.setEdition("Pocket");

        Examplaire examplaireFinal = examplaireService.updateExamplaire(1L,examplaire);

        assertThat(examplaireFinal.getEdition()).isEqualTo("Pocket");
    }

    @Test
    public void deleteExamplaire()
    {
        long id = 1L;

        examplaireService.deleteExamplaire(id);

        verify(examplaireRepository, times(1)).deleteById(eq(id));
    }

    @Test
    public void getExamplaireById()
    {
        Examplaire examplaire = examplaireService.getExamplaireById(1L);

        assertThat(examplaire.getEdition()).isEqualTo("Hachette");
    }

    @Test
    public void getLivreById()
    {
        Examplaire examplaire = examplaireService.getExamplaireById(1L);

        assertThat(examplaire.getLivre().getTitre()).isEqualTo("Pour une vie");
    }

    @Test
    public void convertExamplaire()
    {
        long id = 1L;
        Examplaire examplaire = examplaireService.getExamplaireById(id);

        ExamplaireDTO examplaireDTO = examplaireService.convertExamplaire(examplaire);

        assertThat(examplaireDTO.getId()).isEqualTo(examplaire.getId());
        assertThat(examplaireDTO.getEdition()).isEqualTo(examplaire.getEdition());
    }
}