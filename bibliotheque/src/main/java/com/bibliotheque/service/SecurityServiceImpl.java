package com.bibliotheque.service;

import com.bibliotheque.configuration.JwtUserDetailsService;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service SecurityServiceimpl
 */
@Service
public class SecurityServiceImpl implements SecurityService{

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    /**
     * Verification de l'authentification
     * @return boolean
     */
    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    /**
     * Recupere l'user
     * @return user
     */
    @Override
    public User getUser()
    {

        System.out.println("\n getUser" );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("\n username " + authentication.getName() );

        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    /**
     * Recupere l'username
     * @return string
     */
    @Override
    public String getUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }



}
