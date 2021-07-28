package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.*;
import com.bibliotheque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/***
 * Service BatchServiceImpl
 */
@Service
public class BatchServiceImpl implements BatchService{

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExamplaireRepository examplaireRepository;


    /**
     *  Obtient les pret valider
     * @return liste pret
     * @throws IOException
     */
    @Override
    public List<PretBatchDTO> getPretEnCours() throws IOException {

        //doit traiter les statut Valider

        Statut statut = statutRepository.findByNom("Valider");

        List<Pret> list = pretRepository.findByStatut(statut);


        return convertForBatch(list);
    }


    /**
     * Envoie les Prets en cours dans verfication
     * @param map
     */
    @Override
    public void sendPretEnCours(Map<Integer,PretBatchDTO> map)
    {
        List<PretBatchDTO> list = new ArrayList<>();

        for (Map.Entry mapentry : map.entrySet()) {
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());

            list.add((Integer) mapentry.getKey(),(PretBatchDTO) mapentry.getValue());
        }

        verfication(list);

    }


    /**
     * Verifie le statut avant de le sauvegarder
     * @param prets
     * @return
     */
    private void verfication(List<PretBatchDTO> prets)
    {
        List<Pret> list;
        Pret pret ;
        Statut statut;

        for (PretBatchDTO pretBatch : prets)
        {
            long id = pretBatch.getId();
            pret =  pretRepository.findById(id);

            //si ils ont un statut different on le save
            if (! pret.getStatut().getNom().equals(pretBatch.getStatut()))
            {

                statut = statutRepository.findByNom(pretBatch.getStatut());
                pret.setStatut(statut);
            }


            pretRepository.save(pret);
        }
    }

    /**
     * Return tout les pret a rendre
     * @return List<PretBatchDTO>
     */
    @Override
    public List<PretBatchDTO> getPretRetard() {

        Statut statut = statutRepository.findByNom("A Rendre");

        List<Pret> list = pretRepository.findByStatut(statut);

        return convertForBatch(list);
    }


    /**
     * Convertion de la liste des prets
     * @param prets
     * @return
     */
    private List<PretBatchDTO> convertForBatch(List<Pret> prets)
    {
        List<PretBatchDTO> list = new ArrayList<>();


        for (Pret pret : prets)
        {
            System.out.println("\n pret " + pret.getId());

            PretBatchDTO pretBatchDTO = new PretBatchDTO();

            pretBatchDTO.setId(pret.getId());
            pretBatchDTO.setDate_debut(pret.getDate_debut().toString());
            pretBatchDTO.setDate_fin(pret.getDate_fin().toString());
            pretBatchDTO.setStatut(pret.getStatut().getNom());
            pretBatchDTO.setUsername(pret.getUser().getUsername());
            pretBatchDTO.setEmail(pret.getUser().getEmail());
            pretBatchDTO.setTitre(pret.getExamplaire().getLivre().getTitre());
            pretBatchDTO.setEnvoieEmail(pret.getEmail());

            list.add(pretBatchDTO);
        }

        return list;
    }


    /**
     * Envoie les Prets qui ont recu leur email
     * @param map Map<Integer, PretBatchDTO>
     * @return List<PretBatchDTO>
     */
    @Override
    public List<PretBatchDTO> sendPretRappel(Map<Integer, PretBatchDTO> map) {

        List<PretBatchDTO> pretList = new ArrayList<>();

        for (Map.Entry mapentry : map.entrySet()) {
            System.out.println("clé: "+mapentry.getKey()
                    + " | valeur: " + mapentry.getValue());

            pretList.add((Integer) mapentry.getKey(),(PretBatchDTO) mapentry.getValue());
        }



        return saveRappel(pretList);
    }

    /**
     * Sauvegarde les prets ayant recu leurs rappel
     * @param list List<PretBatchDTO>
     * @return List<PretBatchDTO>
     */
    private List<PretBatchDTO> saveRappel(List<PretBatchDTO> list)
    {
        List<PretBatchDTO> listPret = new ArrayList<>();
        Pret pret ;

        for (PretBatchDTO pretBatch : list)
        {
            long id = pretBatch.getId();
            pret =  pretRepository.findById(id);

            //si c'est leur premier mail
            if (! pretBatch.getEnvoieEmail() )
            {
               pret.setEmail(true);
               pretBatch.setEnvoieEmail(true);
            }

            listPret.add(pretBatch);
            pretRepository.save(pret);
        }

        return listPret;
    }
}
