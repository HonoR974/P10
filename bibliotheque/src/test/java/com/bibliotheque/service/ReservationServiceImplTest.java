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

import java.util.*;

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
    Livre l4;

    User user;

    Statut statut1;
    Statut statut2;
    Statut statut3;

    Reservation r1;
    Reservation r2;
    Reservation r3;


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

        getResrvByIdBefore();

        initMocks(this);
        when(livreRepository.findById(1L)).thenReturn(l1);
        when(livreRepository.findById(3L)).thenReturn(l3);

        when(securityService.getUser()).thenReturn(user);

        when(statutRepository.findByNom("First")).thenReturn(statut1);
        when(statutRepository.findByNom("InList")).thenReturn(statut2);
        when(statutRepository.findByNom("En Attente")).thenReturn(statut3);

        when(reservationRepository.findByStatutReservationAndLivreReservation(statut3,l3)).thenReturn(listReserv);
        when(reservationRepository.findAll()).thenReturn(listReserv);
        when(reservationRepository.findById(3L)).thenReturn(r3);
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

    private void getResrvByIdBefore()
    {
        r3 = new Reservation();
        l4 = new Livre();

        l4.setTitre("le soleil");

        r3.setLivreReservation(l4);
        r3.setId(3L);

    }

    @Test
    public void createReservation()
    {
        Reservation reservation = reservationService.createReservation(1L);
        System.out.println("\n reservation test  " + reservation.toString());

        //il a l'id du livre
        assertThat(reservation.getLivreReservation().getId()).isEqualTo(1L);

    }

    //- verifie si l'user emprunte deja le livre, par l'id
    //le titre du livre ne doit pas correspondre a ceux deja emprunté
    //par l'user
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
    public void getByUser()
    {
        //

        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        Reservation r3 = new Reservation();
        Reservation r4 = new Reservation();

        r1.setStatutReservation(statut1);
        r2.setStatutReservation(statut1);
        r3.setStatutReservation(statut2);
        r4.setStatutReservation(statut2);

        List<Reservation> list = new ArrayList<>();
        list.add(r1);
        list.add(r2);
        list.add(r3);
        list.add(r4);

        user.setListReservation(list);

        when(reservationRepository.findByStatutReservationAndUserReservation(statut1,user)).thenReturn(list);


        //
        List<Reservation> listTest = reservationService.getByUser();

        System.out.println("\n liste test " + listTest.size());
        assertThat(listTest.size()).isGreaterThan(3);




    }

    @Test
    public void cancelReservation()
    {
        //intro
        Reservation reserservation = new Reservation();
        Statut statut = new Statut();

        reserservation.setId(10L);

        statut.setId(10L);
        statut.setNom("Annuler");

        when(reservationRepository.findById(10L)).thenReturn(reserservation);
        when(statutRepository.findByNom("Annuler")).thenReturn(statut);


        //Test
        reservationService.cancelReservation(10L);


        //verif
        Reservation reservationTest = reservationService.getReservById(10L);
        assertThat(reservationTest.getStatutReservation().getNom()).isEqualTo("Annuler");

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
    public void checkDelai()
    {
        List<Reservation> list = new ArrayList<>();

        Statut statut = new Statut();
        statut.setNom("First");


        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        Reservation r3 = new Reservation();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, Calendar.OCTOBER);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();


        r1.setDate_fin(date);
        r1.setStatutReservation(statut);
        r1.setMailSend(true);

        list.add(r1);
        list.add(r2);
        list.add(r3);


        when(statutRepository.findByNom("First")).thenReturn(statut);
        when(reservationRepository.findByStatutReservation(statut)).thenReturn(list);

       List<Reservation> listTest =  reservationService.checkDelai();

        assertThat(listTest.size()).isEqualTo(1);
    }

    @Test
    public void checkListeReservForAllBook() {
    }

    @Test
    public void checkNewFirst() {
    }


    @Test
    public void findNewReserv()
    {
        List<Reservation> list = new ArrayList<>();

        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        Reservation r3 = new Reservation();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2001);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();


        r1.setDateDemande(date);

        cal.set(Calendar.MONTH, Calendar.FEBRUARY);

        r2.setDateDemande( cal.getTime());

        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        r3.setDateDemande(cal.getTime());

        list.add(r1);
        list.add(r2);
        list.add(r3);


        //test

        reservationService.findNewReserv(list);

        //verif
        assertThat(list.get(0).getStatutReservation().getNom()).isEqualTo("First");
    }

    @Test
    public void getReservByBook() {
    }

    @Test
    public void finishReservation() {
    }

    @Test
    public void getAlL()
    {
        List<Reservation> reservationList = reservationService.getAll();

        assertThat(reservationList.size()).isEqualTo(2);
    }

    @Test
    public void getReservById()
    {
        Reservation reservation = reservationService.getReservById(3L);

        assertThat(reservation.getLivreReservation().getTitre()).isEqualTo("le soleil");
    }
}