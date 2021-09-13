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
        System.out.println("\n la classe before ");
        bibliothequeRepository = mock(BibliothequeRepository.class);

        list = new ArrayList<>();

        b1 = new Bibliotheque("B1", "rue 1");
        b2 = new Bibliotheque("B2", "rue 2");

        list.add(b1);
        list.add(b2);

       initMocks(this);

        System.out.println("\n la liste " + list.toString());
        when(bibliothequeRepository.findAll()).thenReturn(list);
        when(bibliothequeRepository.findByNom("B1")).thenReturn(b1);
        System.out.println("\n la liste findAll" + bibliothequeRepository.findAll());
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

    }

    @Test
   public  void updateBibliotheque() {
    }

    @Test
    public void deleteBibliotheque() {
    }
}