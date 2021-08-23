package com.clientui.service;

import com.clientui.model.PretBean;
import com.clientui.dto.PretDTO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Interface PretService
 */
public interface PretService
{

    /**
     * Creation d'un pret
     * @param id_examplaire
     * @return pret
     * @throws IOException
     * @throws InterruptedException
     */
    PretDTO createPret(Long id_examplaire) throws IOException, InterruptedException;

    /**
     * Validation d'un pret
     * @param id_pret
     * @return pret
     * @throws IOException
     * @throws InterruptedException
     */
    PretDTO validePret(Long id_pret) throws IOException, InterruptedException;

    /**
     * Conversion de dto a pretBean
     * @param pretDTO
     * @return pretBean
     */
    PretBean givePretBean(PretDTO pretDTO) throws ParseException;

    /**
     * Recupere un pret dto par l'id
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    PretDTO getPretDTOById(Long id_pret) throws IOException, InterruptedException;

    /**
     * Finalise un pret
     * @param id_pret
     * @throws IOException
     * @throws InterruptedException
     */
    void finishPret(Long id_pret) throws IOException, InterruptedException;

    /**
     * Prolonge un pret
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    PretDTO prolongPret(Long id_pret) throws IOException, InterruptedException;

    /**
     * Recupere tout les prets en cours
     * @return liste prets
     * @throws IOException
     * @throws InterruptedException
     */
    List<PretDTO> getPretEmprunter() throws IOException, InterruptedException;

    /**
     * Convertion DTO Liste
     * @param list
     * @return liste
     */
    List<PretBean> convertList(List<PretDTO> list) throws ParseException;
}
