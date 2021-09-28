package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.Statut;
import com.bibliotheque.model.User;
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
import java.util.List;

import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class EspaceServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PretRepository pretRepository;

    @Mock
    StatutRepository statutRepository;

    @InjectMocks
    EspaceServiceImpl espaceService;

    User user;

    Pret pret1;
    Pret pret2;

    List<Pret> listPret;

    Statut statut;



    @Before
    public void setUp()
    {
        userRepository = mock(UserRepository.class);
        pretRepository = mock(PretRepository.class);
        statutRepository = mock(StatutRepository.class);

        int id = 1;

        //---- user
        user = new User();

        user.setUsername("userTest");

        //---- pret
        pret1 = new Pret();
        pret2 = new Pret();



        listPret = new ArrayList<>();

        listPret.add(pret1);
        listPret.add(pret2);

        //---- statut

        statut = new Statut();
        statut.setNom("Valider");
        pret1.setStatut(statut);

        initMocks(this);

        when(userRepository.findById(id)).thenReturn(user);
        when(statutRepository.findByNom("Valider")).thenReturn(statut);
        when(pretRepository.findByUserAndStatut(user,statut)).thenReturn(listPret);
        when(pretRepository.findById(1)).thenReturn(pret1);

    }

    @Test
    public void getUserById()
    {

        User user = espaceService.getUserById(1);

        assertThat(user.getUsername()).isEqualTo("userTest");

    }

    @Test
    public void getListPretByIdUser()
    {

        List<Pret>list = espaceService.getListPretByIdUser(1);

        System.out.println("\n la liste de pret   " + pretRepository.findAll());

        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void giveListDTO()
    {

    }

    @Test
    public void givePretDTO()
    {
        Pret pret = new Pret();

        pret.setId(5L);
        pret.setDate_debut(LocalDate.of(2000,1,1));
        pret.setDate_fin(LocalDate.of(2000,2,1));
       // pret.set
    }

    @Test
    public void getPretById()
    {
        Pret pret = espaceService.getPretById(1);
        assertThat(pret.getStatut().getNom()).isEqualTo("Valider");
    }
}







