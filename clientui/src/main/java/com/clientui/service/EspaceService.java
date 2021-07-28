package com.clientui.service;

import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;

import java.io.IOException;
import java.util.List;


/**
 * Interface EspaceService
 */
public interface EspaceService
{
    /**
     * Recupere un userdto par l'id
     * @param id_user
     * @return userdto
     * @throws IOException
     * @throws InterruptedException
     */
    UserDTO getUserDTOByID(Long id_user) throws IOException, InterruptedException;

    /**
     * Recupere un eliste de pret par l'id user
     * @param id_user
     * @return liste pret
     * @throws IOException
     * @throws InterruptedException
     */
    List<PretDTO> getListePretByIdUser(Long id_user) throws IOException, InterruptedException;

    /**
     * Recupere le pretdto par l'id
     * @param id_pret
     * @return pret dto
     * @throws IOException
     * @throws InterruptedException
     */
    PretDTO getPretDTOByIdPret(Long id_pret) throws IOException, InterruptedException;


}
