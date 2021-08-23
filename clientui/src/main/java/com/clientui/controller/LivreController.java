package com.clientui.controller;

import com.clientui.dto.LivreDTO;
import com.clientui.model.TesterUser;
import com.clientui.service.AuthBiblioService;
import com.clientui.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Controller livreController
 */
@Controller
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private AuthBiblioService authBiblioService;


    /**
     * Page liste livre
     * @param model
     * @return liste livre
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/livres/all")
    public String getAllLivre(Model model) throws IOException, InterruptedException {
        List<LivreDTO> list = livreService.getAll();
        TesterUser user = authBiblioService.testConnection();

        model.addAttribute("liste", list);
        model.addAttribute("user", user);

        System.out.println("\n les liste de livre au total " + list.toString());

        return "livres/Livres";
    }


    /**
     * Page liste exemplaire par livre
     * @param id
     * @param model
     * @return liste exemplaire
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping(value = "/livre/Examplaires")
    public String getExamplaireByIdLivre(@RequestParam(value = "id")Long id,
                                         Model model) throws IOException, InterruptedException {

        //le livre
        model.addAttribute("livre", livreService.getLivreByIdLivre(id));
        model.addAttribute("list", livreService.getAllExamplaireByIdLivre(id));
        model.addAttribute("user", authBiblioService.testConnection());

        return "examplaire/Examplaires";
    }

}
