package com.clientui.service;

import com.clientui.dto.BibliothequeDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;
import java.util.List;

/**
 * Interface BibliothequeService
 */
public interface BibliothequeService {

    /**
     * Recupere toutes les bibliotheque
     * @param jwt
     * @return liste bibliotheque
     * @throws IOException
     * @throws InterruptedException
     */
    List<BibliothequeDTO> getAllBibliotheque(String jwt) throws IOException, InterruptedException;

    /**
     * Recupere les livres d'une bibliotheque
     * @param id
     * @return liste livres
     * @throws IOException
     * @throws InterruptedException
     */
    List<LivreDTO> getAllLivreByIdBiblio(Long id) throws IOException, InterruptedException;

    /**
     * Recupere le jwt
     * @return jwt
     */
    String getJwt();


}
