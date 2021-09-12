package com.bibliotheque.service;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.repository.BibliothequeRepository;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
        b1 = new Bibliotheque("B2", "rue 2");

        list.add(b1);
        list.add(b2);

       initMocks(this);

        System.out.println("\n la liste " + list.toString());
        Mockito.when(bibliothequeRepository.findAll()).thenReturn(list);
        Mockito.when(bibliothequeRepository.findByNom("B1")).thenReturn(b1);
        System.out.println("\n la liste findAll" + bibliothequeRepository.findAll());
    }


    @Test
    public void test()
    {
        List<Bibliotheque> listBiblio = bibliothequeRepository.findAll();

        System.out.println("\n la liste des bibliotheque " + listBiblio.toString());

        assertThat(listBiblio.size()).isEqualTo(2);

        Bibliotheque bibliotheque = bibliothequeService.getByName("B1");
        assertThat(bibliotheque.getNom()).isEqualTo("B1");
    }


    @Test
    public void getAllBibliotheque  ()
    {

    }


    @Test
    public void getBibliotheque()
    {

    }

    @Test
    void createBibliotheque() throws Exception {

    }

    @Test
    void updateBibliotheque() {
    }

    @Test
    void deleteBibliotheque() {
    }
}