package com.bibliotheque.service;



import com.bibliotheque.dto.UserDTO;
import com.bibliotheque.model.User;
import com.bibliotheque.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserDTO userDTO;

    @Before
    public void setUp()
    {
        User user = new User();
        User user1  = new User();


        user.setUsername("UserTest");


        userDTO = new UserDTO();
        userDTO.setUsername("logTest");
        userDTO.setPassword("pwTest123");
        userDTO.setMatchingPassword("pwTest123");



        initMocks(this);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findByUsername("UserTest")).thenReturn(user);

        when(bCryptPasswordEncoder.encode("pwTest123")).thenReturn("passWordModifié");

    }

    @Test
    public void save()
    {

        User user = userService.save(userDTO);
        verify(userRepository, times(1)).save(any(User.class));
        assertThat(user.getUsername()).isEqualTo("UserTest");
    }

    @Test
    public void findByUsername()
    {
        User user = userService.findByUsername("UserTest");
        assertThat(user.getUsername()).isEqualTo("UserTest");
    }
}