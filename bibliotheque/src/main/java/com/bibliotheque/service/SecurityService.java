package com.bibliotheque.service;


import com.bibliotheque.model.User;

/**
 * Interface SecurityService
 */
public interface SecurityService {

    /**
     * Verification de l'authentification
     * @return boolean
     */
    boolean isAuthenticated();

    /**
     * Recupere l'user
     * @return l'user
     */
    User getUser();

    /**
     * Recupere l'username
     * @return string
     */
    String getUsername();

}
