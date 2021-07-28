package com.clientui.service;


import com.clientui.dto.UserDTO;
import com.clientui.model.TesterUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Interface AuthBiblioService
 */
public interface AuthBiblioService {

    /**
     * Return  un token jwt
     * @param user
     * @return jwt
     */
     String authenticate (UserDTO user) throws IOException, InterruptedException;

    /**
     * Parse le jwt
     * @param jwt jwtBrut
     * @return jwt final
     * @throws JsonProcessingException
     */
     String parseJwt(String jwt ) throws JsonProcessingException;

    /**
     * Recupere un username par le jwt
     * @param jwt
     * @return string
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     */
     String getUserNameByToken(String jwt) throws IOException, InterruptedException, URISyntaxException;

    /**
     * Inscription de l'user
     * @param userDTO
     * @return user
     * @throws IOException
     * @throws InterruptedException
     */
     UserDTO save(UserDTO userDTO) throws IOException, InterruptedException;

    /**
     * Recupere un user dto par le jwt
     * @param jwt
     * @return  user dto
     * @throws IOException
     * @throws InterruptedException
     */
     UserDTO getUserDTOByJwt(String jwt) throws IOException, InterruptedException;

    /**
     * recupere le jwt
     * @return jwt
     */
     String getJwt();

    /**
     * Verifie l'user
     * @return testUser
     */
    TesterUser testConnection();



}