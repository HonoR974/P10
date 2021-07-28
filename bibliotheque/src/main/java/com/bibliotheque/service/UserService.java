package com.bibliotheque.service;

import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.User;

/**
 * Interface UserService
 */
public interface UserService {

    /**
     * Inscription de l'user
     * @param userDTO
     * @return user
     */
    User save(UserDTO userDTO);

    /**
     * Recupere un user par son username
     * @param username
     * @return user
     */
    User findByUsername(String username);
}
