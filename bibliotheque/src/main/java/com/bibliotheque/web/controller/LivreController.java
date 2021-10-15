package com.bibliotheque.web.controller;

import com.bibliotheque.dto.ExamplaireDTO;
import com.bibliotheque.dto.LivreDTO;
import com.bibliotheque.model.Examplaire;
import com.bibliotheque.model.Livre;
import com.bibliotheque.service.LivreService;
import com.bibliotheque.web.exception.LivreIntrouvableException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController LivreController
 */
@RestController
@RequestMapping("/api/livre")
public class LivreController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LivreService livreService;

    /**
     * Recupere touts les livres
     * @return liste DTO
     */
    @GetMapping
    public List<LivreDTO> getAllLivres()
    {

        List<LivreDTO> livreDTOList = livreService.convertListLivre(livreService.getAllLivres());

        if(livreDTOList.size() == 0 ) throw new LivreIntrouvableException( "il n' y a pas de livre ");

        System.out.println("\n les livres " + livreDTOList.toString());
        return livreDTOList;
    }

    /**
     * Creer un livre
     * @param livreDTO
     * @return livre dto
     */
    @PostMapping
    public ResponseEntity<LivreDTO> createLivre(@RequestBody LivreDTO livreDTO)
    {
        //conversion dto a entity
        Livre livreRequest = modelMapper.map(livreDTO, Livre.class);

        Livre livre = livreService.createLivre(livreRequest);

        //conversion entity a dto
        LivreDTO livreResponse = modelMapper.map(livre, LivreDTO.class);

        return new ResponseEntity<LivreDTO>(livreResponse, HttpStatus.CREATED);
    }

    /**
     * Recupere un livre par son id
     * @param id
     * @return livre dto
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<LivreDTO> getLivreById(@PathVariable(name = "id") Long id)
    {
        Livre livre = livreService.getLivreById(id);

        if(livre==null)
        {
            throw new LivreIntrouvableException( "Le produit avec l'id "
                    + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        }

        LivreDTO  livreResponse = livreService.convertLivre(livre);

        return new ResponseEntity<LivreDTO>(livreResponse, HttpStatus.ACCEPTED);
    }

    /**
     * Modifie un livre
     * @param id id-livre
     * @param livreDTO
     * @return livre dto
     */
    @PutMapping("/{id}")
    public ResponseEntity<LivreDTO> updateLivre(@PathVariable(name = "id")Long id,
                                                @RequestBody LivreDTO livreDTO)
    {
        //dto a entity
        Livre livreRequest = modelMapper.map(livreDTO, Livre.class);

        Livre livre = livreService.updateLivre(id, livreRequest);
        if(livre==null) throw new LivreIntrouvableException( "Le produit avec l'id "
                + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        //entity a dto
        LivreDTO livreResponse = livreService.convertLivre(livre);

        return ResponseEntity.ok().body(livreResponse);
    }

    /**
     * Supprime un livre
     * @param id id-livre
     * @return Statut Http
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteLivre(@PathVariable(name = "id")Long id)
    {
        livreService.deleteLivre(id);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Recupere touts les exemplaires d'un livre
     * @param id id-livre
     * @return liste exemplaire dto
     */
    @GetMapping("/examplaires")
    public ResponseEntity<?>  getAllExamplaireByIdLivre(@RequestParam(name = "id")long id)
    {
        List<Examplaire> examplaires = livreService.getAllExamplaireByIdLivre(id);

        List<ExamplaireDTO> examplaireDTOS = examplaires.stream().map(examplaire -> modelMapper.map(examplaire, ExamplaireDTO.class))
                                            .collect(Collectors.toList());

        return new ResponseEntity<List<ExamplaireDTO>>(examplaireDTOS, HttpStatus.ACCEPTED);
    }

    /**
     * Recupere des livres pour l'accueil
     * @return liste dto
     */
    @GetMapping("/accueil")
    public ResponseEntity<?> getLivreToAccueil()
    {
        List<Livre> list = livreService.getAllLivre();

        System.out.println("\n nombre de livre accueil " + list.size());
        List<LivreDTO> livreDTO = livreService.convertListLivre(list);

        return new ResponseEntity<List<LivreDTO>>(livreDTO, HttpStatus.ACCEPTED);

    }


}
