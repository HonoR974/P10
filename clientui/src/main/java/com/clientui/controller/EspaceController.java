package com.clientui.controller;

import com.clientui.dto.PretDTO;
import com.clientui.dto.ReservationDTO;
import com.clientui.dto.UserDTO;
import com.clientui.model.PretBean;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
import com.clientui.service.PretService;
import com.clientui.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Controller EspaceController
 */
@Controller
@RequestMapping("/espace")
public class EspaceController
{
    @Autowired
    private EspaceService espaceService;

    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private PretService pretService;

    @Autowired
    private ReservationService reservationService;

    /**
     * Accueil espace user
     * @param jwt
     * @return espace accueil
     */
    @GetMapping()
    public String espaceAccueil(@RequestParam(name = "jwt")String jwt,
                                Model model) throws IOException, InterruptedException, ParseException {
        UserDTO user = authBiblioService.getUserDTOByJwt(jwt);

        //alert box
        boolean prolongable=true;
        boolean dateProlongable = true;

        //liste de prets
        List<PretDTO> list = espaceService.getListePretByIdUser(user.getId());

        //liste de reservations
        List<ReservationDTO> reservationDTOList = reservationService.getReserByUser();
        System.out.println("\n la liste des reserv " + reservationDTOList);

        model.addAttribute("user", authBiblioService.testConnection());
        model.addAttribute("utilisateur", espaceService.getUserDTOByID(user.getId()));
        model.addAttribute("liste",  pretService.convertList(list));
        model.addAttribute("prolongable", prolongable);
        model.addAttribute("listeReserv", reservationDTOList);
        model.addAttribute("dateProlongable", dateProlongable);
        

        return "espace/Accueil";
    }




    /**
     *  Page pret detail
     * @param id
     * @param model
     * @return pret detail
     */
    @GetMapping("/pret")
    public String pretDetail(@RequestParam(name = "id")Long id,
                             Model model) throws IOException, InterruptedException, ParseException {
        PretDTO pretDTO = espaceService.getPretDTOByIdPret(id);
        PretBean pretBean = pretService.givePretBean(pretDTO);

        model.addAttribute("pret", pretBean);
        model.addAttribute("user", authBiblioService.testConnection());

        return "pret/Detail";
    }


    /**
     * page admin
     * @param model
     * @return admin page
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/admin/prets")
    public String getPretEmprunter(Model model) throws IOException, InterruptedException, ParseException {

        List<PretDTO> list = pretService.getPretEmprunter();


        model.addAttribute("liste", pretService.convertList(list));
        model.addAttribute("user", authBiblioService.testConnection());
        return "admin/ListePrets";
    }


    //page reservation detail
    @GetMapping("/reservation")
    public String reservationDetail(@RequestParam("id")Long id_reservation, Model model) throws IOException, InterruptedException {

        ReservationDTO reservationDTO = reservationService.getReservById(id_reservation);
        model.addAttribute("reserv", reservationDTO);


        //recupere l'id de l'examplaire disponible
        //pour la demande de pret

        TesterUser user = authBiblioService.testConnection();
        model.addAttribute("user", user);

        return "reservation/reservation";
    }
}
