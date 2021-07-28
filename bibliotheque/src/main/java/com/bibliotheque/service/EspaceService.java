package com.bibliotheque.service;

import com.bibliotheque.dto.PretDTO;
import com.bibliotheque.model.Pret;
import com.bibliotheque.model.User;

import java.util.List;

/**
 * Interface EspaceService
 */
public interface EspaceService {

    /**
     * Recupere un user par l'id
     * @param id id-user
     * @return user
     */
    User getUserById(long id);

    /**
     * Conversion pour DTO
     * @param pret
     * @return
     */
    PretDTO givePretDTO(Pret pret);

    /**
     * Recupère les prets de l'user
     * @param id_user
     * @return liste prets
     */
    List<Pret> getListPretByIdUser(long id_user);

    /**
     * Conversion pour une liste DTO
     * @param list
     * @return liste pret DTO
     */
    List<PretDTO> giveListDTO(List<Pret> list);

    /**
     * Recupère le pret par l'id
     * @param id id-pret
     * @return pret
     */
    Pret getPretById(long id);
}
