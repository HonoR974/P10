package com.bibliotheque.service;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.model.Livre;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface BibliothequeService
 */
public interface BibliothequeService {

    /**
     * Recupère toutes bibliothèques
     * @return liste Bibliotheque
     */
    List<Bibliotheque> getAllBibliotheque();

    Bibliotheque getByName(String nom);

    /**
     * Creer une bibliothèque
     * @param bibliotheque Bibliotheque
     * @return bibliotheque
     */
    Bibliotheque createBibliotheque(Bibliotheque bibliotheque);

    /**
     * Modifie une bibliotheque
     * @param id id
     * @param bibliotheque Bibliotheque
     * @return bibliotheque
     */
    Bibliotheque updateBibliotheque(long id, Bibliotheque bibliotheque);

    /**
     * Supprime une bibliotheque
     * @param id
     */
    void deleteBibliotheque(long id);

    /**
     * Recupere une bibliotheque selon l'id
     * @param id
     * @return bibliotheque
     */
    Bibliotheque getBibliothequeById(long id);


    /**
     * Recupere tout les livres d'une bibliothèque
     * @param id
     * @return Liste livres
     */
    List<Livre> getAllLibreByIdBiblio(long id);

    /**
     * Recupere touts les livres
     * @return Liste de Livres
     */
    List<Livre> getAllLivre();
}
