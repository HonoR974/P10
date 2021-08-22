package com.batch.controller;

import com.batch.model.ReservationDTO;
import com.batch.service.ReservationService;
import com.batch.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/batch")
public class ReservationController {


    @Autowired
    private SecurityService securityService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/reservations")
    public String test()
    {
        return "test";
    }

    @GetMapping("/sendMail")
    public String testMail() throws MessagingException, IOException {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setUsername("Théo Duchamps");
        reservationDTO.setMail("smiirf123@gmail.com");
        reservationDTO.setTitre("Le labyrinth");


        return reservationService.sendMail(reservationDTO);
    }


}
