package com.bibliotheque.service;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;

import java.util.List;

/**
 * Interface LivreService
 */
public interface LivreService {

    /**
     * Recupere touts les livres
     * @return liste de livres
     */
    List<Livre> getAllLivres();

    /**
     * Créer un livre
     * @param livre
     * @return livre
     */
    Livre createLivre(Livre livre);

    /**
     * Modifie un livre
     * @param id id-livre
     * @param livre
     * @return livre
     */
    Livre updateLivre(long id, Livre livre);

    /**
     * Supprime un livre
     * @param id id-livre
     */
    void deleteLivre(long id);

    /**
     * Recupere un livre par l'id
     * @param id id-livre
     * @return livre
     */
    Livre getLivreById(long id);

    /**
     * Recupere touts les exemplaire d'un livre
     * @param id id-livre
     * @return liste exemplaires
     */
    List<Examplaire> getAllExamplaireByIdLivre(long id);

    /**
     * Conversion liste DTO
     * @param list
     * @return list DTO
     */
    List<LivreDTO> convertListLivre(List<Livre> list);

    /**
     * Conversion DTO
     * @param livre
     * @return liste DTO
     */
    LivreDTO convertLivre(Livre livre);

    /**
     * Recupere tout les livres
     * @return liste livres
     */
    List<Livre> getAllLivre();


    /**
     * Recherche des ouvrages par titre et auteur
     * @param recherche
     * @return liste livre
     */
    List<Livre> searchLivre(String recherche);

    boolean checkDispo(long id);


    //verifie les livres disponibles
    void checkDispoAllLivres();

     String dateRetourByLivre(Livre livre);

     int nmbUserReserv(Livre livre);
}
