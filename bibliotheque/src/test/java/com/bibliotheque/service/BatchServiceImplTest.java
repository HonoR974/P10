package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.PretRepository;
import com.bibliotheque.repository.StatutRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class BatchServiceImplTest {

    @InjectMocks
    BatchServiceImpl batchService;

    @Mock
    PretRepository pretRepository;

    @Mock
    StatutRepository statutRepository;

    Pret p1;
    Pret p2;

    @Before
    public void setUp()
    {
        Statut statut1 = new Statut("Valider");
        Statut statut2 = new Statut("En Attente");

        User user = new User();
        user.setUsername("userTest");
        user.setEmail("test@gmail.tst");

        Livre livre = new Livre();
        livre.setTitre("le livre test");
        Examplaire examplaire = new Examplaire();
        examplaire.setLivre(livre);


        p1 = new Pret();
        p1.setId(1L);
        LocalDate date =  LocalDate.of(2001,1,1);
        p1.setDate_debut(date);
        date = LocalDate.of(2001,2,1);
        p1.setDate_fin((date));
        p1.setStatut(statut1);
        p1.setUser(user);
        p1.setExamplaire(examplaire);


         p2 = new Pret();
        p2.setId(2L);
        date =  LocalDate.of(2001,6,1);
        p2.setDate_debut(date);
        date = LocalDate.of(2001,7,1);
        p2.setDate_fin((date));
        p2.setStatut(statut2);
        p2.setUser(user);
        p2.setExamplaire(examplaire);

        initMocks(this);

        when(pretRepository.findById(1L)).thenReturn(p1);
        when(pretRepository.findById(2L)).thenReturn(p2);

        when(statutRepository.findByNom("Valider")).thenReturn(statut1);
        when(statutRepository.findByNom("En Attente")).thenReturn(statut2);


    }

    @Test
    public void getPretEnCours() {
    }

    @Test
    public void sendPretEnCours()
    {
        PretBatchDTO pretBatchDTO1 = new PretBatchDTO();
        PretBatchDTO pretBatchDTO2 = new PretBatchDTO();

        pretBatchDTO1.setId(1L);
        pretBatchDTO1.setStatut("En Attente");

        pretBatchDTO2.setId(2L);
        pretBatchDTO2.setStatut("Valider");


        Map<Integer, PretBatchDTO> map = new HashMap<>();
        map.put(1,pretBatchDTO1);
        map.put(2,pretBatchDTO2);

        batchService.sendPretEnCours(map);
        Pret pret = pretRepository.findById(1L);

        verify(pretRepository, times(2)).save(any(Pret.class));
        assertThat(pret.getStatut().getNom()).isEqualTo("En Attente");


    }

    @Test
    public void getPretRetard() {
    }

    @Test
    public void sendPretRappel()
    {

        PretBatchDTO pretBatchDTO1 = new PretBatchDTO();
        PretBatchDTO pretBatchDTO2 = new PretBatchDTO();
        pretBatchDTO1.setId(1L);
        pretBatchDTO1.setEnvoieEmail(false);

        pretBatchDTO2.setId(2L);
        pretBatchDTO2.setEnvoieEmail(true);

        Map<Integer, PretBatchDTO> map = new HashMap<>();
        map.put(1,pretBatchDTO1);
        map.put(2,pretBatchDTO2);


        // test
       List<PretBatchDTO> listTest = batchService.sendPretRappel(map);

       System.out.println("\n listTest " + listTest.size());
        //verify repo
        assertThat(listTest.size()).isEqualTo(2);
        assertThat(listTest.get(0).getEnvoieEmail()).isTrue();
    }

    @Test
    public void convertForBatch()
    {
        List<Pret> prets = new ArrayList<>();
        prets.add(p1);
        prets.add(p2);


        List<PretBatchDTO> listTest = batchService.convertForBatch(prets);

        assertThat(listTest.size()).isEqualTo(2);
        assertThat(listTest.get(0).getId()).isEqualTo(1L);
    }

    @Test
    public void verification()
    {

        PretBatchDTO pretBatchDTO1 = new PretBatchDTO();
        PretBatchDTO pretBatchDTO2 = new PretBatchDTO();

        pretBatchDTO1.setId(1L);
        pretBatchDTO1.setStatut("En Attente");

        pretBatchDTO2.setId(2L);
        pretBatchDTO2.setStatut("Valider");

        List<PretBatchDTO> list = new ArrayList<>();
        list.add(pretBatchDTO1);
        list.add(pretBatchDTO2);

        //test
        batchService.verfication(list);

        Pret pret = pretRepository.findById(1L);

        verify(pretRepository, times(2)).save(any(Pret.class));
        assertThat(pret.getStatut().getNom()).isEqualTo("En Attente");

    }

    @Test
    public void saveRappel()
    {
        p1.setEmail(false);
        p2.setEmail(true);

        PretBatchDTO pretBatchDTO1 = new PretBatchDTO();
        PretBatchDTO pretBatchDTO2 = new PretBatchDTO();

        pretBatchDTO1.setId(1L);
        pretBatchDTO1.setEnvoieEmail(false);

        pretBatchDTO2.setId(2L);
        pretBatchDTO2.setEnvoieEmail(true);

        List<PretBatchDTO> list = new ArrayList<>();
        list.add(pretBatchDTO1);
        list.add(pretBatchDTO2);

        //test
        List<PretBatchDTO> batchDTOList = batchService.saveRappel(list);


        //verify repo
        assertThat(batchDTOList.size()).isEqualTo(2);
        assertThat(batchDTOList.get(0).getEnvoieEmail()).isTrue();
    }
}