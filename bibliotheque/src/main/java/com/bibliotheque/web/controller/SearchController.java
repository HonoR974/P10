package com.bibliotheque.web.controller;

import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController pour la recherche d'ouvrages
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private LivreService livreService;

    /**
     * Recherche d'ouvrage par le titre et l'auteur
     * @param recherche
     * @return liste livre dto
     */
    @GetMapping("/{search}")
    public ResponseEntity<?> searchLivres(@PathVariable(name = "search")String recherche)
    {
        List<Livre> list = livreService.searchLivre(recherche);

        List<LivreDTO> livreDTOS = livreService.convertListLivre(list);

        System.out.println("\n Résultat recherche  liste livre  " + livreDTOS);

        return new ResponseEntity<List<LivreDTO>>(livreDTOS, HttpStatus.ACCEPTED);
    }

}
