package com.bibliotheque.service;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;

import java.util.List;


/**
 * Interface ExemplaireService
 */
public interface ExamplaireService {

    /**
     * Recupere touts les exemplaires
     * @return liste exemplaires
     */
    List<Examplaire> getAllExamplaire();

    /**
     * Créer un exemplaire
     * @param examplaire
     * @return exemplaire
     */
    Examplaire createExamplaire(Examplaire examplaire);

    /**
     * Modifie un exemplaire
     * @param id id-exemplaire
     * @param examplaire
     * @return exemplaire
     */
    Examplaire updateExamplaire(long id, Examplaire examplaire);

    /**
     * Supprime un exemplaire
     * @param id id-exemplaire
     */
    void deleteExamplaire(long id);


    /**
     * Recupere un exemplaire par l'id
     * @param id id-exemplaire
     * @return exemplaire
     */
    Examplaire getExamplaireById(long id);

    /**
     * Recupere un livre par l'id
     * @param id id-livre
     * @return livre
     */
    Livre getLivreById(long id);

    /**
     * Conversion DTO
     * @param examplaire
     * @return exemplaire DTO
     */
    ExamplaireDTO convertExamplaire(Examplaire examplaire);
}
