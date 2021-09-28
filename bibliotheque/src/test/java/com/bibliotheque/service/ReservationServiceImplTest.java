package com.bibliotheque.service;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    LivreRepository livreRepository;

    @Mock
    SecurityServiceImpl securityService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    StatutRepository statutRepository;

    Livre l1;
    Livre l2;
    Livre l3;

    User user;

    Statut statut1;
    Statut statut2;
    Statut statut3;

    Reservation r1;
    Reservation r2;

    Pret pret1;
    Pret pret2;

    Examplaire ex1;
    Examplaire ex2;
    Examplaire ex3;


    List<Pret> listPret;
    List<Reservation> listReserv;
    List<Examplaire> listExamplaire;

    @Before
    public void setUp()
    {

        l1 = new Livre();
        l2 = new Livre();
        l3 = new Livre();

        user = new User();

        pret1 = new Pret();
        pret2 = new Pret();

        ex1 = new Examplaire();
        ex2 = new Examplaire();
        ex3 = new Examplaire();

        statut1 = new Statut();
        statut2 = new Statut();
        statut3= new Statut();

        r1 = new Reservation();
        r2 = new Reservation();

         listPret = new ArrayList<>();
         listReserv = new ArrayList<>();
         listExamplaire = new ArrayList<>();


        //  livre
        l1.setId(1L);
        l1.setTitre("Magie");


        l2.setTitre("Gourde");

        l3.setId(3L);
        l3.setTitre("Super");



        listPret.add(pret1);
        listPret.add(pret2);

        //statut
        statut1.setNom("First");
        statut2.setNom("InList");
        statut3.setNom("En Attente");

        //reserv
        r1.setId(1L);
        r1.setStatutReservation(statut1);

        r2.setId(2L);
        r2.setStatutReservation(statut2);

        listReserv.add(r1);
        listReserv.add(r2);

        l1.setReservations(listReserv);


        checkEmruntBefore();

        checkPlaceBefore();

        initMocks(this);
        when(livreRepository.findById(1L)).thenReturn(l1);
        when(livreRepository.findById(3L)).thenReturn(l3);

        when(securityService.getUser()).thenReturn(user);

        when(statutRepository.findByNom("First")).thenReturn(statut1);
        when(statutRepository.findByNom("En Attente")).thenReturn(statut3);

        when(reservationRepository.findByStatutReservationAndLivreReservation(statut3,l3)).thenReturn(listReserv);
    }

    //- verifie si l'user emprunte deja le livre, par l'id
    //le titre du livre ne doit pas correspondre a ceux deja emprunté
    //par l'user
    private void checkEmruntBefore()
    {

        ex1.setLivre(l1);
        ex2.setLivre(l2);

        r1.setStatutReservation(statut3);
        r2.setStatutReservation(statut3);


        pret1.setExamplaire(ex1);
        pret2.setExamplaire(ex2);

        listPret.add(pret1);
        listPret.add(pret2);

        user.setListeDePret(listPret);
    }

    //verifie la taille de la liste d'attente des reserv
    //elle ne doit pas etre superieur à aux double d'examplaire  du livre
    private void checkPlaceBefore()
    {
        listExamplaire.add(ex1);
        listExamplaire.add(ex2);
        l3.setExamplaires(listExamplaire);


    }

    @Test
    public void createReservation()
    {
        Reservation reservation = reservationService.createReservation(1L);
        System.out.println("\n reservation test  " + reservation.toString());

        //il a l'id du livre
        assertThat(reservation.getLivreReservation().getId()).isEqualTo(1L);

    }

    @Test
    public void checkEmpruntUser()
    {
        //l'user a deja le livre
       boolean check = reservationService.checkEmpruntUser(1L);
       assertThat(check).isFalse();

       //l'user ne l'a pas, il peut l'emprunter
       boolean check2 = reservationService.checkEmpruntUser(3L);
       assertThat(check2).isTrue();
    }

    //verifie la taille de la liste d'attente des reserv
    //elle ne doit pas etre superieur à aux double d'examplaire  du livre
    @Test
    public void checkPlaceListe()
    {
        //l3 a deux examplaire et il y a 2 reserv
        boolean check = reservationService.checkPlaceListe(3L);
        assertThat(check).isTrue();

    }

    @Test
    public void checkReservDispo() {
    }

    @Test
    public void getByUser() {
    }

    @Test
    public void cancelReservation() {
    }

    @Test
    public void getAllFirstReserve() {
    }

    @Test
    public void getAllFirstReserveNoSendMail() {
    }

    @Test
    public void saveList() {
    }

    @Test
    public void checkDelai() {
    }

    @Test
    public void checkListeReservForAllBook() {
    }

    @Test
    public void checkNewFirst() {
    }

    @Test
    public void findNewReserv() {
    }

    @Test
    public void getReservByBook() {
    }

    @Test
    public void finishReservation() {
    }
}