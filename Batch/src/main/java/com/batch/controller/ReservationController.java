package com.batch.controller;

import com.batch.model.ReservationDTO;
import com.batch.service.ReservationService;
import com.batch.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

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
    public String testMail() throws MessagingException {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setUsername("Théo Duchamps");
        reservationDTO.setMail("honore.guillaudeau1@gmail.com");
        reservationDTO.setTitre("Le labyrinth");


        reservationService.sendMail(reservationDTO);


        return "mail Envoyé";
    }


}
