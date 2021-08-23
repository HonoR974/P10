package com.clientui.controller;

import com.clientui.model.PretBean;
import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
import com.clientui.service.ExamplaireService;
import com.clientui.service.PretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Controller PretController
 */
@Controller
public class PretController
{

    @Autowired
    private PretService pretService;

    @Autowired
    private ExamplaireService examplaireService;

    @Autowired
    private AuthBiblioService authBiblioService;

    @Autowired
    private EspaceService espaceService;

    /**
     * Creer le pret
     * @param id_examplaire
     * @param model
     * @return pret creation
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/pret")
    public String createPret(@RequestParam(value = "id")Long id_examplaire,
                             Model model) throws IOException, InterruptedException, ParseException {

        TesterUser user = authBiblioService.testConnection();

        System.out.println("\n user  " + user);

        if (user.isConnected())
        {

            PretDTO pretDTO = pretService.createPret(id_examplaire);

            System.out.println("\n le pretDTO créer " + pretDTO.toString());
            PretBean pretBean = pretService.givePretBean(pretDTO);

            model.addAttribute("pret", pretBean);
            model.addAttribute("user", authBiblioService.testConnection());
            model.addAttribute("exemplaire", examplaireService.getExamplaire(id_examplaire));
            model.addAttribute("livre", examplaireService.getLivreByIdExamplaire(id_examplaire));

            return "pret/Creation";
        }
        else
        {
            return "redirect:/login";
        }

    }

    /**
     * Valide le pret
     * @param id_pret
     * @param model
     * @return pret validate
     */
    @GetMapping("/validate")
    public String validePret(@RequestParam(value = "id")Long id_pret,
                             Model model) throws IOException, InterruptedException, ParseException {

        PretDTO pretDTO = pretService.validePret(id_pret);
        PretBean pretBean = pretService.givePretBean(pretDTO);

        model.addAttribute("pret", pretBean);

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
       model.addAttribute("utilisateur", userDTO);
       model.addAttribute("user", authBiblioService.testConnection());

        return "pret/Validate";
    }


    /**
     * Finalise le pret
     * @param id
     * @param model
     * @return pret finish
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/validate/finish")
    public String finishPret(@RequestParam(value = "id")Long id,
                             Model model) throws IOException, InterruptedException {

        pretService.finishPret(id);

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
        model.addAttribute("liste",espaceService.getListePretByIdUser(userDTO.getId()));

        return "redirect:/espace/admin/prets";
    }


    /**
     * Prolonge le pret
     * @param id
     * @param model
     * @return espace accueil
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/prolong")
    public String prolongPret(@RequestParam(value = "id")Long id,
                              Model model) throws IOException, InterruptedException, ParseException {

       PretDTO pretDTO2 = pretService.getPretDTOById(id);

       boolean prolongable= pretDTO2.isEnabled();

        UserDTO userDTO = authBiblioService.getUserDTOByJwt(authBiblioService.getJwt());
        model.addAttribute("user", authBiblioService.testConnection());
        model.addAttribute("utilisateur", userDTO);



        //si il n'a pas été prolongé
       if (! pretDTO2.isEnabled())
       {

           PretDTO pretDTO= pretService.prolongPret(id);

           prolongable = true;

       }
       //on peut le prolonger qu'une fois
       else
       {

            prolongable = false;
       }

        List<PretDTO> list =  espaceService.getListePretByIdUser(userDTO.getId());
        model.addAttribute("liste", pretService.convertList(list));

        model.addAttribute("prolongable", prolongable);
        return "espace/Accueil";
    }



}
