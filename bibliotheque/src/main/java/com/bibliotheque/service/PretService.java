package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;

import java.util.List;


/**
 * Interface PretService
 */
public interface PretService {

    /**
     * Créer un pret
     * @param id_examplaire
     * @return pret
     */
    Pret createPret(Long id_examplaire);

    /**
     * Conversion DTO
     * @param pret
     * @return
     */
    PretDTO givePretDTO(Pret pret);

    /**
     * Valide la création d'un pret
     * @param id_pret
     * @return pret
     */
    Pret validePret(long id_pret);

    /**
     * Recupere un pret par l'id
     * @param id_pret
     * @return pret
     */
    Pret getPretById(long id_pret);

    /**
     * Finalise le pret
     * @param id_pret
     */
    void finishPret (long id_pret);

    /**
     * Prolonge le pret
     * @param id_pret
     * @return pret
     */
    Pret prolongPret(long id_pret);

    /**
     * Recupere touts les prets en cours
     * @return liste prets
     */
    List<Pret> getPretEmprunter();

    /**
     * Conversion liste DTO
     * @param list
     * @return liste DTO
     */
    List<PretDTO> giveListPretDTO(List<Pret> list);

}
