package com.bibliotheque.service;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.repository.BibliothequeRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@ExtendWith(MockitoExtension.class)
public class BibliothequeServiceImplTest {


    @Mock
     BibliothequeRepository bibliothequeRepository;

    @InjectMocks
     BibliothequeServiceImpl bibliothequeService;

     Bibliotheque b1;
     Bibliotheque b2;
     Bibliotheque b3;
     Bibliotheque b4;

     List<Bibliotheque> list;


    @Before
    public void setUp()
    {
        bibliothequeRepository = mock(BibliothequeRepository.class);

        list = new ArrayList<>();

        b1 = new Bibliotheque("B1", "rue 1");
        b2 = new Bibliotheque("B2", "rue 2");

        list.add(b1);
        list.add(b2);

        b3 = new Bibliotheque("B3", "rue 3");

       initMocks(this);


        when(bibliothequeRepository.findAll()).thenReturn(list);
        when(bibliothequeRepository.findByNom("B1")).thenReturn(b1);
        when(bibliothequeRepository.save(any(Bibliotheque.class))).thenReturn(b3);

    }



    @Test
    public void getAllBibliotheque  ()
    {
        List<Bibliotheque> listBiblio = bibliothequeRepository.findAll();
        System.out.println("\n la liste des bibliotheque " + listBiblio.toString());

        assertThat(listBiblio.size()).isEqualTo(2);
    }


    @Test
    public void getBibliotheque()
    {
        String name = "B1";
        Bibliotheque bibliotheque = bibliothequeService.getByName(name);

        assertThat(bibliotheque.getNom()).isEqualTo(name);
    }

    @Test
    public void createBibliotheque()
    {
        Bibliotheque bibliotheque = new Bibliotheque("B3","rue 3");

        Bibliotheque b3 = bibliothequeService.createBibliotheque(bibliotheque);

        assertThat(b3.getNom()).isEqualTo("B3");
    }

    @Test
   public  void updateBibliotheque()
    {
        Bibliotheque bibliotheque = new Bibliotheque("B3", "rue 3");
        bibliothequeService.updateBibliotheque(b3.getId(), bibliotheque);

    }

    @Test
    public void deleteBibliotheque() {
    }
}