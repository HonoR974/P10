package com.clientui.controller;

import com.clientui.dto.BibliothequeDTO;
import com.clientui.service.BibliothequeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

/**
 * Controller BibliothequeController
 */

@Controller
public class BibliothequeController {

    @Autowired
    private BibliothequeService bibliothequeService;


    /**
     * Envoie un jwt a l'API biblio
     * return la liste des bibliotheque de la ville
     * @param jwt
     * @param model
     * @return liste bibliotheque
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/bibliotheques")
    public String getAll(@RequestParam String jwt,
                               Model model) throws IOException, InterruptedException {
        List<BibliothequeDTO> list = bibliothequeService.getAllBibliotheque(jwt);
        model.addAttribute("list", list);
        return "bibliotheque/Accueil";
    }

    /**
     * Donne la page liste livres
     * @param id
     * @param model
     * @return liste livres
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/bibliotheque/Livres")
    public String getAllLivreByIdBiblio(@RequestParam(value = "id")Long id,
                                        Model model) throws IOException, InterruptedException {
        model.addAttribute("list", bibliothequeService.getAllLivreByIdBiblio(id));

        return "livres/Livres";
    }




}
