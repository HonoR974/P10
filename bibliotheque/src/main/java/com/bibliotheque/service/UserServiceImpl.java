package com.bibliotheque.service;

import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.UserRepository;
import com.bibliotheque.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Inscription de l'user
     * @param userDTO
     * @return user
     */
    public User save(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setMatchingPassword(user.getPassword());
        user.setEnabled(false);
        return userRepository.save(user);
    }

    /**
     * Recupere un user par son username
     * @param username
     * @return user
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
