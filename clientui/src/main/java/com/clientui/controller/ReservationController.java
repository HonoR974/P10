package com.clientui.controller;

import com.clientui.dto.ExamplaireDTO;
import com.clientui.dto.LivreDTO;
import com.clientui.dto.ReservationDTO;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.LivreService;
import com.clientui.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ReservationController {


    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LivreService livreService;

    //l'user demande une reservation d'un livre
    //il accede à la page récapitulatif des reservations présentes
    //de l'email lorsque celui-ci sera First et le delai

    @GetMapping( value = {"/livre/Reservations", "/livre/Reservations/{msg}"})
    public String getReservationByBook(@RequestParam("id")Long id_livre,
                                     @PathVariable(value = "msg", required = false)String message,
                                       Model model) throws IOException, InterruptedException {

        TesterUser user = authBiblioService.testConnection();

        System.out.println("\n param msg : " + message);

        if (user.isConnected())
        {
            List<ReservationDTO> reservationDTOList =  reservationService.getReservByBook(id_livre);
            LivreDTO livreDTO = livreService.getLivreByIdLivre(id_livre);

            model.addAttribute("user", user);
            model.addAttribute("livre", livreDTO);

            model.addAttribute("listeReserv", reservationDTOList);
            if (message != null)
            {
                model.addAttribute("message", message);
            }

            return "reservation/liste";

        }
        else
        {
            return "redirect:/login";
        }
    }

    //creation de la reservation
    @GetMapping("/createReservation")
    public String createReserv(@RequestParam(value = "id")Long id_livre) throws IOException, InterruptedException {

        //l'user ne doit pas posseder le livre sinon page d'erreur avec message


        String message = reservationService.createReserv(id_livre);

        //si la creation a échouer
        if (message.contains("id"))
        {
            return "redirect:/livre/Reservations?id="+id_livre;

        }
        else
        {
            return "redirect:/livre/Reservations/"+ message+ "/?id=" + id_livre;
        }





    }


    //annulation de la reservation
    @GetMapping("/cancelReservation")
    public String cancelReserv(@RequestParam("id")Long id_reserv) throws IOException, InterruptedException {

        String message = reservationService.cancelReservation(id_reserv);
        return "redirect:/espace?jwt="+authBiblioService.getJwt();
    }


    //emprunter le livre ( sendMail is true )
    //pour la creation du pret
    //l'id de l'exemplaire est necessaire
    @GetMapping("/finishReservation")
    public String finishReserv(@RequestParam("id")Long id_reserv) throws IOException, InterruptedException {

        ExamplaireDTO examplaireDTO = reservationService.finishReserv(id_reserv);
        System.out.println("\n l'examplaire choisi pôur la reserv est " + examplaireDTO.toString());

        //le return possede l'id de l'exemplaire disponible
        return "redirect:/pret/" + examplaireDTO.getId();
    }



}
