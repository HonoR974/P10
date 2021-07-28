package com.bibliotheque.service;

import com.bibliotheque.dto.PretBatchDTO;
import com.bibliotheque.model.Pret;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Interface BatchService
 */
public interface BatchService {

    /**
     * Recupere les prets en retard
     * @return liste de prets
     */
    List<PretBatchDTO> getPretRetard();

    /**
     * Recupere les prets en cours
     * @return Liste Prets
     * @throws IOException
     */
    List<PretBatchDTO> getPretEnCours() throws IOException;

    /**
     * Envoie les Prets en cours dans une verification
     * @param map Map<Integer,PretBatchDTO>
     */
    void sendPretEnCours(Map<Integer,PretBatchDTO> map);


    /**
     * Envoie les Prets qui ont recu leur mail
     * @param map Map<Integer, PretBatchDTO>
     * @return liste PretBatchDTO
     */
    List<PretBatchDTO> sendPretRappel(Map<Integer, PretBatchDTO> map);


}
