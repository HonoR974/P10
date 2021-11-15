package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class LivreServiceImplTest {

    @Mock
    LivreRepository livreRepository;

    @InjectMocks
    LivreServiceImpl livreService;

    @Mock
    StatutRepository statutRepository;

    @Mock
    ReservationRepository reservationRepository;

    Livre l1;
    Livre l2;
    Livre l3;
    Livre l4;
    Livre l5;

    Examplaire e1;
    Examplaire e2;

    Statut statut;
    Statut statut2;

    Reservation reserv1;
    Reservation reserv2;


    List<Livre> list;
    List<Livre> listSearch;
    List<Examplaire> listExamplaire;
    List<Reservation> list2;

    @Before
    public void setUp()
    {
        initLivre();

        initExamplaire();

        initList();

        initDateRetour();

        initNmbUser();

        initMocks(this);

        //--- when

        when(livreRepository.findAll()).thenReturn(list);
        when(livreRepository.findById(1L)).thenReturn(l1);
        when(livreRepository.findById(2L)).thenReturn(l2);
        when(livreRepository.findById(5L)).thenReturn(l5);
        when(livreRepository.save(l1)).thenReturn(l1);
        when(livreRepository.save(l2)).thenReturn(l3);


        //search
        String recherche = "La danseuse";
        when(livreRepository.findByTitreContainsOrAuteurContains(recherche,recherche)).thenReturn(listSearch);

        //statut
        when(statutRepository.findByNom("First")).thenReturn(statut);
        when(statutRepository.findByNom("InList")).thenReturn(statut2);

        //reservation
        when(reservationRepository.findByLivreReservationAndStatutReservationOrStatutReservation(l5,statut,statut2)).thenReturn(list2);
    }

    private void initLivre()
    {
        l1 = new Livre();
        l2 = new Livre();
        l3 = new Livre();

        l1.setId(1L);
        l1.setTitre("Le runer");

        l2.setId(2L);
        l2.setTitre("Deuxieme");

        l3.setTitre("La danseuse");

    }

    private void initExamplaire()
    {
        e1 = new Examplaire();
        e2 = new Examplaire();

        e1.setEmprunt(false);
        e2.setEmprunt(false);
    }

    private void initList()
    {
        list = new ArrayList<>();
        list.add(l1);
        list.add(l2);

        listSearch = new ArrayList<>();
        listSearch.add(l3);

        listExamplaire = new ArrayList<>();

        listExamplaire.add(e1);
        listExamplaire.add(e2);
        l1.setExamplaires(listExamplaire);
    }

    private void initDateRetour()
    {
        l4 = new Livre();

        statut = new Statut();
        statut.setNom("First");


        reserv1 = new Reservation();
        reserv2 = new Reservation();

        reserv1.setDate_fin(new Date(2001, Calendar.JANUARY,2));
        reserv1.setDate_fin(new Date(2001,Calendar.FEBRUARY,2));

        reserv1.setStatutReservation(statut);
        reserv2.setStatutReservation(statut);

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reserv1);
        reservationList.add(reserv2);



        l4.setReservations(reservationList);

    }

    private void initNmbUser()
    {
        statut2 = new Statut();
        statut2.setNom("InList");

        Reservation r1 = new Reservation();
        r1.setStatutReservation(statut);

        Reservation r2 = new Reservation();
        r2.setStatutReservation(statut2);

        l5 = new Livre();

        list2 = new ArrayList<>();
        list2.add(r1);
        list2.add(r2);

        l5.setReservations(list2);
    }

    @Test
    public void getAllLivres()
    {
        List<Livre> list = livreService.getAllLivres();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void createLivre()
    {
        Livre livre = livreService.getLivreById(1L);

        Livre livreFinal = livreService.createLivre(livre);

        assertThat(livreFinal.getId()).isEqualTo(1L);
        assertThat(livreFinal.getTitre()).isEqualTo("Le runer");
    }

    @Test
    public void getLivreById()
    {
        Livre l1 = livreService.getLivreById(1L);

        assertThat(l1.getId()).isEqualTo(1L);
        assertThat(l1.getTitre()).isEqualTo("Le runer");
    }

    @Test
    public void updateLivre()
    {
        Livre livre = livreService.getLivreById(2L);
        assertThat(livre.getTitre()).isEqualTo("Deuxieme");

        livre.setTitre("La danseuse");
        Livre livreFinal = livreService.updateLivre(2L, l2);
        assertThat(livreFinal.getTitre()).isEqualTo("La danseuse");
    }

    @Test
    public void deleteLivre()
    {
        long id = 1L;

        livreService.deleteLivre(id);

        verify(livreRepository, times(1)).deleteById(eq(id));
    }

    @Test
    public void getAllExamplaireByIdLivre()
    {
        List<Examplaire> list = livreService.getAllExamplaireByIdLivre(1L);

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void convertListLivre() {
    }

    @Test
    public void convertLivre()
    {
        //Intro
        Livre livre = new Livre();
        Examplaire examplaire = new Examplaire();
        Examplaire examplaire1 = new Examplaire();
        examplaire.setEmprunt(false);
        examplaire1.setEmprunt(false);

        List<Examplaire> list = new ArrayList<>();
        list.add(examplaire);
        list.add(examplaire1);

        ImageGallery imageGallery = new ImageGallery();
        imageGallery.setName("laBelleVie.png");

        livre.setId(20L);
        livre.setAuteur("Marc Levy");
        livre.setTitre("La belle vie");
        livre.setDescription("test Description");
        livre.setExamplaires(list);
        livre.setImage(imageGallery);


        when(livreRepository.findById(20L)).thenReturn(livre);

        //le test
        LivreDTO livreDTO = livreService.convertLivre(livre);


        //verif
        assertThat(livreDTO.getAuteur()).isEqualTo("Marc Levy");

    }

    @Test
    public void getAllLivre()
    {
        List<Livre> list = livreService.getAllLivre();

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void searchLivre()
    {
        String recherche = "La danseuse";
        List<Livre> list = livreService.searchLivre(recherche);

        assertThat(list.get(0).getTitre()).isEqualTo("La danseuse");
    }

    @Test
    public void checkDispo()
    {
        boolean dispo = livreService.checkDispo(1L);

        assertThat(dispo).isTrue();
    }

    @Test
    public void checkDispoAllLivres()
    {
        livreService.checkDispoAllLivres();

        verify(livreRepository, times(1)).findAll();
        verify(livreRepository, times(2)).save(any());
    }

    @Test
    public void dateRetourByLivre()
    {

        String dateRetour = livreService.dateRetourByLivre(l4);
    }

    @Test
    public void nmbUserReserv()
    {
      
        Statut first = new Statut("First");
        Statut inList = new Statut("InList");
      
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();

        r1.setStatutReservation(first);
        r2.setStatutReservation(inList);

        Livre livre = new Livre();
        List<Reservation> list = new ArrayList<>();
        List<Reservation> list2 = new ArrayList<>();
        list.add(r1);
        list2.add(r2);

        when(statutRepository.findByNom("First")).thenReturn(first);
        when(statutRepository.findByNom("InList")).thenReturn(inList);

        when(reservationRepository.findByLivreReservationAndStatutReservation(livre, first)).thenReturn(list);
        when(reservationRepository.findByLivreReservationAndStatutReservation(livre, inList)).thenReturn(list2);


        int nmb = livreService.nmbUserReserv(livre);
        assertThat(nmb).isGreaterThan(0);

    }
}