package com.bibliotheque.web.controller;

import com.bibliotheque.dto.BibliothequeDTO;
import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Bibliotheque;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.BibliothequeService;
import com.bibliotheque.service.LivreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController BibliothequeController
 * Chaque methode retourne un Object DTO
 */
@RestController
@RequestMapping("/api/bibliotheque")
public class BibliothequeController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BibliothequeService bibliothequeService;

    @Autowired
    private LivreService livreService;

    /**
     * Recupere toutes les bibliothèques
     * @return liste Bibliotheques
     */
    @GetMapping
    public List<BibliothequeDTO> getAllBiblio()
    {
        return bibliothequeService.getAllBibliotheque().stream().map(
                bibliotheque -> modelMapper.map(bibliotheque, BibliothequeDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Créer une bibliothèque
     * @param bibliothequeDTO
     * @return Bibliotheque
     */
    @PostMapping
    public ResponseEntity<BibliothequeDTO> createBibliotheque(@RequestBody BibliothequeDTO bibliothequeDTO)
    {
        Bibliotheque biblioRequest = modelMapper.map(bibliothequeDTO, Bibliotheque.class);

        Bibliotheque bibliotheque = bibliothequeService.createBibliotheque(biblioRequest);

        BibliothequeDTO bibliothequeResponse = modelMapper.map(bibliotheque, BibliothequeDTO.class );

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.CREATED);
    }

    /**
     * Recupere une Bibliotheques selon l'id
     * @param id id-biblio
     * @return Bibliotheque
     */
    @GetMapping("/{id}")
    public ResponseEntity<BibliothequeDTO> getBiBliothequeById(@PathVariable(name = "id")Long id)
    {

        Bibliotheque bibliotheque = bibliothequeService.getBibliothequeById(id);

        BibliothequeDTO bibliothequeResponse = modelMapper.map(bibliotheque, BibliothequeDTO.class);

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.ACCEPTED);
    }

    /**
     * Modifie une bibliothèque
     * @param id id-biblio
     * @param bibliothequeDTO BibliothequeNouvelle
     * @return Bibliotheque
     */
    @PutMapping("/{id}")
    public ResponseEntity<BibliothequeDTO> updateBibliotheque(@PathVariable(name = "id")Long id,
                                            @RequestBody BibliothequeDTO bibliothequeDTO )
    {
        Bibliotheque bibliothequeRequest = modelMapper.map(bibliothequeDTO, Bibliotheque.class);

        Bibliotheque bibliotheque = bibliothequeService.updateBibliotheque(id, bibliothequeRequest);

        BibliothequeDTO bibliothequeResponse =modelMapper.map(bibliotheque, BibliothequeDTO.class);

        return new ResponseEntity<BibliothequeDTO>(bibliothequeResponse, HttpStatus.ACCEPTED);
    }

    /**
     * Supprime une Bibliotheque
     * @param id id-biblio
     * @return Bibliotheque
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteBibliotheque(@PathVariable(name = "id")Long id)
    {
        bibliothequeService.deleteBibliotheque(id);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Recupere tout les livres d'une bibliothèque
     * @param id id-biblio
     * @return Liste livres
     */
    @GetMapping("/Livres")
    public List<LivreDTO> getAllLivresByIdBiblio(@RequestParam(name = "id")Long id)
    {
        List<Livre> livreList= bibliothequeService.getAllLibreByIdBiblio(id);

        List<LivreDTO> list = livreService.convertListLivre(livreList);

        return list;
    }

    /**
     * Recupere touts les livres
     * @return liste livres
     */
    @GetMapping("/Livres/all")
    public List<LivreDTO> getAllLivres()
    {
        List<Livre> livreList= bibliothequeService.getAllLivre();

        List<LivreDTO> list = livreService.convertListLivre(livreList);

        return list;
    }
}
