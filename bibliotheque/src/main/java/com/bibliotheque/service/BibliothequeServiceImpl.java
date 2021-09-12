package com.bibliotheque.service;

import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.model.Livre;
import com.bibliotheque.repository.BibliothequeRepository;
import com.bibliotheque.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Service BibliothequeServiceImpl
 */
@Service
public class BibliothequeServiceImpl implements BibliothequeService {

    @Autowired
    private BibliothequeRepository bibliothequeRepository;

    @Autowired
    private LivreRepository livreRepository;


    /**
     * Recupère toutes bibliothèques
     * @return liste Bibliotheque
     */
    @Override
    public List<Bibliotheque> getAllBibliotheque() {
        return  bibliothequeRepository.findAll();
    }

    @Override
    public Bibliotheque getByName(String nom) {
        return bibliothequeRepository.findByNom(nom);
    }


    /**
     * Creer une bibliothèque
     * @param bibliotheque
     * @return bibliotheque
     */
    @Override
    public Bibliotheque createBibliotheque(Bibliotheque bibliotheque) {
        bibliothequeRepository.save(bibliotheque);
        return bibliotheque;
    }

    /**
     * Modifie une bibliothèque selon l'id
     * @param id id_bibliotheque
     * @param bibliothequeRequest
     * @return bibliotheque
     */
    @Override
    public Bibliotheque updateBibliotheque(long id, Bibliotheque bibliothequeRequest) {
        Bibliotheque bibliotheque = bibliothequeRepository.findById(id);
        bibliotheque.setNom(bibliothequeRequest.getNom());
        bibliotheque.setAdresse(bibliothequeRequest.getAdresse());
        bibliothequeRepository.save(bibliotheque);
        return bibliotheque;
    }

    /**
     * Supprime une bibliothèque
     * @param id id_biblio
     */
    @Override
    public void deleteBibliotheque(long id) {
        bibliothequeRepository.deleteById(id);
    }

    /**
     * Recupere une bibliothèque
     * @param id
     * @return bibliotheque
     */
    @Override
    public Bibliotheque getBibliothequeById(long id) {
        Bibliotheque bibliotheque =  bibliothequeRepository.findById(id);
        return bibliotheque;
    }

    /**
     * Recupere touts les livres d'une bibliotheque
     * @param id id_bibliotheque
     * @return liste livres
     */
    @Override
    public List<Livre> getAllLibreByIdBiblio(long id) {
        Bibliotheque bibliotheque = bibliothequeRepository.findById(id);
        return bibliotheque.getLivres();
    }

    /**
     * Recupere touts les livres
     * @return
     */
    @Override
    public List<Livre> getAllLivre() {

        return livreRepository.findAll();
    }
}
