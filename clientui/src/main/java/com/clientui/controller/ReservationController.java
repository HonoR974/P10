package com.clientui.controller;

import com.clientui.dto.ReservationDTO;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ReservationController {


    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private ReservationService reservationService;

    //l'user demande une reservation d'un livre
    //il accede à la page récapitulatif des reservations présentes
    //de l'email lorsque celui-ci sera First et le delai

    @GetMapping("/livre/Reservations")
    public String getReservationByBook(@RequestParam("id")Long id_livre, Model model) throws IOException, InterruptedException {

        TesterUser user = authBiblioService.testConnection();

        if (user.isConnected())
        {
            List<ReservationDTO> reservationDTOList =  reservationService.getReservByBook(id_livre);

            model.addAttribute("listeReserv", reservationDTOList);

            return "reservation/liste";

        }
        else
        {
            return "redirect:/login";
        }
    }
}
