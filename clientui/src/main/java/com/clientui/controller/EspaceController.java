package com.clientui.controller;

import com.clientui.dto.PretDTO;
import com.clientui.dto.UserDTO;
import com.clientui.model.PretBean;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.EspaceService;
import com.clientui.service.PretService;
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


    /**
     * Accueil espace user
     * @param jwt
     * @return espace accueil
     */
    @GetMapping()
    public String espaceAccueil(@RequestParam(name = "jwt")String jwt,
                                Model model) throws IOException, InterruptedException, ParseException {
        UserDTO user = authBiblioService.getUserDTOByJwt(jwt);
        boolean prolongable=true;

        List<PretDTO> list = espaceService.getListePretByIdUser(user.getId());


        model.addAttribute("user", authBiblioService.testConnection());
        model.addAttribute("utilisateur", espaceService.getUserDTOByID(user.getId()));
        model.addAttribute("liste",  pretService.convertList(list));
        model.addAttribute("prolongable", prolongable);

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

}
