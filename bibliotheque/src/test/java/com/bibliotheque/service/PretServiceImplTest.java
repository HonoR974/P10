package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.ExamplaireRepository;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PretServiceImplTest {

    @Mock
    PretRepository pretRepository;

    @Mock
    StatutRepository statutRepository;

    @Mock
    ExamplaireRepository examplaireRepository;

    @Mock
    SecurityServiceImpl securityService;

    @Mock
    ExamplaireService examplaireService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    PretServiceImpl pretService;

    Pret p1;
    Pret p2;
    Pret p3;

    List<Pret> list;

    Statut stat1;
    Statut stat2;
    Statut stat3;


    User user;

    Livre l1;

    Examplaire ex;

    @Before
    public void setUp()
    {
        long id = 1L;
        long idp2 = 2L;

        p1 = new Pret();
        p2 = new Pret();
        p3 = new Pret();

        list = new ArrayList<>();

        stat1 = new Statut();
        stat2 = new Statut();
        stat3 = new Statut();

        user = new User();
        l1 = new Livre();
        ex = new Examplaire();

        p1.setId(id);
        stat1.setNom("Valider");
        p1.setStatut(stat1);
        p1.setDate_fin(LocalDate.of(2000, 1,1));
        p1.setProlonger(false);

        //validePret & finish Pret
        p2.setId(idp2);
        stat2.setNom("En Attente");
        p2.setStatut(stat2);
        ex.setEmprunt(false);
        p2.setExamplaire(ex);


        p3.setStatut(stat1);

        stat3.setNom("Fini");


        list.add(p1);
        list.add(p3);
        

        initMocks(this);

        when(pretRepository.findById(id)).thenReturn(p1);
        when(pretRepository.findById(idp2)).thenReturn(p2);
        when(pretRepository.findByStatut(stat1)).thenReturn(list);

        when(statutRepository.findByNom("Valider")).thenReturn(stat1);
        when(statutRepository.findByNom("Fini")).thenReturn(stat3);

        when(examplaireRepository.save(any())).thenReturn(any());
        when(examplaireRepository.save(ex)).thenReturn(ex);

        when(securityService.getUsername()).thenReturn("UserTest");


    }

    @Test
    public void createPret()
    {

        String username = "userCreate";
        User user = new User();
        user.setUsername(username);

        Statut statut = new Statut("En Creation");

        Examplaire examplaire = new Examplaire();

        examplaire.setId(10L);


        when(examplaireService.getExamplaireById(10L)).thenReturn(examplaire);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(securityService.getUsername()).thenReturn(username);
        when(statutRepository.findByNom("En Creation")).thenReturn(statut);

        // test
        Pret pret = pretService.createPret(10L);

        verify(pretRepository, times(1)).save(any(Pret.class));
        assertThat(pret.getExamplaire().getId()).isEqualTo(10L);
    }

    @Test
    public void givePretDTO()
    {
        User user = new User();
        user.setUsername("userTest");

        Livre livre = new Livre();
        livre.setTitre("livre des test ");

        Examplaire examplaire = new Examplaire();
        examplaire.setLivre(livre);

        Pret pret = new Pret();
        pret.setId(10L);
        stat1.setNom("Valider");
        pret.setStatut(stat1);
        pret.setDate_debut(LocalDate.of(2000,1,1));
        pret.setDate_fin(LocalDate.of(2000, 2,1));
        pret.setProlonger(false);
        pret.setExamplaire(examplaire);
        pret.setUser(user);

        // test
        PretDTO pretDTO = pretService.givePretDTO(pret);

        assertThat(pretDTO.getId()).isEqualTo(10L);

    }

    @Test
    public void giveListPretDTO() {
    }

    @Test
    public void validePret()
    {
          Pret pret = pretService.validePret(2L);

        System.out.println("\n le pret " + pret.toString());

        assertThat(pret.getStatut().getNom()).isEqualTo("Valider");
    }

    @Test
    public void getPretById()
    {
        Pret pret = pretService.getPretById(1L);

        assertThat(pret.getStatut().getNom()).isEqualTo("Valider");
    }

    @Test
    public void finishPret()
    {

         pretService.finishPret(2L);
         Pret pret = pretService.getPretById(2L);
         assertThat(pret.getStatut().getNom()).isEqualTo("Fini");
         assertThat(pret.getExamplaire().isEmprunt()).isFalse();
    }

    @Test
    public void prolongPret()
    {
        Pret pret = pretService.prolongPret(1L);
        assertThat(pret.getProlonger()).isTrue();

    }

    @Test
    public void getPretEmprunter()
    {
        List<Pret> list = pretService.getPretEmprunter();
        assertThat(list.size()).isEqualTo(2);

    }

}




