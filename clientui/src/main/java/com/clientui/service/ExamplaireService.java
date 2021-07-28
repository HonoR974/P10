package com.clientui.service;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;

import java.io.IOException;

/**
 * Interface ExemplaireService
 */
public interface ExamplaireService
{
    /**
     * Recupere un exemplaire par l'id
     * @param id
     * @return exemplaire
     * @throws IOException
     * @throws InterruptedException
     */
    ExamplaireDTO getExamplaire(Long id) throws IOException, InterruptedException;

    /**
     * Recupere un livre par l'id exemplaire
     * @param id id-exemplaire
     * @return livre
     * @throws IOException
     * @throws InterruptedException
     */
    LivreDTO getLivreByIdExamplaire(Long id) throws IOException, InterruptedException;

    /**
     * Retourne le jwt
     * @return
     */
    String getJwt();
}
