package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.model.Reservation;
import com.bibliotheque.model.Statut;
import com.bibliotheque.repository.LivreRepository;
import com.bibliotheque.repository.ReservationRepository;
import com.bibliotheque.repository.StatutRepository;
import com.bibliotheque.web.exception.LivreIntrouvableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service LivreServiceImpl
 */
@Service
public class LivreServiceImpl implements LivreService{

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private StatutRepository statutRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Recupere touts les livres
     * @return liste livres
     */
    @Override
    public List<Livre> getAllLivres()
    {
        return livreRepository.findAll();
    }

    /**
     * Créer un livre
     * @param livre
     * @return livre
     */
    @Override
    public Livre createLivre(Livre livre)
    {
        livreRepository.save(livre);
        return livre;
    }


    /**
     * Recupere un livre selon l'id
     * @param id id-livre
     * @return l'id
     */
    @Override
    public Livre getLivreById(long id) {
        Livre livre =  livreRepository.findById(id);

        return livre;
    }

    /**
     * Modifie un livre
     * @param id id-livre
     * @param livreRequest
     * @return livre
     */
    @Override
    public Livre updateLivre(long id, Livre livreRequest)
    {
        Livre livre = livreRepository.findById(id);
        livre.setTitre(livreRequest.getTitre());
        livre.setAuteur(livreRequest.getAuteur());
        livreRepository.save(livre);
        return livre;
    }


    /**
     * Supprime un livre
     * @param id id-livre
     */
    @Override
    public void deleteLivre(long id) {
        livreRepository.deleteById(id);
    }

    /**
     * Recupere touts les exemplaires d'un livre
     * @param id id-livre
     * @return liste exemplaire
     */
    @Override
    public List<Examplaire> getAllExamplaireByIdLivre(long id) {
        Livre livre = livreRepository.findById(id);

        List<Examplaire> list = livre.getExamplaires();
        List<Examplaire> listFinal = new ArrayList<>();

        for (Examplaire examplaire : list)
        {
            if (!examplaire.isEmprunt())
            {
                listFinal.add(examplaire);
            }
        }

        return listFinal;
    }

    /**
     * Conversion Liste DTO
     * @param list
     * @return liste DTO
     */
    @Override
    public List<LivreDTO> convertListLivre(List<Livre> list)
    {
        List<LivreDTO> listFinal = new ArrayList<>();

        List<Examplaire> listExamplaire;


        for (Livre livre : list)
        {
            LivreDTO livreDTO = new LivreDTO();
            livreDTO.setId(livre.getId());
            livreDTO.setAuteur(livre.getAuteur());
            livreDTO.setTitre(livre.getTitre());

            listExamplaire = livre.getExamplaires();
            long countExamplaire = 0 ;
            for (Examplaire examplaire : listExamplaire)
            {
                if (! examplaire.isEmprunt() )
                    countExamplaire++;
            }
            livreDTO.setExamplaires(countExamplaire);

            livreDTO.setTitreImage(livre.getImage().getName());
            livreDTO.setDescription(livre.getDescription());
            livreDTO.setDateRetour(dateRetourByLivre(livre));
            livreDTO.setNmbUserReserv(nmbUserReserv(livre));
            listFinal.add(livreDTO);
        }

        return listFinal;
    }

    /**
     * Conversion DTO
     * @param livre
     * @return livreDTO
     */
    @Override
    public LivreDTO convertLivre(Livre livre) {

        LivreDTO livreDTO = new LivreDTO();
        livreDTO.setId(livre.getId());
        livreDTO.setAuteur(livre.getAuteur());
        livreDTO.setTitre(livre.getTitre());

        List<Examplaire> examplaires = livre.getExamplaires();

        long count = 0 ;
        for (Examplaire examplaire : examplaires)
        {
            if (!examplaire.isEmprunt())
            {
                count++;
            }
        }
        livreDTO.setExamplaires(count);

        livreDTO.setTitreImage(livre.getImage().getName());
        livreDTO.setDescription(livre.getDescription());


        return livreDTO;
    }

    private String dateRetourByLivre(Livre livre)
    {
        Statut statut = statutRepository.findByNom("First");
        List<Reservation> listReserv = reservationRepository.findByStatutReservationAndLivreReservation(statut, livre);
        Date dateRetour = new Date();

        for (Reservation reservation : listReserv)
        {
            if (reservation.getDate_fin().before(dateRetour))
            {
                dateRetour = reservation.getDate_fin();
            }

        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if (dateRetour==null)
        {
            return "null";
        }
        return dateFormat.format(dateRetour);

    }

    private int nmbUserReserv(Livre livre)
    {
        int nmbUser = 0;
        Statut statut1 = statutRepository.findByNom("First");
        Statut statut2 = statutRepository.findByNom("InList");

        List<Reservation> reservationList = reservationRepository.findByStatutReservationOrStatutReservationAndLivreReservation(statut1,statut2,livre);
        System.out.println("\n nmb de reservation est de " + reservationList.size() + "sur le livre a l'id " + livre.getId());

        nmbUser = reservationList.size();
        return nmbUser;
    }

    /**
     * Recupere touts les livres
     * @return
     */
    @Override
    public List<Livre> getAllLivre() {
        return livreRepository.findAll();
    }


    /**
     * Recherche des ouvrages par titre et auteur
     * @param recherche
     * @return liste livre
     */
    @Override
    public List<Livre> searchLivre(String recherche) {


       // List<Livre> list3 = livreRepository.findByTitreContainingOrAuteurContaining(recherche,recherche);

      //  System.out.println("\n list 3  " + list3);

        List<Livre> list4 = livreRepository.findByTitreContainsOrAuteurContains(recherche,recherche);

        System.out.println("\n list 4  " + list4);

        //List<Livre> list5 = livreRepository.findByTitreIsContainingOrAuteurIsContaining(recherche,recherche);

        //System.out.println("\n list   " + list5);


        return list4;
    }
}
