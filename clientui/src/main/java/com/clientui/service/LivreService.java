package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;
import java.util.List;

/**
 * Interface LivreService
 */
public interface LivreService {

    /**
     * Recupere touts les livres
     * @return liste livre
     * @throws IOException
     * @throws InterruptedException
     */
    List<LivreDTO> getAll() throws IOException, InterruptedException;

    /**
     * Recupere touts les exemplaire par l'id livre
     * @param id id-livre
     * @return liste exemplaire
     * @throws IOException
     * @throws InterruptedException
     */
    List<ExamplaireDTO> getAllExamplaireByIdLivre(Long id) throws IOException, InterruptedException;

    /**
     * Recupere des livres pour l'accueil
     * @return liste livre
     * @throws IOException
     * @throws InterruptedException
     */
    List<LivreDTO> getLivreToAccueil() throws IOException, InterruptedException;

    /**
     * Recupere un livre par l'id
     * @param id
     * @return livre
     * @throws IOException
     * @throws InterruptedException
     */
    LivreDTO getLivreByIdLivre(Long id) throws IOException, InterruptedException;

    /**
     * Retourne le jwt
     * @return jwt
     */
    String getJwt();

    /**
     * Recupere les livres pour l'accueil
     * @param list
     * @return liste livre
     */
    List<LivreDTO> getLivreToAccueil(List<LivreDTO> list);

    /**
     * Recherche d'ouvrages
     * @param search
     * @return liste livre dto
     * @throws IOException
     * @throws InterruptedException
     */
    List<LivreDTO> recherche(String search) throws IOException, InterruptedException;

}
